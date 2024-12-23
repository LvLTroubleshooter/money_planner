<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from "@/utils/axios";

const props = defineProps({
  show: Boolean,
  userId: {
    type: [String, Number],
    required: false
  }
});

const emit = defineEmits(['close']);

const allTransactions = ref([]);

const fetchAllTransactions = async () => {
  try {
    const id = localStorage.getItem('userId');
    if (!id) return;
    const response = await axios.get(`/api/transactions/user/${id}/all`);
    allTransactions.value = response.data || [];
  } catch (error) {
    console.error('Failed to fetch all transactions:', error);
    allTransactions.value = [];
  }
};

watch(() => props.show, (newValue) => {
  if (newValue) {
    fetchAllTransactions();
  }
});

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return new Intl.RelativeTimeFormat('en', { numeric: 'auto' }).format(
    Math.ceil((date - new Date()) / (1000 * 60 * 60 * 24)),
    'day'
  );
};
</script>

<template>
  <div v-if="show" 
       class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center overflow-auto py-8"
       @click.self="$emit('close')">
    <div class="bg-white rounded-xl shadow-xl w-full max-w-4xl max-h-[80vh] flex flex-col m-4">
      <!-- Header -->
      <div class="p-4 border-b flex justify-between items-center sticky top-0 bg-white rounded-t-xl">
        <h2 class="text-xl font-semibold text-gray-800">All Transactions</h2>
        <button @click="$emit('close')" 
                class="text-gray-500 hover:text-gray-700 p-2 rounded-lg hover:bg-gray-100 transition-colors">
          <i class="pi pi-times"></i>
        </button>
      </div>

      <!-- Content -->
      <div class="flex-1 overflow-y-auto p-4">
        <div class="space-y-3">
          <div v-if="allTransactions.length === 0" 
               class="text-center text-gray-500 py-8 bg-gray-50 rounded-lg">
            No transactions found
          </div>
          <div v-for="transaction in allTransactions" 
               :key="transaction.id"
               class="flex items-center justify-between p-4 hover:bg-gray-50 rounded-lg transition-all duration-200 group">
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
</template>

<style scoped>
.fixed {
  position: fixed;
}

.overflow-auto {
  scrollbar-width: thin;
  scrollbar-color: rgba(156, 163, 175, 0.5) transparent;
}

.overflow-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-auto::-webkit-scrollbar-track {
  background: transparent;
}

.overflow-auto::-webkit-scrollbar-thumb {
  background-color: rgba(156, 163, 175, 0.5);
  border-radius: 3px;
}
</style> 