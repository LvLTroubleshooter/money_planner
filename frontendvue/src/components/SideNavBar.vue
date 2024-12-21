<script setup>
import { onMounted, ref, onBeforeUnmount } from "vue";
import { useRouter, useRoute, RouterLink } from "vue-router";
import axios from '@/utils/axios';

const userName = ref("");
const profilePhoto = ref(null);
const userInitials = ref("");

const router = useRouter();
const route = useRoute();

const fetchUserData = async () => {
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) return;

    const response = await axios.get(`/api/users/${userId}`);
    userName.value = response.data.username || 'Guest';
    userInitials.value = userName.value.charAt(0).toUpperCase();

    if (response.data.profilePhotoUrl) {
      profilePhoto.value = response.data.profilePhotoUrl;
    } else {
      profilePhoto.value = null;
    }
  } catch (error) {
    console.error('Failed to fetch user data:', error);
  }
};

const handleUserDataUpdate = (event) => {
  userName.value = event.detail.username;
  userInitials.value = event.detail.username.charAt(0).toUpperCase();
};

const handleProfileUpdate = () => {
  fetchUserData();
};

onMounted(() => {
  fetchUserData();
  window.addEventListener('profilePhotoUpdated', handleProfileUpdate);
  window.addEventListener('userDataUpdated', handleUserDataUpdate);
});

onBeforeUnmount(() => {
  window.removeEventListener('profilePhotoUpdated', handleProfileUpdate);
  window.removeEventListener('userDataUpdated', handleUserDataUpdate);
});

// Handle user logout
const handleLogout = () => {
  localStorage.removeItem("userId");
  localStorage.removeItem("username");
  localStorage.removeItem("userProfilePicture");
  router.push("/");
};

// Check if the link is active
const isActiveLink = (routePath) => {
  const route = useRoute();
  return route.path.startsWith(routePath);
};
</script>

<template>
  <div class="w-64 h-full bg-blue-950 text-gray-50 fixed top-0 left-0 shadow-xl">
    <div class="p-6 flex flex-col space-y-8">
      <!-- Profile Section -->
      <div class="flex items-center space-x-4">
        <div class="w-12 h-12 rounded-full overflow-hidden">
          <img v-if="profilePhoto" :src="profilePhoto" alt="Profile" class="w-full h-full object-cover" />
          <div v-else class="w-full h-full bg-gray-300 flex items-center justify-center text-xl font-semibold">
            <span>{{ userInitials }}</span>
          </div>
        </div>
        <div class="text-white font-semibold">
          <p>{{ userName }}</p>
        </div>
      </div>

      <!-- Navigation Links -->
      <nav>
        <ul class="space-y-4">
          <li>
            <RouterLink to="/user-dashboard/${userId}" :class="[
              isActiveLink('/user-dashboard/${userId}') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
              'flex',
              'items-center',
              'space-x-3',
              'p-2',
              'rounded-md',
              'transition'
            ]">
              <i class="pi pi-home text-indigo-400"></i>
              <span>Dashboard</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/goals_page" :class="[
              isActiveLink('/goals_page') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
              'flex',
              'items-center',
              'space-x-3',
              'p-2',
              'rounded-md',
              'transition'
            ]">
              <i class="pi pi-bullseye text-indigo-400"></i>
              <span>Goals</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/categories_page" :class="[
              isActiveLink('/categories_page') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
              'flex',
              'items-center',
              'space-x-3',
              'p-2',
              'rounded-md',
              'transition'
            ]">
              <i class="pi pi-th-large text-indigo-400"></i>
              <span>Categories</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/income_page" :class="[
              isActiveLink('/income_page') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
              'flex',
              'items-center',
              'space-x-3',
              'p-2',
              'rounded-md',
              'transition'
            ]">
              <i class="pi pi-wallet text-indigo-400"></i>
              <span>Income Sources</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/expenses_page" :class="[
              isActiveLink('/expenses_page') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
              'flex',
              'items-center',
              'space-x-3',
              'p-2',
              'rounded-md',
              'transition'
            ]">
              <i class="pi pi-credit-card text-indigo-400"></i>
              <span>Expenses</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/settings_page" :class="[
              isActiveLink('/settings_page') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
              'flex',
              'items-center',
              'space-x-3',
              'p-2',
              'rounded-md',
              'transition'
            ]">
              <i class="pi pi-cog text-indigo-400"></i>
              <span>Settings</span>
            </RouterLink>
          </li>
        </ul>
      </nav>

      <!-- Logout Section -->
      <div class="mt-auto flex justify-center">
        <button @click="handleLogout"
          class="flex items-center space-x-2 bg-custom-color text-white py-2 px-4 rounded-md hover:bg-custom-hover-color">
          <i class="pi pi-sign-out text-white"></i>
          <span>Logout</span>
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

.shadow-xl {
  box-shadow: 4px 0 12px rgba(0, 0, 0, 0.1), 0 4px 20px rgba(0, 0, 0, 0.2);
}
</style>
