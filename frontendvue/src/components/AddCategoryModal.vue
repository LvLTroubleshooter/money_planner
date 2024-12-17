<script setup>
import { ref } from 'vue';

const emit = defineEmits(['close', 'save']);

const newCategory = ref({
    categoryName: ''
});

const saveCategory = () => {
    if (newCategory.value.categoryName.trim()) {
        emit('save', {
            categoryName: newCategory.value.categoryName.trim()
        });
        emit('close');
    }
};
</script>

<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white rounded-lg p-6 w-96 shadow-lg">
            <div class="flex justify-between items-center mb-4">
                <h3 class="text-lg font-bold text-gray-800">Add New Category</h3>
                <button @click="$emit('close')" class="text-gray-500 hover:text-gray-700">
                    <i class="pi pi-times"></i>
                </button>
            </div>

            <div class="mb-4">
                <label class="block text-gray-700 text-sm font-bold mb-2">Category Name</label>
                <input v-model="newCategory.categoryName" type="text"
                    class="w-full p-2 border rounded-lg focus:border-blue-500 focus:ring-0"
                    placeholder="Enter category name" />
            </div>

            <div class="flex justify-end space-x-4">
                <button @click="$emit('close')" class="px-4 py-2 bg-gray-300 rounded-lg hover:bg-gray-400">
                    Cancel
                </button>
                <button @click="saveCategory"
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