<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import GoalsNavbar from "@/components/GoalsNavbar.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";

const goals = ref([]);
const showAddModal = ref(false);
const showEditModal = ref(false);
const selectedGoal = ref(null);
const error = ref('');
const loading = ref(false);
const showDeleteConfirmModal = ref(false);
const goalToDelete = ref(null);

const newGoal = ref({
  goalName: '',
  goalAmount: '',
  currentAmount: '',
  deadline: new Date().toISOString().split('T')[0]
});

// Fetch goals
const fetchGoals = async () => {
  loading.value = true;
  error.value = '';
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('User not found. Please log in again.');
    }
    const response = await axios.get(`/api/goals/user/${userId}`);
    goals.value = response.data;
  } catch (err) {
    if (err.response?.status === 404) {
      goals.value = [];
      return;
    }
    error.value = 'Unable to load goals. Please try again later.';
  } finally {
    loading.value = false;
  }
};

// Validate goal data
const validateGoal = (goal) => {
  if (!goal.goalName?.trim()) {
    throw new Error('Goal name is required');
  }

  const goalAmount = parseFloat(goal.goalAmount);
  if (isNaN(goalAmount) || goalAmount <= 0) {
    throw new Error('Goal amount must be greater than 0');
  }

  const currentAmount = parseFloat(goal.currentAmount || 0);
  if (isNaN(currentAmount) || currentAmount < 0) {
    throw new Error('Current amount cannot be negative');
  }

  if (currentAmount > goalAmount) {
    throw new Error('Current amount cannot be greater than goal amount');
  }

  if (!goal.deadline) {
    throw new Error('Deadline is required');
  }

  const deadlineDate = new Date(goal.deadline);
  const today = new Date();
  today.setHours(0, 0, 0, 0);

  if (deadlineDate < today) {
    throw new Error('Deadline cannot be in the past');
  }
};

// Add goal
const addGoal = async () => {
  error.value = '';
  loading.value = true;
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('User not found. Please log in again.');
    }

    validateGoal(newGoal.value);

    const goalData = {
      goalName: newGoal.value.goalName.trim(),
      goalAmount: parseFloat(newGoal.value.goalAmount),
      currentAmount: parseFloat(newGoal.value.currentAmount || 0),
      deadline: newGoal.value.deadline,
      user: { userId: parseInt(userId) }
    };

    const response = await axios.post('/api/goals/create', goalData);
    goals.value.push(response.data);
    showAddModal.value = false;
    newGoal.value = {
      goalName: '',
      goalAmount: '',
      currentAmount: '',
      deadline: new Date().toISOString().split('T')[0]
    };
  } catch (err) {
    console.error('Failed to add goal:', err);
    const errorMessage = err.response?.data?.error || err.message || 'Failed to add goal';
    error.value = errorMessage;
    alert(errorMessage);
  } finally {
    loading.value = false;
  }
};

// Update goal
const updateGoal = async () => {
  error.value = '';
  loading.value = true;
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      throw new Error('User not found. Please log in again.');
    }

    validateGoal(selectedGoal.value);

    const response = await axios.put(
      `/api/goals/user/${userId}/goal/${selectedGoal.value.goalId}`,
      {
        ...selectedGoal.value,
        goalAmount: parseFloat(selectedGoal.value.goalAmount),
        currentAmount: parseFloat(selectedGoal.value.currentAmount)
      }
    );
    const index = goals.value.findIndex(goal => goal.goalId === selectedGoal.value.goalId);
    if (index !== -1) {
      goals.value[index] = response.data;
    }
    showEditModal.value = false;
  } catch (err) {
    console.error('Failed to update goal:', err);
    error.value = err.response?.data?.error || err.message || 'Failed to update goal';
    alert(error.value);
  } finally {
    loading.value = false;
  }
};

// Delete goal
const confirmDelete = (goal) => {
  goalToDelete.value = goal;
  showDeleteConfirmModal.value = true;
};

const handleDelete = async () => {
  try {
    const userId = localStorage.getItem('userId');
    await axios.delete(`/api/goals/user/${userId}/goal/${goalToDelete.value.goalId}`);
    goals.value = goals.value.filter(goal => goal.goalId !== goalToDelete.value.goalId);
    showDeleteConfirmModal.value = false;
    goalToDelete.value = null;
  } catch (error) {
    console.error('Failed to delete goal:', error);
  }
};

onMounted(() => {
  fetchGoals();
});
</script>

<template>
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <GoalsNavbar @add-goal="showAddModal = true" />

      <div class="p-6 pt-20">
        <!-- Error Message -->
        <div v-if="error" class="mb-6 p-4 bg-red-100 text-red-700 rounded-lg">
          {{ error }}
        </div>

        <!-- Loading State -->
        <div v-if="loading" class="flex justify-center items-center py-8">
          <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
        </div>

        <!-- Empty State -->
        <div v-else-if="goals.length === 0" class="text-center py-12">
          <div class="bg-white rounded-2xl p-8 shadow-md max-w-md mx-auto">
            <div class="text-gray-400 mb-4">
              <i class="pi pi-flag text-5xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-gray-700 mb-2">No Goals Yet</h3>
            <p class="text-gray-500 mb-6">Start by adding your first financial goal!</p>
            <button @click="showAddModal = true"
              class="bg-custom-color text-white px-6 py-2 rounded-lg shadow-md hover:bg-custom-hover-color transition-all">
              Add Goal
            </button>
          </div>
        </div>

        <!-- Goals Grid -->
        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="goal in goals" :key="goal.goalId"
            class="bg-white rounded-2xl shadow-md overflow-hidden hover:shadow-xl transform hover:-translate-y-1 transition-all duration-300 group">
            <!-- Progress Bar -->
            <div class="relative h-24 bg-gradient-to-r from-blue-500 to-indigo-600 flex items-center justify-center">
              <div class="text-white text-2xl font-bold opacity-75">
                {{ ((goal.currentAmount / goal.goalAmount) * 100).toFixed(1) }}%
              </div>
              <div class="absolute bottom-0 left-0 right-0 h-2 bg-black bg-opacity-20">
                <div class="h-full bg-amber-400" :style="{ width: `${(goal.currentAmount / goal.goalAmount) * 100}%` }">
                </div>
              </div>
            </div>

            <div class="p-6 relative">
              <!-- Goal Header -->
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-xl font-bold text-gray-800 group-hover:text-blue-600 transition-colors">
                  {{ goal.goalName }}
                </h3>
                <div class="flex space-x-2 opacity-0 group-hover:opacity-100 transition-opacity">
                  <button @click="selectedGoal = { ...goal }; showEditModal = true"
                    class="text-yellow-500 hover:text-yellow-600 transition-colors p-2 hover:bg-yellow-50 rounded-full">
                    <i class="pi pi-pencil"></i>
                  </button>
                  <button @click="confirmDelete(goal)"
                    class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
                    <i class="pi pi-trash"></i>
                  </button>
                </div>
              </div>

              <!-- Goal Progress -->
              <div class="space-y-4 relative">
                <div class="flex justify-between items-baseline">
                  <div>
                    <span
                      class="text-3xl font-bold bg-gradient-to-r from-blue-500 to-indigo-600 bg-clip-text text-transparent">
                      ${{ goal.currentAmount }}
                    </span>
                    <span class="text-sm text-gray-500 ml-2">saved</span>
                  </div>
                  <div class="text-right">
                    <span class="text-xl font-semibold text-gray-700">${{ goal.goalAmount }}</span>
                    <span class="text-sm text-gray-500 ml-1">goal</span>
                  </div>
                </div>

                <div class="text-sm text-gray-500 flex items-center space-x-2">
                  <i class="pi pi-calendar text-blue-500"></i>
                  <span>Deadline: </span>
                  <span>{{ new Date(goal.deadline).toLocaleDateString() }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Add Goal Modal -->
        <div v-if="showAddModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div class="bg-white rounded-xl shadow-xl max-w-md w-full">
            <div class="p-6 border-b">
              <div class="flex justify-between items-center">
                <h3 class="text-xl font-bold text-gray-800">Add New Goal</h3>
                <button @click="showAddModal = false" class="text-gray-500 hover:text-gray-700">
                  <i class="pi pi-times"></i>
                </button>
              </div>
            </div>

            <form @submit.prevent="addGoal" class="p-6 space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Goal Name</label>
                <input v-model.trim="newGoal.goalName" type="text" required
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Goal Amount</label>
                <input v-model.number="newGoal.goalAmount" type="number" required min="0.01" step="0.01"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Current Amount</label>
                <input v-model.number="newGoal.currentAmount" type="number" min="0" step="0.01"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Deadline</label>
                <input v-model="newGoal.deadline" type="date" required :min="new Date().toISOString().split('T')[0]"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div class="flex justify-end space-x-3 mt-6">
                <button type="button" @click="showAddModal = false"
                  class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200">
                  Cancel
                </button>
                <button type="submit" class="px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                  Add Goal
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- Edit Goal Modal -->
        <div v-if="showEditModal"
          class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div class="bg-white rounded-xl shadow-xl max-w-md w-full">
            <div class="p-6 border-b">
              <div class="flex justify-between items-center">
                <h3 class="text-xl font-bold text-gray-800">Edit Goal</h3>
                <button @click="showEditModal = false" class="text-gray-500 hover:text-gray-700">
                  <i class="pi pi-times"></i>
                </button>
              </div>
            </div>

            <form @submit.prevent="updateGoal" class="p-6 space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Goal Name</label>
                <input v-model.trim="selectedGoal.goalName" type="text" required
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Goal Amount</label>
                <input v-model.number="selectedGoal.goalAmount" type="number" required min="0.01" step="0.01"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Current Amount</label>
                <input v-model.number="selectedGoal.currentAmount" type="number" min="0" step="0.01"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Deadline</label>
                <input v-model="selectedGoal.deadline" type="date" required
                  :min="new Date().toISOString().split('T')[0]"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500" />
              </div>

              <div class="flex justify-end space-x-3 mt-6">
                <button type="button" @click="showEditModal = false"
                  class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200">
                  Cancel
                </button>
                <button type="submit" class="px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                  Save Changes
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <ConfirmationModal 
          v-if="showDeleteConfirmModal" 
          title="Delete Goal"
          message="Are you sure you want to delete this goal? This action cannot be undone."
          @confirm="handleDelete"
          @cancel="showDeleteConfirmModal = false"
          :show="showDeleteConfirmModal"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1) !important;
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1) !important;
}

/* Add these specific button styles */
button.bg-custom-color {
  background-color: rgba(191, 148, 95, 1) !important;
}

button.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1) !important;
}

.bg-custom-bg {
  background-color: rgba(191, 148, 95, 0.1);
}
</style>
