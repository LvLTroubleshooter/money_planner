<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import GoalsNavbar from "@/components/GoalsNavbar.vue";

const goals = ref([]);
const showAddModal = ref(false);
const showEditModal = ref(false);
const selectedGoal = ref(null);
const error = ref('');
const loading = ref(false);

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
    console.error('Failed to fetch goals:', err);
    error.value = err.response?.data?.error || err.message || 'Failed to fetch goals';
  } finally {
    loading.value = false;
  }
};

// Validate goal data
const validateGoal = (goal) => {
  console.log('Validating goal:', goal);

  if (!goal.goalName?.trim()) {
    throw new Error('Goal name is required');
  }

  const goalAmount = parseFloat(goal.goalAmount);
  console.log('Parsed goal amount:', goalAmount);
  if (isNaN(goalAmount) || goalAmount <= 0) {
    throw new Error('Goal amount must be greater than 0');
  }

  const currentAmount = parseFloat(goal.currentAmount || 0);
  console.log('Parsed current amount:', currentAmount);
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

    // Add detailed logging
    console.log('Validating goal data:', {
      goalName: goalData.goalName,
      goalAmount: goalData.goalAmount,
      currentAmount: goalData.currentAmount,
      deadline: goalData.deadline,
      userId: goalData.user.userId
    });

    // Check if any values are undefined or null
    if (!goalData.goalName || !goalData.goalAmount || !goalData.deadline) {
      throw new Error('Required fields are missing');
    }

    const response = await axios.post('/api/goals/create', goalData);
    console.log('Response from server:', response.data);

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
const deleteGoal = async (goalId) => {
  try {
    const userId = localStorage.getItem('userId');
    await axios.delete(`/api/goals/user/${userId}/goal/${goalId}`);
    goals.value = goals.value.filter(goal => goal.goalId !== goalId);
  } catch (error) {
    console.error('Failed to delete goal:', error);
  }
};

onMounted(() => {
  fetchGoals();
});
</script>

<template>
  <div class="flex bg-gray-100 min-h-screen">
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
        <div v-else-if="goals.length === 0" class="text-center py-8">
          <p class="text-gray-500 mb-4">No goals found. Start by adding a new goal!</p>
          <button @click="showAddModal = true"
            class="px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600 transition-colors">
            Add Your First Goal
          </button>
        </div>

        <!-- Goals Grid -->
        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="goal in goals" :key="goal.goalId"
            class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-all duration-300">
            <!-- Progress Bar -->
            <div class="relative h-2">
              <div class="absolute inset-0 bg-gray-200"></div>
              <div class="absolute inset-0 bg-gradient-to-r from-blue-500 to-indigo-500"
                :style="{ width: `${(goal.currentAmount / goal.goalAmount) * 100}%` }"></div>
            </div>

            <div class="p-6">
              <!-- Goal Header -->
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-xl font-bold text-gray-800">{{ goal.goalName }}</h3>
                <div class="flex space-x-2">
                  <button @click="selectedGoal = { ...goal }; showEditModal = true"
                    class="text-yellow-500 hover:text-yellow-600 transition-colors p-2 hover:bg-yellow-50 rounded-full">
                    <i class="pi pi-pencil"></i>
                  </button>
                  <button @click="deleteGoal(goal.goalId)"
                    class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
                    <i class="pi pi-trash"></i>
                  </button>
                </div>
              </div>

              <!-- Goal Progress -->
              <div class="space-y-4">
                <div class="flex justify-between items-baseline">
                  <span class="text-3xl font-bold text-gray-900">${{ goal.currentAmount }}</span>
                  <span class="text-gray-500">of ${{ goal.goalAmount }}</span>
                </div>
                <div class="text-sm text-gray-500">
                  Deadline: {{ new Date(goal.deadline).toLocaleDateString() }}
                </div>
                <div class="text-sm font-medium text-gray-700">
                  {{ ((goal.currentAmount / goal.goalAmount) * 100).toFixed(1) }}% Complete
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
      </div>
    </div>
  </div>
</template>

<style scoped></style>
