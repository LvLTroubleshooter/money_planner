<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
import DashboardNavbar from "@/components/DashboardNavbar.vue";
import GoalDeadlineOverview from "@/components/GoalDeadlineOverview.vue";
import IncomeExpenseTrends from "@/components/IncomeExpenseTrends.vue";
import axios from "@/utils/axios";
import { ref, onMounted, computed } from "vue";

const selectedFilter = ref("Week");
const incomeSources = ref([]);
const expenses = ref([]);
const latestGoals = ref([]);
const latestCategories = ref([]);
const recentTransactions = ref([]);

// Compute total income
const totalIncome = computed(() => {
  return incomeSources.value.reduce((sum, source) => sum + source.amount, 0);
});

// Compute total expenses
const totalExpenses = computed(() => {
  return expenses.value.reduce((sum, expense) => sum + expense.amount, 0);
});

// Fetch incomes
const fetchIncomes = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/incomesources/user/${userId}`);
    incomeSources.value = response.data;
  } catch (error) {
    console.error('Failed to fetch incomes:', error);
    incomeSources.value = [];
  }
};

// Fetch expenses
const fetchExpenses = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/expenses/user/${userId}`);
    expenses.value = response.data;
  } catch (error) {
    console.error('Failed to fetch expenses:', error);
    expenses.value = [];
  }
};

// Fetch goals - using the same pattern as income and expenses
const fetchLatestGoals = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/goals/user/${userId}/latest`);
    latestGoals.value = response.data || [];  // Ensure we always have an array
  } catch (error) {
    console.error('Failed to fetch goals:', error);
    latestGoals.value = [];
  }
};

// Fetch latest categories
const fetchLatestCategories = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/categories/user/${userId}/latest`);
    latestCategories.value = response.data || [];
  } catch (error) {
    console.error('Failed to fetch categories:', error);
    latestCategories.value = [];
  }
};

// Calculate goal progress percentage
const calculateProgress = (currentAmount, goalAmount) => {
  return ((currentAmount / goalAmount) * 100).toFixed(0);
};

// Fetch recent transactions
const fetchRecentTransactions = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/transactions/user/${userId}/recent`);
    recentTransactions.value = response.data || [];
  } catch (error) {
    console.error('Failed to fetch transactions:', error);
    recentTransactions.value = [];
  }
};

// Format date
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return new Intl.RelativeTimeFormat('en', { numeric: 'auto' }).format(
    Math.ceil((date - new Date()) / (1000 * 60 * 60 * 24)),
    'day'
  );
};

onMounted(() => {
  fetchIncomes();
  fetchExpenses();
  fetchLatestGoals();
  fetchLatestCategories();
  fetchRecentTransactions();
});

const handleFilterChange = (filter) => {
  selectedFilter.value = filter;
};
</script>

<template>
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />
    <div class="flex-1 ml-64">
      <DashboardNavbar @filter-change="handleFilterChange" />
      <div class="p-6 pt-24">
        <div class="grid grid-cols-12 gap-6">
          <!-- Left Column - 8 columns wide -->
          <div class="col-span-12 lg:col-span-8 space-y-6">
            <!-- Quick Stats Row -->
            <div class="grid grid-cols-2 gap-6">
              <!-- Total Income Card -->
              <div class="bg-white rounded-xl shadow-md p-4 border-l-4 border-emerald-500">
                <h3 class="text-gray-500 text-sm font-medium">Total Income</h3>
                <p class="text-2xl font-bold text-gray-800 mt-2">
                  ${{ totalIncome.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) }}
                </p>
              </div>

              <div class="bg-white rounded-xl shadow-md p-4 border-l-4 border-red-500">
                <h3 class="text-gray-500 text-sm font-medium">Total Expenses</h3>
                <p class="text-2xl font-bold text-gray-800 mt-2">
                  ${{ totalExpenses.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) }}
                </p>
              </div>
            </div>

            <!-- Main Chart -->
            <div class="bg-white rounded-xl shadow-md overflow-hidden">
              <div class="p-4">
                <div class="w-full h-[400px]">
                  <IncomeExpenseTrends :filter="selectedFilter" />
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column - 4 columns wide -->
          <div class="col-span-12 lg:col-span-4 grid grid-rows-2 gap-6 h-full">
            <!-- Goals Progress -->
            <div class="bg-white rounded-xl shadow-md p-4 flex flex-col">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Goals Progress</h3>
                <button 
                  @click="$router.push({ name: 'GoalsPage' })" 
                  class="text-sm text-gray-500 hover:text-gray-700 transition-colors">
                  View All
                </button>
              </div>
              <div class="space-y-4 flex-grow overflow-auto">
                <div v-if="latestGoals.length === 0" class="text-center text-gray-500 py-4">
                  No goals found
                </div>
                <div v-for="goal in latestGoals" :key="goal.goalId" class="space-y-2">
                  <div class="flex justify-between items-center">
                    <span class="font-medium text-gray-800">{{ goal.goalName }}</span>
                    <span class="text-sm text-gray-500">
                      ${{ goal.currentAmount.toLocaleString() }} / ${{ goal.goalAmount.toLocaleString() }}
                    </span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div 
                      class="bg-blue-500 h-2 rounded-full transition-all duration-500" 
                      :style="{ width: `${calculateProgress(goal.currentAmount, goal.goalAmount)}%` }"
                    ></div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Spending Categories -->
            <div class="bg-white rounded-xl shadow-md p-4 flex flex-col">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Top Categories</h3>
                <button 
                  @click="$router.push({ name: 'categoriesPage' })" 
                  class="text-sm text-gray-500 hover:text-gray-700 transition-colors hover:underline focus:outline-none">
                  View All
                </button>
              </div>
              <div class="space-y-3 flex-grow overflow-auto">
                <div v-if="latestCategories.length === 0" 
                     class="text-center text-gray-500 py-4 bg-gray-50 rounded-lg">
                  No categories found
                </div>
                <div v-for="category in latestCategories" 
                     :key="category.categoryId"
                     class="flex items-center p-2 hover:bg-gray-50 rounded-lg transition-all duration-200 group cursor-pointer">
                  <div class="flex items-center space-x-3">
                    <div class="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center group-hover:bg-white group-hover:shadow-sm transition-all duration-200">
                      <i :class="[`pi ${category.icon}`, 'text-gray-600']"></i>
                    </div>
                    <span class="font-medium text-gray-700 group-hover:text-gray-900 transition-colors">
                      {{ category.categoryName }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Recent Transactions - Full Width -->
          <div class="col-span-12">
            <div class="bg-white rounded-xl shadow-md p-4">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Recent Transactions</h3>
                <button class="text-sm text-gray-500 hover:text-gray-700 hover:underline focus:outline-none">
                  View All
                </button>
              </div>
              <div class="space-y-3">
                <div v-if="recentTransactions.length === 0" 
                     class="text-center text-gray-500 py-8 bg-gray-50 rounded-lg">
                  No recent transactions
                </div>
                <div v-for="transaction in recentTransactions" 
                     :key="transaction.id"
                     class="flex items-center justify-between p-4 hover:bg-gray-50 rounded-lg transition-all duration-200 group cursor-pointer">
                  <div class="flex items-center space-x-4 flex-1 min-w-0">
                    <div class="flex flex-col items-center space-y-1">
                      <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center group-hover:bg-white group-hover:shadow-sm transition-all duration-200">
                        <i :class="[
                          transaction.type === 'INCOME' ? 'pi pi-arrow-up text-green-500' : 'pi pi-arrow-down text-red-500',
                          'text-lg'
                        ]"></i>
                      </div>
                      <span class="text-xs font-medium" 
                            :class="transaction.type === 'INCOME' ? 'text-green-600' : 'text-red-600'">
                        #{{ transaction.id }}
                      </span>
                    </div>

                    <div class="flex-1 min-w-0">
                      <div class="flex items-center space-x-2">
                        <p class="font-medium text-gray-800 truncate">{{ transaction.name }}</p>
                        <span class="px-2 py-1 text-xs rounded-full" 
                              :class="transaction.type === 'INCOME' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'">
                          {{ transaction.type }}
                        </span>
                      </div>
                      <div class="flex items-center space-x-2 text-sm text-gray-500 mt-1">
                        <span>{{ formatDate(transaction.createdAt) }}</span>
                        <span>•</span>
                        <span class="truncate">
                          {{ transaction.type === 'INCOME' ? 'Income Source' : 'Category' }}
                        </span>
                      </div>
                      <div class="flex items-center space-x-4 mt-2 text-xs text-gray-500">
                        <span class="flex items-center space-x-1">
                          <i class="pi pi-clock text-gray-400"></i>
                          <span>{{ new Date(transaction.createdAt).toLocaleTimeString([], { 
                            hour: '2-digit', 
                            minute: '2-digit',
                            hour12: true 
                          }) }}</span>
                        </span>
                        <span class="flex items-center space-x-1">
                          <i class="pi pi-calendar text-gray-400"></i>
                          <span>{{ new Date(transaction.createdAt).toLocaleDateString() }}</span>
                        </span>
                        <span v-if="transaction.type === 'EXPENSE'" 
                              class="flex items-center space-x-1 bg-gray-100 px-2 py-1 rounded-full">
                          <i class="pi pi-tag text-gray-400"></i>
                          <span>{{ transaction.name }}</span>
                        </span>
                      </div>
                    </div>
                  </div>

                  <div class="text-right flex flex-col items-end">
                    <span :class="[
                      'font-medium whitespace-nowrap ml-4 text-lg',
                      transaction.type === 'INCOME' ? 'text-green-500' : 'text-red-500'
                    ]">
                      {{ transaction.type === 'INCOME' ? '+' : '-' }}${{ transaction.amount.toLocaleString() }}
                    </span>
                    <div class="mt-2 flex items-center space-x-2">
                      <span class="px-2 py-1 text-xs rounded-full bg-gray-100 text-gray-600">
                        {{ transaction.type === 'INCOME' ? 'Received' : 'Completed' }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-custom-bg {
  background-color: rgba(191, 148, 95, 0.1);
}

.rounded-xl {
  transition: all 0.3s ease;
}

.hover\:shadow-lg:hover {
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  transform: translateY(-2px);
}
</style>
