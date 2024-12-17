<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/utils/axios';

const emit = defineEmits(['close', 'save']);
const categories = ref([]);
const activeStep = ref(1);
const error = ref('');

const newExpense = ref({
    amount: '',
    expenseDate: new Date().toISOString().split('T')[0],
    categoryId: ''
});

const fetchCategories = async () => {
    try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
            error.value = 'No user ID found. Please log in again.';
            return;
        }
        const response = await axios.get(`/api/categories/user/${userId}`);
        categories.value = response.data;
    } catch (error) {
        console.error('Failed to fetch categories:', error);
        error.value = 'Failed to load categories. Please try again.';
    }
};

const saveExpense = () => {
    if (newExpense.value.amount && newExpense.value.categoryId && newExpense.value.expenseDate) {
        try {
            const userId = localStorage.getItem('userId');
            if (!userId) {
                error.value = 'No user ID found. Please log in again.';
                return;
            }

            emit('save', {
                amount: parseFloat(newExpense.value.amount),
                expenseDate: newExpense.value.expenseDate,
                category: { categoryId: parseInt(newExpense.value.categoryId) },
                user: { userId: parseInt(userId) }
            });
            emit('close');
        } catch (error) {
            console.error('Failed to save expense:', error);
        }
    }
};

const nextStep = () => {
    if (activeStep.value < 3) activeStep.value++;
};

const prevStep = () => {
    if (activeStep.value > 1) activeStep.value--;
};

const getIconByCategory = (categoryName) => {
    const icons = {
        'Food': 'pi-shopping-cart',
        'Transport': 'pi-car',
        'Entertainment': 'pi-ticket',
        'Bills': 'pi-file',
        'Shopping': 'pi-shopping-bag',
        'Health': 'pi-heart',
        'Education': 'pi-book',
        'Other': 'pi-box'
    };
    return icons[categoryName] || 'pi-box';
};

onMounted(fetchCategories);
</script>

<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
        <div class="bg-white rounded-2xl p-6 w-[480px] shadow-xl">
            <div class="flex justify-between items-center mb-6">
                <h3 class="text-xl font-bold text-gray-800">Record New Expense</h3>
                <button @click="$emit('close')" class="text-gray-500 hover:text-gray-700">
                    <i class="pi pi-times"></i>
                </button>
            </div>

            <!-- Error Message -->
            <div v-if="error" class="mb-4 p-3 bg-red-100 text-red-700 rounded-lg">
                {{ error }}
            </div>

            <!-- Progress Steps -->
            <div class="flex justify-between mb-8 relative">
                <div class="absolute top-1/2 left-0 right-0 h-0.5 bg-gray-200 -translate-y-1/2"></div>
                <div class="absolute top-1/2 left-0 right-0 h-0.5 bg-blue-500 -translate-y-1/2 transition-all"
                    :style="{ width: `${((activeStep - 1) / 2) * 100}%` }"></div>
                <div v-for="step in 3" :key="step"
                    :class="['relative z-10 flex items-center justify-center w-8 h-8 rounded-full transition-colors', activeStep >= step ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-600']">
                    {{ step }}
                </div>
            </div>

            <!-- Step Content -->
            <div class="mb-6">
                <!-- Step 1: Select Category -->
                <div v-if="activeStep === 1">
                    <div v-if="categories.length === 0" class="text-center py-4">
                        <p class="text-gray-600 mb-4">No categories found. Please add categories first.</p>
                        <router-link to="/categories_page"
                            class="inline-block px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
                            Add Categories
                        </router-link>
                    </div>
                    <div v-else class="grid grid-cols-2 gap-4">
                        <button v-for="category in categories" :key="category.categoryId"
                            @click="newExpense.categoryId = category.categoryId; nextStep()"
                            :class="['p-4 rounded-xl border-2 transition-all hover:border-blue-500 hover:bg-blue-50', newExpense.categoryId === category.categoryId ? 'border-blue-500 bg-blue-50' : 'border-gray-200']">
                            <i :class="['pi text-2xl mb-2', getIconByCategory(category.categoryName)]"></i>
                            <p class="font-medium">{{ category.categoryName }}</p>
                        </button>
                    </div>
                </div>

                <!-- Step 2: Enter Amount -->
                <div v-if="activeStep === 2" class="text-center">
                    <div class="mb-4">
                        <label class="text-2xl font-bold text-gray-800 mb-2 block">Amount</label>
                        <div class="relative inline-block">
                            <span class="absolute left-4 top-1/2 -translate-y-1/2 text-2xl text-gray-500">$</span>
                            <input v-model="newExpense.amount" type="number" step="0.01"
                                class="text-4xl font-bold text-center w-64 p-4 border-2 border-gray-200 rounded-xl focus:border-blue-500 focus:ring-0"
                                placeholder="0.00" />
                        </div>
                    </div>
                </div>

                <!-- Step 3: Select Date -->
                <div v-if="activeStep === 3">
                    <label class="block text-gray-700 text-sm font-bold mb-2">Date of Expense</label>
                    <input v-model="newExpense.expenseDate" type="date"
                        class="w-full p-3 border-2 border-gray-200 rounded-xl focus:border-blue-500 focus:ring-0" />
                </div>
            </div>

            <!-- Navigation Buttons -->
            <div class="flex justify-between">
                <button v-if="activeStep > 1" @click="prevStep"
                    class="px-6 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200">
                    Back
                </button>
                <div class="flex-1"></div>
                <button v-if="activeStep < 3 && categories.length > 0" @click="nextStep"
                    class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
                    Next
                </button>
                <button v-if="activeStep === 3" @click="saveExpense"
                    class="px-6 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600">
                    Save Expense
                </button>
            </div>
        </div>
    </div>
</template>