<script setup>
// ... existing imports ...
import { ref, onMounted } from "vue";

// ... existing code ...

// Add new ref for confirmation modal
const showDeleteConfirmModal = ref(false);
const goalToDelete = ref(null);

// Modify delete goal function
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

// ... rest of the existing code ...
</script>

<template>
  <!-- ... existing template ... -->
  
  <!-- Modify the delete button in the goals grid -->
  <button @click="confirmDelete(goal)"
    class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
    <i class="pi pi-trash"></i>
  </button>

  <!-- Add Delete Confirmation Modal -->
  <div v-if="showDeleteConfirmModal"
    class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-xl shadow-xl max-w-md w-full p-6">
      <div class="text-center">
        <div class="w-16 h-16 rounded-full bg-red-100 flex items-center justify-center mx-auto mb-4">
          <i class="pi pi-exclamation-triangle text-red-500 text-2xl"></i>
        </div>
        <h3 class="text-xl font-bold text-gray-900 mb-2">Delete Goal</h3>
        <p class="text-gray-500 mb-6">
          Are you sure you want to delete "{{ goalToDelete?.goalName }}"? This action cannot be undone.
        </p>
        <div class="flex justify-center space-x-3">
          <button @click="showDeleteConfirmModal = false"
            class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors">
            Cancel
          </button>
          <button @click="handleDelete"
            class="px-4 py-2 text-white bg-red-500 rounded-lg hover:bg-red-600 transition-colors">
            Delete Goal
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- ... rest of existing modals ... -->
</template>

<style scoped>
/* ... existing styles ... */

/* Add animation for the confirmation modal */
.fixed {
  animation: fadeIn 0.2s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* Add hover effect for delete button */
.bg-red-500:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style> 