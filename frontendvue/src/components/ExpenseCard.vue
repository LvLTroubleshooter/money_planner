<script setup>
defineProps({
    expense: {
        type: Object,
        required: true,
        validator: (expense) => {
            return expense &&
                typeof expense.amount === 'number' &&
                expense.expenseId !== undefined &&
                expense.category !== undefined;
        }
    }
});

const emit = defineEmits(['edit', 'delete']);

const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString();
};
</script>

<template>
    <div
        class="group relative bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-all duration-300">
        <!-- Use category's color for the gradient bar -->
        <div :class="`bg-gradient-to-r ${expense.category?.color || 'from-blue-500 to-indigo-600'} h-2`"></div>

        <div class="p-4">
            <div class="flex items-center justify-between">
                <div class="flex items-center space-x-3">
                    <div
                        :class="`p-3 rounded-full bg-gradient-to-r ${expense.category?.color || 'from-blue-500 to-indigo-600'}`">
                        <!-- Use category's icon -->
                        <i :class="['pi text-xl text-white', expense.category?.icon || 'pi-tag']"></i>
                    </div>
                    <div>
                        <h3 class="text-lg font-semibold text-gray-800">
                            {{ expense.category?.categoryName || 'Unknown Category' }}
                        </h3>
                        <p class="text-2xl font-bold text-gray-900">${{ expense.amount }}</p>
                        <p class="text-sm text-gray-500">Date: {{ formatDate(expense.expenseDate) }}</p>
                    </div>
                </div>
                <div class="flex space-x-2">
                    <button @click="$emit('edit', expense)"
                        class="text-yellow-500 hover:text-yellow-600 transition-colors p-2 hover:bg-yellow-50 rounded-full">
                        <i class="pi pi-pencil"></i>
                    </button>
                    <button @click="$emit('delete', expense.expenseId)"
                        class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
                        <i class="pi pi-trash"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>