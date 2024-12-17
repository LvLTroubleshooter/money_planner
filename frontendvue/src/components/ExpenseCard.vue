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

const getCategoryColor = (categoryName) => {
    const colors = {
        'Food': 'from-orange-500 to-red-500',
        'Transport': 'from-blue-500 to-indigo-500',
        'Entertainment': 'from-purple-500 to-pink-500',
        'Bills': 'from-yellow-500 to-orange-500',
        'Shopping': 'from-green-500 to-teal-500',
        'Health': 'from-red-500 to-pink-500',
        'Education': 'from-blue-500 to-purple-500',
        'Other': 'from-gray-500 to-slate-500'
    };
    return colors[categoryName] || 'from-gray-500 to-slate-500';
};
</script>

<template>
    <div
        class="group relative bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-all duration-300">
        <div class="absolute top-0 right-0 p-2 opacity-0 group-hover:opacity-100 transition-opacity">
            <div class="flex space-x-1">
                <button @click="$emit('edit', expense)"
                    class="p-1.5 rounded-full bg-white/90 hover:bg-yellow-50 text-yellow-600 transition-colors">
                    <i class="pi pi-pencil text-sm"></i>
                </button>
                <button @click="$emit('delete', expense.expenseId)"
                    class="p-1.5 rounded-full bg-white/90 hover:bg-red-50 text-red-600 transition-colors">
                    <i class="pi pi-trash text-sm"></i>
                </button>
            </div>
        </div>

        <div :class="`bg-gradient-to-r ${getCategoryColor(expense.category.categoryName)} h-2`"></div>

        <div class="p-4">
            <div class="flex items-center space-x-3">
                <div class="p-2.5 rounded-lg bg-gray-100">
                    <i :class="['pi text-xl', getIconByCategory(expense.category.categoryName)]"></i>
                </div>
                <div class="flex-grow">
                    <h3 class="font-semibold text-gray-800">{{ expense.category.categoryName }}</h3>
                    <p class="text-sm text-gray-500">{{ new Date(expense.expenseDate).toLocaleDateString('en-US', {
                        month: 'short',
                        day: 'numeric',
                        year: 'numeric'
                    }) }}</p>
                </div>
                <div class="text-right">
                    <p class="text-lg font-bold text-gray-800">${{ expense.amount.toFixed(2) }}</p>
                </div>
            </div>
        </div>
    </div>
</template>