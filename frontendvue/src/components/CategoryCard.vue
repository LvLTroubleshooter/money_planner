<script setup>
defineProps({
    category: {
        type: Object,
        required: true,
        validator: (category) => {
            return category &&
                typeof category.categoryName === 'string' &&
                category.categoryId !== undefined;
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
        <div :class="[
            'h-2',
            category.color.includes('from-') 
                ? `bg-gradient-to-r ${category.color}`
                : category.color
        ]"></div>

        <div class="p-4">
            <div class="flex items-center justify-between">
                <div class="flex items-center space-x-3">
                    <div :class="[
                        'p-3 rounded-full',
                        category.color.includes('from-') 
                            ? `bg-gradient-to-r ${category.color}`
                            : category.color
                    ]">
                        <i :class="['pi text-xl text-white', category.icon]"></i>
                    </div>
                    <div>
                        <h3 class="text-lg font-semibold text-gray-800">{{ category.categoryName }}</h3>
                        <p class="text-sm text-gray-500">Created: {{ formatDate(category.createdAt) }}</p>
                    </div>
                </div>
                <div class="flex space-x-2">
                    <button @click="$emit('edit', {...category})"
                        class="text-yellow-500 hover:text-yellow-600 transition-colors p-2 hover:bg-yellow-50 rounded-full">
                        <i class="pi pi-pencil"></i>
                    </button>
                    <button @click="$emit('delete', category.categoryId)"
                        class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
                        <i class="pi pi-trash"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>