<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import GoalsNavbar from "@/components/GoalsNavbar.vue";
import SideNavBar from "@/components/SideNavBar.vue";
import GoalCard from "@/components/GoalCard.vue";
import AddGoalModal from "@/components/AddGoalModal.vue";
import EditGoalModal from "@/components/EditGoalModal.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";
import { useRouter } from 'vue-router';

const goals = ref([]);
const showAddModal = ref(false);
const showEditModal = ref(false);
const showConfirmModal = ref(false);
const selectedGoal = ref(null);
const goalToDelete = ref(null);
const router = useRouter();

// Fetch goals from backend
const fetchGoals = async () => {
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      console.error('No user ID found');
      return;
    }
    const response = await axios.get(`/api/goals/user/${userId}`);
    goals.value = response.data;
  } catch (error) {
    console.error('Failed to fetch goals:', error);
    if (error.response?.status === 403) {
      // Handle unauthorized access
      router.push('/login-page');
    }
  }
};

// Add new goal
const addGoal = async (newGoal) => {
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      console.error('No user ID found');
      return;
    }
    const goalData = {
      goalName: newGoal.name,
      goalAmount: parseFloat(newGoal.goalAmount),
      currentAmount: 0,
      deadline: newGoal.deadline,
      user: { userId: parseInt(userId) }
    };
    
    const response = await axios.post('/api/goals/create', goalData);
    goals.value.push(response.data);
    showAddModal.value = false;
  } catch (error) {
    console.error('Failed to add goal:', error);
  }
};

// Update goal
const updateGoal = async (updatedGoal) => {
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      console.error('No user ID found');
      return;
    }
    const response = await axios.put(
      `/api/goals/user/${userId}/goal/${updatedGoal.goalId}`,
      updatedGoal
    );
    const index = goals.value.findIndex((goal) => goal.goalId === updatedGoal.goalId);
    if (index !== -1) {
      goals.value[index] = response.data;
    }
    showEditModal.value = false;
  } catch (error) {
    console.error('Failed to update goal:', error);
  }
};

// Delete goal
const deleteGoal = async (goalId) => {
  try {
    const userId = localStorage.getItem('userId');
    await axios.delete(`http://localhost:8080/api/goals/user/${userId}/goal/${goalId}`);
    goals.value = goals.value.filter((goal) => goal.goalId !== goalId);
  } catch (error) {
    console.error('Failed to delete goal:', error);
  }
};

// Handle delete confirmation
const confirmDelete = async () => {
  if (goalToDelete.value) {
    await deleteGoal(goalToDelete.value);
    goalToDelete.value = null;
    showConfirmModal.value = false;
  }
};

// Update the delete handler to show confirmation first
const handleDeleteClick = (goalId) => {
  goalToDelete.value = goalId;
  showConfirmModal.value = true;
};

const openEditModal = (goal) => {
  selectedGoal.value = { ...goal };
  showEditModal.value = true;
};

onMounted(() => {
  fetchGoals();
});
</script>

<template>
  <div class="flex bg-gray-200 min-h-screen">
    <!-- Sidebar -->
    <SideNavBar />

    <!-- Main Content -->
    <div class="flex-1 ml-64">
      <!-- Goals Navbar -->
      <GoalsNavbar @add-goal="showAddModal = true" />

      <!-- Page Content -->
      <div class="p-6 pt-20">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <GoalCard
              v-for="goal in goals"
              :key="goal.goalId"
              :goal="goal"
              @edit="openEditModal"
              @delete="handleDeleteClick"
          />
        </div>
      </div>
    </div>

    <!-- Add Goal Modal -->
    <AddGoalModal v-if="showAddModal" @close="showAddModal = false" @save="addGoal" />

    <!-- Edit Goal Modal -->
    <EditGoalModal
        v-if="showEditModal"
        :goal="selectedGoal"
        @close="showEditModal = false"
        @save="updateGoal"
    />

    <ConfirmationModal
        v-if="showConfirmModal"
        title="Delete Goal"
        message="Are you sure you want to delete this goal? This action cannot be undone."
        @confirm="confirmDelete"
        @cancel="showConfirmModal = false"
        :show="showConfirmModal"
    />
  </div>
</template>

<style scoped>
</style>
