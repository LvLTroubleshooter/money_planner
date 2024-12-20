<script setup>
import { ref, onMounted, computed } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import IncomeNavbar from "@/components/IncomeNavbar.vue";
import IncomeCard from "@/components/IncomeCard.vue";
import AddIncomeModal from "@/components/AddIncomeModal.vue";
import EditIncomeModal from "@/components/EditIncomeModal.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";

const incomeSources = ref([]);
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
    incomeSources.value = response.data;
  } catch (error) {
    console.error('Failed to fetch incomes:', error);
  }
};

// Compute total income
const totalIncome = computed(() => {
  return incomeSources.value.reduce((sum, source) => sum + source.amount, 0);
});

// Add income
const addIncomeSource = async (newIncome) => {
  try {
    const userId = localStorage.getItem('userId');
    const incomeData = {
      ...newIncome,
      user: { userId: parseInt(userId) }
    };

    const response = await axios.post('/api/incomesources/create', incomeData);
    incomeSources.value.push(response.data);
    showAddModal.value = false;
  } catch (error) {
    console.error('Failed to add income:', error);
  }
};

// Update income
const updateIncomeSource = async (updatedIncome) => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.put(
      `/api/incomesources/user/${userId}/${updatedIncome.sourceId}`,
      updatedIncome
    );
    const index = incomeSources.value.findIndex((source) => source.sourceId === updatedIncome.sourceId);
    if (index !== -1) {
      incomeSources.value[index] = response.data;
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
    incomeSources.value = incomeSources.value.filter((source) => source.sourceId !== sourceId);
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
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <IncomeNavbar @add-income="showAddModal = true" />

      <div class="p-6 pt-20">
        <!-- Income Summary Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
          <!-- Total Monthly Income Card -->
          <div class="bg-gradient-to-br from-amber-400 to-orange-500 rounded-xl shadow-lg p-4 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-base font-medium opacity-90">Total Monthly Income</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-dollar text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-1">${{ totalIncome.toFixed(2) }}</div>
            <div class="text-xs opacity-75">Combined income from all sources</div>
          </div>

          <!-- Income Sources Count Card -->
          <div class="bg-gradient-to-br from-rose-400 to-pink-500 rounded-xl shadow-lg p-4 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-base font-medium opacity-90">Income Sources</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-wallet text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-1">{{ incomeSources.length }}</div>
            <div class="text-xs opacity-75">Active income streams</div>
          </div>
        </div>

        <!-- Income Sources Grid -->
        <div v-if="incomeSources.length === 0" class="text-center py-12">
          <div class="bg-white rounded-2xl p-8 shadow-md max-w-md mx-auto">
            <div class="text-gray-400 mb-4">
              <i class="pi pi-wallet text-5xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-gray-700 mb-2">No Income Sources Yet</h3>
            <p class="text-gray-500 mb-6">Start by adding your first income source!</p>
            <button @click="showAddModal = true"
              class="bg-custom-color text-white px-6 py-2 rounded-lg shadow-md hover:bg-custom-hover-color transition-all">
              Add Income Source
            </button>
          </div>
        </div>

        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="source in incomeSources" :key="source.sourceId"
            class="bg-white rounded-2xl shadow-md overflow-hidden hover:shadow-lg transition-all duration-300 group">
            <!-- Card Header with Gradient -->
            <div class="h-2 bg-gradient-to-r from-amber-400 to-orange-500"></div>

            <div class="p-6">
              <!-- Source Header -->
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-xl font-bold text-gray-800">{{ source.sourceName }}</h3>
                <div class="flex space-x-2 opacity-0 group-hover:opacity-100 transition-opacity">
                  <button @click="openEditModal(source)"
                    class="text-yellow-500 hover:text-yellow-600 transition-colors p-2 hover:bg-yellow-50 rounded-full">
                    <i class="pi pi-pencil"></i>
                  </button>
                  <button @click="handleDeleteClick(source.sourceId)"
                    class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
                    <i class="pi pi-trash"></i>
                  </button>
                </div>
              </div>

              <!-- Amount Section -->
              <div class="space-y-4">
                <div class="flex items-baseline justify-between">
                  <span class="text-3xl font-bold text-gray-900">${{ source.amount.toFixed(2) }}</span>
                  <span class="text-sm text-gray-500">per month</span>
                </div>
                <div class="text-sm text-gray-500">
                  Added on {{ new Date(source.createdAt).toLocaleDateString() }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Modals -->
    <AddIncomeModal v-if="showAddModal" @close="showAddModal = false" @save="addIncomeSource" />

    <EditIncomeModal v-if="showEditModal" :income="selectedIncome" @close="showEditModal = false"
      @save="updateIncomeSource" />

    <ConfirmationModal v-if="showConfirmModal" title="Delete Income Source"
      message="Are you sure you want to delete this income source? This action cannot be undone."
      @confirm="confirmDelete" @cancel="showConfirmModal = false" :show="showConfirmModal" />
  </div>
</template>

<style scoped>
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1);
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1);
}

.bg-custom-bg {
  background-color: rgba(191, 148, 95, 0.1);
}
</style>