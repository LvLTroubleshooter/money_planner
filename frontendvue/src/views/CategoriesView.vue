<script setup>
import { ref, onMounted, computed } from "vue";
import { RouterLink } from 'vue-router';
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import CategoriesNavbar from "@/components/CategoriesNavbar.vue";
import CategoryCard from "@/components/CategoryCard.vue";
import ConfirmationModal from "@/components/ConfirmationModal.vue";
import { useRouter } from 'vue-router';

const router = useRouter();

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
    const categoryData = {
      categoryId: updatedCategory.categoryId,
      categoryName: updatedCategory.categoryName,
      icon: updatedCategory.icon,
      color: updatedCategory.color,
      user: { userId: parseInt(userId) }
    };

    const response = await axios.put(
      `/api/categories/user/${userId}/category/${updatedCategory.categoryId}`,
      categoryData
    );

    // Update local state
    const index = categories.value.findIndex(
      category => category.categoryId === updatedCategory.categoryId
    );
    if (index !== -1) {
      categories.value[index] = {
        ...response.data,
        icon: categoryData.icon,
        color: categoryData.color
      };
    }

    // Update all expenses with this category
    try {
      await axios.put(`/api/expenses/updateCategory/${updatedCategory.categoryId}`, {
        categoryName: categoryData.categoryName,
        icon: categoryData.icon,
        color: categoryData.color
      });
      showEditModal.value = false;
    } catch (error) {
      console.error('Failed to update expenses with new category details:', error);
    }

    // Close the modal after all updates are complete
    showEditModal.value = false;
  } catch (error) {
    console.error('Failed to update category:', error);
    alert('Failed to update category. Please try again.');
  }
};

// Compute most used category
const mostUsedCategory = computed(() => {
  if (!categories.value.length) return null;
  // This is a placeholder - you'll need to implement the actual logic
  // based on how you track category usage in your backend
  return categories.value[0];
});

onMounted(() => {
  fetchCategories();
});
</script>

<template>
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <CategoriesNavbar @add-category="showAddModal = true" />

      <div class="p-6 pt-20">
        <!-- Categories Summary Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
          <!-- Total Categories Card -->
          <div class="bg-gradient-to-br from-purple-500 to-indigo-600 rounded-xl shadow-lg p-4 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-base font-medium opacity-90">Total Categories</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-tags text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-1">{{ categories.length }}</div>
            <div class="text-xs opacity-75">Active expense categories</div>
          </div>

          <!-- Most Used Category Card -->
          <div class="bg-gradient-to-br from-blue-500 to-cyan-500 rounded-xl shadow-lg p-4 text-white">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-base font-medium opacity-90">Most Used Category</h3>
              <div class="p-2 bg-white bg-opacity-20 rounded-lg">
                <i class="pi pi-star text-xl"></i>
              </div>
            </div>
            <div class="text-3xl font-bold mb-1">{{ mostUsedCategory?.categoryName || 'None' }}</div>
            <div class="text-xs opacity-75">Based on expense frequency</div>
          </div>
        </div>

        <!-- Empty State -->
        <div v-if="categories.length === 0" class="text-center py-12">
          <div class="bg-white rounded-2xl p-8 shadow-md max-w-md mx-auto">
            <div class="text-gray-400 mb-4">
              <i class="pi pi-tags text-5xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-gray-700 mb-2">No Categories Yet</h3>
            <p class="text-gray-500 mb-6">Start by adding your first expense category!</p>
            <button @click="showAddModal = true"
              class="bg-custom-color text-white px-6 py-2 rounded-lg shadow-md hover:bg-custom-hover-color transition-all">
              Add Category
            </button>
          </div>
        </div>

        <!-- Categories Grid -->
        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="category in categories" :key="category.categoryId"
            class="bg-white rounded-2xl shadow-md overflow-hidden hover:shadow-xl transform hover:-translate-y-1 transition-all duration-300 group">
            <!-- Card Header with Gradient -->
            <div :class="`h-2 bg-gradient-to-r ${category.color}`"></div>

            <div class="p-6">
              <!-- Category Header -->
              <div class="flex items-center justify-between mb-4">
                <div class="flex items-center space-x-3">
                  <div :class="`p-3 rounded-full bg-gradient-to-r ${category.color} bg-opacity-10`">
                    <i :class="['pi text-xl text-white', category.icon]"></i>
                  </div>
                  <h3 class="text-xl font-bold text-gray-800 group-hover:text-custom-color transition-colors">
                    {{ category.categoryName }}
                  </h3>
                </div>
                <div class="flex space-x-2 opacity-0 group-hover:opacity-100 transition-opacity">
                  <button @click="openEditModal(category)"
                    class="text-yellow-500 hover:text-yellow-600 transition-colors p-2 hover:bg-yellow-50 rounded-full">
                    <i class="pi pi-pencil"></i>
                  </button>
                  <button @click="handleDeleteClick(category.categoryId)"
                    class="text-red-500 hover:text-red-600 transition-colors p-2 hover:bg-red-50 rounded-full">
                    <i class="pi pi-trash"></i>
                  </button>
                </div>
              </div>

              <!-- Category Details -->
              <div class="space-y-3">
                <div class="text-sm text-gray-500 flex items-center space-x-2">
                  <i class="pi pi-calendar text-custom-color"></i>
                  <span>Added {{ new Date(category.createdAt).toLocaleDateString() }}</span>
                </div>
                <div class="text-sm text-gray-500 flex items-center space-x-2">
                  <i class="pi pi-chart-bar text-custom-color"></i>
                  <span>Used in {{ category.expenseCount || 0 }} expenses</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Add Category Modal -->
  <div v-if="showAddModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-xl shadow-xl max-w-4xl w-full max-h-[90vh] flex flex-col">
      <!-- Header -->
      <div class="p-6 border-b">
        <div class="flex justify-between items-center">
          <h3 class="text-xl font-bold text-gray-800">Add New Category</h3>
          <button @click="showAddModal = false" class="text-gray-500 hover:text-gray-700">
            <i class="pi pi-times"></i>
          </button>
        </div>
      </div>

      <!-- Content -->
      <div class="p-6 overflow-y-auto">
        <div class="grid grid-cols-2 gap-8">
          <!-- Left Column: Basic Info and Preview -->
          <div class="space-y-6">
            <!-- Category Name -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Category Name</label>
              <input v-model="newCategory.categoryName" type="text"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-custom-color focus:border-custom-color"
                placeholder="Enter category name" />
            </div>

            <!-- Preview Card -->
            <div class="bg-gray-50 rounded-lg p-4">
              <label class="block text-sm font-medium text-gray-700 mb-3">Preview</label>
              <div class="bg-white rounded-xl shadow-sm overflow-hidden transform hover:scale-105 transition-all duration-300">
                <div :class="`h-2 bg-gradient-to-r ${newCategory.color}`"></div>
                <div class="p-6">
                  <div class="flex items-center space-x-3 mb-4">
                    <div :class="`p-3 rounded-full bg-gradient-to-r ${newCategory.color}`">
                      <i :class="['pi text-xl text-white', newCategory.icon]"></i>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800">
                      {{ newCategory.categoryName || 'Category Name' }}
                    </h3>
                  </div>
                  <div class="space-y-2">
                    <div class="text-sm text-gray-500 flex items-center space-x-2">
                      <i class="pi pi-calendar"></i>
                      <span>Added {{ new Date().toLocaleDateString() }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column: Icon and Color Selection -->
          <div class="space-y-6">
            <!-- Icon Selection -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-3">Select Icon</label>
              <div class="grid grid-cols-4 gap-3 bg-gray-50 p-4 rounded-lg max-h-[200px] overflow-y-auto scrollbar-thin">
                <button v-for="(icon, name) in availableIcons" :key="name" 
                  @click="newCategory.icon = icon" 
                  :class="[
                    'p-3 rounded-lg transition-all hover:bg-white hover:shadow-md',
                    newCategory.icon === icon 
                      ? 'bg-white shadow-md border-2 border-custom-color' 
                      : 'bg-gray-100 border-2 border-transparent'
                  ]">
                  <i :class="['pi text-xl', icon]"></i>
                  <div class="text-xs mt-1 text-gray-600 truncate">{{ name }}</div>
                </button>
              </div>
            </div>

            <!-- Color Selection -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-3">Select Color Theme</label>
              <div class="grid grid-cols-2 gap-3 bg-gray-50 p-4 rounded-lg max-h-[200px] overflow-y-auto scrollbar-thin">
                <button v-for="(gradient, name) in availableColors" :key="name" 
                  @click="newCategory.color = gradient"
                  class="relative h-14 rounded-lg overflow-hidden transition-all hover:shadow-lg"
                  :class="newCategory.color === gradient ? 'ring-2 ring-custom-color shadow-lg scale-105' : ''">
                  <div :class="`absolute inset-0 bg-gradient-to-r ${gradient}`"></div>
                  <div class="absolute inset-0 flex items-center justify-center text-white text-sm font-medium">
                    {{ name }}
                  </div>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="p-6 border-t bg-gray-50">
        <div class="flex justify-end space-x-3">
          <button @click="showAddModal = false"
            class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-all">
            Cancel
          </button>
          <button @click="addCategory" 
            class="px-4 py-2 text-white bg-custom-color rounded-lg hover:bg-custom-hover-color transition-all">
            Add Category
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- Edit Category Modal -->
  <div v-if="showEditModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-xl shadow-xl max-w-4xl w-full max-h-[90vh] flex flex-col">
      <!-- Header -->
      <div class="p-6 border-b">
        <div class="flex justify-between items-center">
          <h3 class="text-xl font-bold text-gray-800">Edit Category</h3>
          <button @click="showEditModal = false" class="text-gray-500 hover:text-gray-700">
            <i class="pi pi-times"></i>
          </button>
        </div>
      </div>

      <!-- Content -->
      <div class="p-6 overflow-y-auto">
        <div class="grid grid-cols-2 gap-8">
          <!-- Left Column: Basic Info and Preview -->
          <div class="space-y-6">
            <!-- Category Name -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Category Name</label>
              <input v-model="selectedCategory.categoryName" type="text"
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-custom-color focus:border-custom-color"
                placeholder="Enter category name" />
            </div>

            <!-- Preview Card -->
            <div class="bg-gray-50 rounded-lg p-4">
              <label class="block text-sm font-medium text-gray-700 mb-3">Preview</label>
              <div class="bg-white rounded-xl shadow-sm overflow-hidden transform hover:scale-105 transition-all duration-300">
                <div :class="`h-2 bg-gradient-to-r ${selectedCategory.color}`"></div>
                <div class="p-6">
                  <div class="flex items-center space-x-3 mb-4">
                    <div :class="`p-3 rounded-full bg-gradient-to-r ${selectedCategory.color}`">
                      <i :class="['pi text-xl text-white', selectedCategory.icon]"></i>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800">
                      {{ selectedCategory.categoryName || 'Category Name' }}
                    </h3>
                  </div>
                  <div class="space-y-2">
                    <div class="text-sm text-gray-500 flex items-center space-x-2">
                      <i class="pi pi-calendar"></i>
                      <span>Last Updated {{ new Date().toLocaleDateString() }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column: Icon and Color Selection -->
          <div class="space-y-6">
            <!-- Icon Selection -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-3">Select Icon</label>
              <div class="grid grid-cols-4 gap-3 bg-gray-50 p-4 rounded-lg max-h-[200px] overflow-y-auto scrollbar-thin">
                <button v-for="(icon, name) in availableIcons" :key="name" 
                  @click="selectedCategory.icon = icon" 
                  :class="[
                    'p-3 rounded-lg transition-all hover:bg-white hover:shadow-md',
                    selectedCategory.icon === icon 
                      ? 'bg-white shadow-md border-2 border-custom-color' 
                      : 'bg-gray-100 border-2 border-transparent'
                  ]">
                  <i :class="['pi text-xl', icon]"></i>
                  <div class="text-xs mt-1 text-gray-600 truncate">{{ name }}</div>
                </button>
              </div>
            </div>

            <!-- Color Selection -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-3">Select Color Theme</label>
              <div class="grid grid-cols-2 gap-3 bg-gray-50 p-4 rounded-lg max-h-[200px] overflow-y-auto scrollbar-thin">
                <button v-for="(gradient, name) in availableColors" :key="name" 
                  @click="selectedCategory.color = gradient"
                  class="relative h-14 rounded-lg overflow-hidden transition-all hover:shadow-lg"
                  :class="selectedCategory.color === gradient ? 'ring-2 ring-custom-color shadow-lg scale-105' : ''">
                  <div :class="`absolute inset-0 bg-gradient-to-r ${gradient}`"></div>
                  <div class="absolute inset-0 flex items-center justify-center text-white text-sm font-medium">
                    {{ name }}
                  </div>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="p-6 border-t bg-gray-50">
        <div class="flex justify-end space-x-3">
          <button @click="showEditModal = false"
            class="px-4 py-2 text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 transition-all">
            Cancel
          </button>
          <button @click="updateCategory(selectedCategory)"
            class="px-4 py-2 text-white bg-custom-color rounded-lg hover:bg-custom-hover-color transition-all">
            Save Changes
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- Confirmation Modal -->
  <ConfirmationModal v-if="showConfirmModal" title="Delete Category"
    message="Are you sure you want to delete this category? This action cannot be undone." @confirm="confirmDelete"
    @cancel="showConfirmModal = false" :show="showConfirmModal" />
</template>

<style scoped>
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1) !important;
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1) !important;
}

/* Add these specific button styles */
button.bg-custom-color {
  background-color: rgba(191, 148, 95, 1) !important;
}

button.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1) !important;
}

.bg-custom-bg {
  background-color: rgba(191, 148, 95, 0.1);
}
</style>