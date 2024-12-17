<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import IncomeNavbar from "@/components/IncomeNavbar.vue";
import IncomeCard from "@/components/IncomeCard.vue";
import AddIncomeModal from "@/components/AddIncomeModal.vue";
import EditIncomeModal from "@/components/EditIncomeModal.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";

const incomes = ref([]);
const showAddModal = ref(false);
const showEditModal = ref(false);
const showConfirmModal = ref(false);
const selectedIncome = ref(null);
const incomeToDelete = ref(null);

// Fetch incomes
const fetchIncomes = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/incomesources/user/${userId}`);
    incomes.value = response.data;
  } catch (error) {
    console.error('Failed to fetch incomes:', error);
  }
};

// Add income
const addIncome = async (newIncome) => {
  try {
    const userId = localStorage.getItem('userId');
    const incomeData = {
      ...newIncome,
      user: { userId: parseInt(userId) }
    };

    const response = await axios.post('/api/incomesources/create', incomeData);
    incomes.value.push(response.data);
    showAddModal.value = false;
  } catch (error) {
    console.error('Failed to add income:', error);
  }
};

// Update income
const updateIncome = async (updatedIncome) => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.put(
      `/api/incomesources/user/${userId}/${updatedIncome.sourceId}`,
      updatedIncome
    );
    const index = incomes.value.findIndex((income) => income.sourceId === updatedIncome.sourceId);
    if (index !== -1) {
      incomes.value[index] = response.data;
    }
    showEditModal.value = false;
  } catch (error) {
    console.error('Failed to update income:', error);
  }
};

// Delete income
const deleteIncome = async (sourceId) => {
  try {
    await axios.delete(`/api/incomesources/${sourceId}`);
    incomes.value = incomes.value.filter((income) => income.sourceId !== sourceId);
  } catch (error) {
    console.error('Failed to delete income:', error);
  }
};

// Handle delete confirmation
const confirmDelete = async () => {
  if (incomeToDelete.value) {
    await deleteIncome(incomeToDelete.value);
    incomeToDelete.value = null;
    showConfirmModal.value = false;
  }
};

const handleDeleteClick = (sourceId) => {
  incomeToDelete.value = sourceId;
  showConfirmModal.value = true;
};

const openEditModal = (income) => {
  selectedIncome.value = { ...income };
  showEditModal.value = true;
};

onMounted(() => {
  fetchIncomes();
});
</script>

<template>
  <div class="flex bg-gray-200 min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <IncomeNavbar @add-income="showAddModal = true" />

      <div class="p-6 pt-20">
        <div class="space-y-4">
          <IncomeCard v-for="income in incomes" :key="income.sourceId" :income="income" @edit="openEditModal"
            @delete="handleDeleteClick" />
        </div>
      </div>
    </div>

    <!-- Modals -->
    <AddIncomeModal v-if="showAddModal" @close="showAddModal = false" @save="addIncome" />

    <EditIncomeModal v-if="showEditModal" :income="selectedIncome" @close="showEditModal = false"
      @save="updateIncome" />

    <ConfirmationModal v-if="showConfirmModal" title="Delete Income Source"
      message="Are you sure you want to delete this income source? This action cannot be undone."
      @confirm="confirmDelete" @cancel="showConfirmModal = false" :show="showConfirmModal" />
  </div>
</template>