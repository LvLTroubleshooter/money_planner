<script setup>
import { ref } from "vue";
import GoalsNavbar from "@/components/GoalsNavbar.vue";
import SideNavBar from "@/components/SideNavBar.vue";
import GoalCard from "@/components/GoalCard.vue";
import AddGoalModal from "@/components/AddGoalModal.vue";
import EditGoalModal from "@/components/EditGoalModal.vue";

const goals = ref([
  {
    id: 1,
    name: "Vacation Fund",
    goalAmount: 2000,
    currentAmount: 500,
    deadline: "2024-12-31",
  },
  {
    id: 2,
    name: "Emergency Fund",
    goalAmount: 3000,
    currentAmount: 1500,
    deadline: "2025-06-30",
  },
  {
    id: 3,
    name: "Car Purchase",
    goalAmount: 10000,
    currentAmount: 2000,
    deadline: "2026-01-01",
  },
]);

const showAddModal = ref(false);
const showEditModal = ref(false);
const selectedGoal = ref(null);

const addGoal = (newGoal) => {
  goals.value.push({ ...newGoal, id: Date.now() });
};

const updateGoal = (updatedGoal) => {
  const index = goals.value.findIndex((goal) => goal.id === updatedGoal.id);
  if (index !== -1) {
    goals.value[index] = { ...updatedGoal };
  }
};

const deleteGoal = (id) => {
  goals.value = goals.value.filter((goal) => goal.id !== id);
};

const openEditModal = (goal) => {
  selectedGoal.value = { ...goal }; // Pass the goal to the modal
  showEditModal.value = true;
};
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
              :key="goal.id"
              :goal="goal"
              @edit="openEditModal"
              @delete="deleteGoal"
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
  </div>
</template>

<style scoped>
</style>
