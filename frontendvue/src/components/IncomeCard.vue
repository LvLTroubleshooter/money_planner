<script setup>
defineProps({
    income: {
        type: Object,
        required: true,
        validator: (income) => {
            return income &&
                typeof income.sourceName === 'string' &&
                typeof income.amount === 'number' &&
                income.sourceId !== undefined;
        }
    }
});

const emit = defineEmits(['edit', 'delete']);
</script>

<template>
    <div class="bg-white shadow-md rounded-lg p-4 relative hover:shadow-lg transition-shadow">
        <div class="flex justify-between items-center">
            <div>
                <h3 class="text-lg font-bold text-gray-700">{{ income.sourceName }}</h3>
                <p class="text-sm text-gray-500 mt-1">Amount: ${{ income.amount.toFixed(2) }}</p>
                <p class="text-xs text-gray-400">Added: {{ new Date(income.createdAt).toLocaleDateString() }}</p>
            </div>

            <!-- Action Icons -->
            <div class="flex space-x-2">
                <button @click="$emit('edit', income)" class="text-yellow-500 hover:text-yellow-600 transition-colors"
                    title="Edit Income">
                    <i class="pi pi-pencil text-xl"></i>
                </button>
                <button @click="$emit('delete', income.sourceId)"
                    class="text-red-500 hover:text-red-600 transition-colors" title="Delete Income">
                    <i class="pi pi-trash text-xl"></i>
                </button>
            </div>
        </div>
    </div>
</template>