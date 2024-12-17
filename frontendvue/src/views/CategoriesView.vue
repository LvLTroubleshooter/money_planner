<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import CategoriesNavbar from "@/components/CategoriesNavbar.vue";
import CategoryCard from "@/components/CategoryCard.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";

const categories = ref([]);
const showAddModal = ref(false);
const showEditModal = ref(false);
const showConfirmModal = ref(false);
const selectedCategory = ref(null);
const categoryToDelete = ref(null);

// Available icons and colors for categories
const availableIcons = {
  'Shopping Cart': 'pi-shopping-cart',
  'Car': 'pi-car',
  'Ticket': 'pi-ticket',
  'File': 'pi-file',
  'Shopping Bag': 'pi-shopping-bag',
  'Heart': 'pi-heart',
  'Book': 'pi-book',
  'Box': 'pi-box',
  'Home': 'pi-home',
  'Wallet': 'pi-wallet',
  'Gift': 'pi-gift',
  'Globe': 'pi-globe',
  'Phone': 'pi-phone',
  'Desktop': 'pi-desktop',
  'Camera': 'pi-camera',
  'Plane': 'pi-send'
};

const availableColors = {
  'Orange to Red': 'from-orange-500 to-red-500',
  'Blue to Indigo': 'from-blue-500 to-indigo-500',
  'Purple to Pink': 'from-purple-500 to-pink-500',
  'Yellow to Orange': 'from-yellow-500 to-orange-500',
  'Green to Teal': 'from-green-500 to-teal-500',
  'Red to Pink': 'from-red-500 to-pink-500',
  'Blue to Purple': 'from-blue-500 to-purple-500',
  'Teal to Cyan': 'from-teal-500 to-cyan-500',
  'Indigo to Purple': 'from-indigo-500 to-purple-500',
  'Pink to Rose': 'from-pink-500 to-rose-500'
};

const newCategory = ref({
  categoryName: '',
  icon: 'pi-shopping-cart',
  color: 'from-blue-500 to-indigo-500'
});

// Add category
const addCategory = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const categoryData = {
      categoryName: newCategory.value.categoryName,
      icon: newCategory.value.icon,
      color: newCategory.value.color,
      user: { userId: parseInt(userId) }
    };

    const response = await axios.post('/api/categories/create', categoryData);
    categories.value.push(response.data);
    showAddModal.value = false;
    newCategory.value = {
      categoryName: '',
      icon: 'pi-shopping-cart',
      color: 'from-blue-500 to-indigo-500'
    };
  } catch (error) {
    console.error('Failed to add category:', error);
  }
};

// Fetch categories
const fetchCategories = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/categories/user/${userId}`);
    categories.value = response.data;
  } catch (error) {
    console.error('Failed to fetch categories:', error);
  }
};

// Handle delete confirmation
const handleDeleteClick = (categoryId) => {
  categoryToDelete.value = categoryId;
  showConfirmModal.value = true;
};

const confirmDelete = async () => {
  try {
    const userId = localStorage.getItem('userId');
    await axios.delete(`/api/categories/user/${userId}/category/${categoryToDelete.value}`);
    categories.value = categories.value.filter(
      category => category.categoryId !== categoryToDelete.value
    );
    showConfirmModal.value = false;
    categoryToDelete.value = null;
  } catch (error) {
    console.error('Failed to delete category:', error);
    alert('Failed to delete category. Please try again.');
  }
};

// Handle edit
const openEditModal = (category) => {
  selectedCategory.value = {
    ...category,
    icon: category.icon || 'pi-box',
    color: category.color || 'from-gray-500 to-slate-500'
  };
  showEditModal.value = true;
};

const updateCategory = async (updatedCategory) => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.put(
      `/api/categories/user/${userId}/category/${updatedCategory.categoryId}`,
      updatedCategory
    );
    const index = categories.value.findIndex(
      category => category.categoryId === updatedCategory.categoryId
    );
    if (index !== -1) {
      categories.value[index] = response.data;
    }
    showEditModal.value = false;
  } catch (error) {
    console.error('Failed to update category:', error);
    alert('Failed to update category. Please try again.');
  }
};

onMounted(() => {
  fetchCategories();
});
</script>

<template>
  <div class="flex bg-gray-100 min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <CategoriesNavbar @add-category="showAddModal = true" />

      <div class="p-6 pt-20">
        <!-- Add Category Modal -->
        <div v-if="showAddModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div class="bg-white rounded-xl shadow-xl max-w-2xl w-full max-h-[90vh] flex flex-col">
            <!-- Header -->
            <div class="p-6 border-b">
              <div class="flex justify-between items-center">
                <h3 class="text-xl font-bold text-gray-800">Add New Category</h3>
                <button @click="showAddModal = false" class="text-gray-500 hover:text-gray-700">
                  <i class="pi pi-times"></i>
                </button>
              </div>
            </div>

            <!-- Scrollable Content -->
            <div class="p-6 overflow-y-auto flex-1">
              <div class="space-y-6">
                <!-- Category Name -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Category Name
                  </label>
                  <input v-model="newCategory.categoryName" type="text"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    placeholder="Enter category name" />
                </div>

                <!-- Icon Selection -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Select Icon
                  </label>
                  <div class="grid grid-cols-4 gap-3 max-h-48 overflow-y-auto p-1">
                    <button v-for="(icon, name) in availableIcons" :key="name" @click="newCategory.icon = icon" :class="['p-3 rounded-lg border-2 transition-all hover:bg-blue-50',
                      newCategory.icon === icon ? 'border-blue-500 bg-blue-50' : 'border-gray-200']">
                      <i :class="['pi text-xl', icon]"></i>
                      <div class="text-xs mt-1 text-gray-600 truncate">{{ name }}</div>
                    </button>
                  </div>
                </div>

                <!-- Color Selection -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Select Color Theme
                  </label>
                  <div class="grid grid-cols-2 gap-3 max-h-48 overflow-y-auto p-1">
                    <button v-for="(gradient, name) in availableColors" :key="name"
                      @click="newCategory.color = gradient"
                      class="relative h-12 rounded-lg overflow-hidden border-2 transition-all hover:scale-105"
                      :class="newCategory.color === gradient ? 'border-blue-500 shadow-lg' : 'border-transparent'">
                      <div :class="`absolute inset-0 bg-gradient-to-r ${gradient}`"></div>
                      <div class="absolute inset-0 flex items-center justify-center text-white text-sm font-medium">
                        {{ name }}
                      </div>
                    </button>
                  </div>
                </div>

                <!-- Preview -->
                <div class="bg-gray-50 rounded-lg p-4">
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Preview
                  </label>
                  <div class="bg-white rounded-xl shadow-sm overflow-hidden">
                    <div :class="`bg-gradient-to-r ${newCategory.color} h-2`"></div>
                    <div class="p-4 flex items-center space-x-3">
                      <div class="p-3 rounded-full bg-gray-100">
                        <i :class="['pi text-xl', newCategory.icon]"></i>
                      </div>
                      <div class="font-medium text-gray-800">
                        {{ newCategory.categoryName || 'Category Name' }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Footer -->
            <div class="p-6 border-t bg-gray-50">
              <div class="flex justify-end space-x-3">
                <button @click="showAddModal = false"
                  class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200">
                  Cancel
                </button>
                <button @click="addCategory" class="px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                  Add Category
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Edit Modal -->
        <div v-if="showEditModal"
          class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div class="bg-white rounded-xl shadow-xl max-w-2xl w-full max-h-[90vh] flex flex-col">
            <!-- Header -->
            <div class="p-6 border-b">
              <div class="flex justify-between items-center">
                <h3 class="text-xl font-bold text-gray-800">Edit Category</h3>
                <button @click="showEditModal = false" class="text-gray-500 hover:text-gray-700">
                  <i class="pi pi-times"></i>
                </button>
              </div>
            </div>

            <!-- Scrollable Content -->
            <div class="p-6 overflow-y-auto flex-1">
              <div class="space-y-6">
                <!-- Category Name -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Category Name
                  </label>
                  <input v-model="selectedCategory.categoryName" type="text"
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    placeholder="Enter category name" />
                </div>

                <!-- Icon Selection -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Select Icon
                  </label>
                  <div class="grid grid-cols-4 gap-3 max-h-48 overflow-y-auto p-1">
                    <button v-for="(icon, name) in availableIcons" :key="name" @click="selectedCategory.icon = icon"
                      :class="['p-3 rounded-lg border-2 transition-all hover:bg-blue-50',
                        selectedCategory.icon === icon ? 'border-blue-500 bg-blue-50' : 'border-gray-200']">
                      <i :class="['pi text-xl', icon]"></i>
                      <div class="text-xs mt-1 text-gray-600 truncate">{{ name }}</div>
                    </button>
                  </div>
                </div>

                <!-- Color Selection -->
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Select Color Theme
                  </label>
                  <div class="grid grid-cols-2 gap-3 max-h-48 overflow-y-auto p-1">
                    <button v-for="(gradient, name) in availableColors" :key="name"
                      @click="selectedCategory.color = gradient"
                      class="relative h-12 rounded-lg overflow-hidden border-2 transition-all hover:scale-105"
                      :class="selectedCategory.color === gradient ? 'border-blue-500 shadow-lg' : 'border-transparent'">
                      <div :class="`absolute inset-0 bg-gradient-to-r ${gradient}`"></div>
                      <div class="absolute inset-0 flex items-center justify-center text-white text-sm font-medium">
                        {{ name }}
                      </div>
                    </button>
                  </div>
                </div>

                <!-- Preview -->
                <div class="bg-gray-50 rounded-lg p-4">
                  <label class="block text-sm font-medium text-gray-700 mb-2">
                    Preview
                  </label>
                  <div class="bg-white rounded-xl shadow-sm overflow-hidden">
                    <div :class="`bg-gradient-to-r ${selectedCategory.color} h-2`"></div>
                    <div class="p-4 flex items-center space-x-3">
                      <div class="p-3 rounded-full bg-gray-100">
                        <i :class="['pi text-xl', selectedCategory.icon]"></i>
                      </div>
                      <div class="font-medium text-gray-800">
                        {{ selectedCategory.categoryName }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Footer -->
            <div class="p-6 border-t bg-gray-50">
              <div class="flex justify-end space-x-3">
                <button @click="showEditModal = false"
                  class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200">
                  Cancel
                </button>
                <button @click="updateCategory(selectedCategory)"
                  class="px-4 py-2 text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                  Save Changes
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Categories Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <CategoryCard v-for="category in categories" :key="category.categoryId" :category="category"
            @edit="openEditModal" @delete="handleDeleteClick" />
        </div>
      </div>
    </div>
  </div>

  <!-- Confirmation Modal -->
  <ConfirmationModal v-if="showConfirmModal" title="Delete Category"
    message="Are you sure you want to delete this category? This action cannot be undone." @confirm="confirmDelete"
    @cancel="showConfirmModal = false" :show="showConfirmModal" />
</template>