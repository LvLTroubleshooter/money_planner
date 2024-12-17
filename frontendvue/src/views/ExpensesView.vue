<script setup>
import { ref, onMounted, computed } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import ExpensesNavbar from "@/components/ExpensesNavbar.vue";
import ExpenseCard from "@/components/ExpenseCard.vue";
import AddExpenseModal from "@/components/AddExpenseModal.vue";
import EditExpenseModal from "@/components/EditExpenseModal.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";

const expenses = ref([]);
const showAddModal = ref(false);
const showEditModal = ref(false);
const showConfirmModal = ref(false);
const selectedExpense = ref(null);
const expenseToDelete = ref(null);

// Fetch expenses
const fetchExpenses = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/expenses/user/${userId}`);
    expenses.value = response.data;
  } catch (error) {
    console.error('Failed to fetch expenses:', error);
  }
};

// Add expense
const addExpense = async (newExpense) => {
  try {
    const userId = localStorage.getItem('userId');
    const expenseData = {
      ...newExpense,
      user: { userId: parseInt(userId) }
    };

    console.log('Sending expense data:', JSON.stringify(expenseData, null, 2));

    const response = await axios.post('/api/expenses/create', expenseData);
    console.log('Created expense:', response.data);
    await fetchExpenses();
    showAddModal.value = false;
  } catch (error) {
    console.error('Failed to add expense:', error);
    console.error('Error details:', {
      message: error.message,
      status: error.response?.status,
      statusText: error.response?.statusText,
      data: error.response?.data,
      url: error.config?.url,
      method: error.config?.method,
      requestData: error.config?.data
    });

    let errorMessage = 'Failed to add expense. ';
    if (error.response?.data?.error) {
      errorMessage += error.response.data.error;
    } else if (error.message) {
      errorMessage += error.message;
    }
    alert(errorMessage);
  }
};

// Update expense
const updateExpense = async (updatedExpense) => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.put(
      `/api/expenses/user/${userId}/expense/${updatedExpense.expenseId}`,
      updatedExpense
    );
    const index = expenses.value.findIndex((expense) => expense.expenseId === updatedExpense.expenseId);
    if (index !== -1) {
      expenses.value[index] = response.data;
    }
    showEditModal.value = false;
  } catch (error) {
    console.error('Failed to update expense:', error);
  }
};

// Delete expense
const deleteExpense = async (expenseId) => {
  try {
    const userId = localStorage.getItem('userId');
    await axios.delete(`/api/expenses/user/${userId}/expense/${expenseId}`);
    expenses.value = expenses.value.filter((expense) => expense.expenseId !== expenseId);
  } catch (error) {
    console.error('Failed to delete expense:', error);
  }
};

// Handle delete confirmation
const confirmDelete = async () => {
  if (expenseToDelete.value) {
    await deleteExpense(expenseToDelete.value);
    expenseToDelete.value = null;
    showConfirmModal.value = false;
  }
};

const handleDeleteClick = (expenseId) => {
  expenseToDelete.value = expenseId;
  showConfirmModal.value = true;
};

const openEditModal = (expense) => {
  selectedExpense.value = { ...expense };
  showEditModal.value = true;
};

// Add computed properties for summary cards
const totalExpenses = computed(() => {
  return expenses.value.reduce((sum, expense) => sum + expense.amount, 0);
});

const monthlyExpenses = computed(() => {
  const currentMonth = new Date().getMonth();
  return expenses.value
    .filter(expense => new Date(expense.expenseDate).getMonth() === currentMonth)
    .reduce((sum, expense) => sum + expense.amount, 0);
});

const todayExpenses = computed(() => {
  const today = new Date().toISOString().split('T')[0];
  return expenses.value
    .filter(expense => expense.expenseDate === today)
    .reduce((sum, expense) => sum + expense.amount, 0);
});

onMounted(() => {
  fetchExpenses();
});
</script>

<template>
  <div class="flex bg-gray-100 min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <ExpensesNavbar @add-expense="showAddModal = true" />

      <div class="p-6 pt-20">
        <!-- Expenses Summary Cards -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <!-- Total Expenses Card -->
          <div class="bg-gradient-to-br from-purple-500 to-indigo-600 rounded-2xl shadow-xl p-6 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-lg font-medium opacity-90">Total Expenses</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-dollar text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-2">${{ totalExpenses.toFixed(2) }}</div>
            <div class="text-sm opacity-75">All time expenses</div>
          </div>

          <!-- This Month's Expenses Card -->
          <div class="bg-gradient-to-br from-blue-500 to-cyan-500 rounded-2xl shadow-xl p-6 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-lg font-medium opacity-90">This Month</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-calendar text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-2">${{ monthlyExpenses.toFixed(2) }}</div>
            <div class="text-sm opacity-75">Expenses for {{ new Date().toLocaleString('default', { month: 'long' }) }}
            </div>
          </div>

          <!-- Today's Expenses Card -->
          <div class="bg-gradient-to-br from-emerald-500 to-teal-500 rounded-2xl shadow-xl p-6 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-lg font-medium opacity-90">Today</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-clock text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-2">${{ todayExpenses.toFixed(2) }}</div>
            <div class="text-sm opacity-75">Expenses for {{ new Date().toLocaleDateString() }}</div>
          </div>
        </div>

        <!-- Expenses Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <ExpenseCard v-for="expense in expenses" :key="expense.expenseId" :expense="expense" @edit="openEditModal"
            @delete="handleDeleteClick" />
        </div>
      </div>
    </div>

    <!-- Modals -->
    <AddExpenseModal v-if="showAddModal" @close="showAddModal = false" @save="addExpense" />

    <EditExpenseModal v-if="showEditModal" :expense="selectedExpense" @close="showEditModal = false"
      @save="updateExpense" />

    <ConfirmationModal v-if="showConfirmModal" title="Delete Expense"
      message="Are you sure you want to delete this expense? This action cannot be undone." @confirm="confirmDelete"
      @cancel="showConfirmModal = false" :show="showConfirmModal" />
  </div>
</template>