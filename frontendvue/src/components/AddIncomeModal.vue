<script setup>
import { ref } from 'vue';

const emit = defineEmits(['close', 'save']);

const newIncome = ref({
    sourceName: '',
    amount: ''
});

const saveIncome = () => {
    if (newIncome.value.sourceName && newIncome.value.amount) {
        emit('save', {
            sourceName: newIncome.value.sourceName,
            amount: parseFloat(newIncome.value.amount)
        });
        emit('close');
    }
};
</script>

<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white rounded-lg p-6 w-96 shadow-lg">
            <h3 class="text-lg font-bold text-gray-700 mb-4">Add New Income Source</h3>

            <div class="mb-4">
                <label class="block text-gray-600 text-sm mb-2">Source Name</label>
                <input v-model="newIncome.sourceName" type="text" class="w-full p-2 border rounded-lg"
                    placeholder="Enter source name" />
            </div>

            <div class="mb-6">
                <label class="block text-gray-600 text-sm mb-2">Amount</label>
                <input v-model="newIncome.amount" type="number" step="0.01" class="w-full p-2 border rounded-lg"
                    placeholder="Enter amount" />
            </div>

            <div class="flex justify-end space-x-4">
                <button @click="$emit('close')" class="px-4 py-2 bg-gray-300 rounded-lg hover:bg-gray-400">
                    Cancel
                </button>
                <button @click="saveIncome"
                    class="px-4 py-2 bg-custom-color text-white rounded-lg hover:bg-custom-hover-color">
                    Save
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.bg-custom-color {
    background-color: rgba(191, 148, 95, 1);
}

.hover\:bg-custom-hover-color:hover {
    background-color: rgba(164, 120, 65, 1);
}
</style>