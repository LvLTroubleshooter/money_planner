<script setup>
import { reactive, ref, onMounted, watch } from 'vue';
import axios from '@/utils/axios';

const props = defineProps({
    expense: {
        type: Object,
        required: true
    }
});

const emit = defineEmits(['close', 'save']);
const categories = ref([]);

const updatedExpense = reactive({
    expenseId: null,
    amount: 0,
    expenseDate: '',
    category: {
        categoryId: null,
        categoryName: '',
        icon: '',
        color: ''
    },
    user: null
});

const fetchCategories = async () => {
    try {
        const userId = localStorage.getItem('userId');
        const response = await axios.get(`/api/categories/user/${userId}`);
        categories.value = response.data;
    } catch (error) {
        console.error('Failed to fetch categories:', error);
    }
};

watch(() => props.expense, (newExpense) => {
    if (newExpense) {
        updatedExpense.expenseId = newExpense.expenseId;
        updatedExpense.amount = newExpense.amount;
        updatedExpense.expenseDate = newExpense.expenseDate;
        updatedExpense.category = {
            categoryId: newExpense.category.categoryId,
            categoryName: newExpense.category.categoryName,
            icon: newExpense.category.icon,
            color: newExpense.category.color
        };
        updatedExpense.user = newExpense.user;
    }
}, { immediate: true });

const saveExpense = () => {
    if (updatedExpense.amount && updatedExpense.category && updatedExpense.expenseDate) {
        const selectedCategory = categories.value.find(
            cat => cat.categoryId === updatedExpense.category.categoryId
        );

        const expenseData = {
            ...updatedExpense,
            category: selectedCategory
        };

        emit('save', expenseData);
        emit('close');
    }
};

onMounted(fetchCategories);
</script>

<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white rounded-lg p-6 w-96 shadow-lg">
            <h3 class="text-lg font-bold text-gray-700 mb-4">Edit Expense</h3>

            <div class="mb-4">
                <label class="block text-gray-600 text-sm mb-2">Category</label>
                <select v-model="updatedExpense.category.categoryId" @change="updateCategory($event)"
                    class="w-full p-2 border rounded-lg">
                    <option v-for="category in categories" :key="category.categoryId" :value="category.categoryId">
                        {{ category.categoryName }}
                    </option>
                </select>
            </div>

            <div class="mb-4">
                <label class="block text-gray-600 text-sm mb-2">Amount</label>
                <input v-model="updatedExpense.amount" type="number" step="0.01" class="w-full p-2 border rounded-lg"
                    placeholder="Enter amount" />
            </div>

            <div class="mb-6">
                <label class="block text-gray-600 text-sm mb-2">Date</label>
                <input v-model="updatedExpense.expenseDate" type="date" class="w-full p-2 border rounded-lg" />
            </div>

            <div class="flex justify-end space-x-4">
                <button @click="$emit('close')" class="px-4 py-2 bg-gray-300 rounded-lg hover:bg-gray-400">
                    Cancel
                </button>
                <button @click="saveExpense"
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