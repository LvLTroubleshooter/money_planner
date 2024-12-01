<script setup>
import { ref } from 'vue';
import { useRouter, useRoute, RouterLink } from 'vue-router';

const userName = ref('Ismail'); // Replace with dynamic data
const userInitials = ref('I'); // Replace with dynamic initials or profile picture

const router = useRouter();

const handleLogout = () => {
  // Handle logout logic here (clear session, etc.)
  router.push('/');
};

const isActiveLink = (routePath) => {
  const route = useRoute();
  return route.path.startsWith(routePath); // Match base route and sub-routes
};
</script>

<template>
  <div class="w-64 h-full bg-blue-950 text-gray-50 fixed top-0 left-0 shadow-xl">
    <div class="p-6 flex flex-col space-y-8">
      <!-- Profile Section -->
      <div class="flex items-center space-x-4">
        <div class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center text-xl font-semibold">
          <!-- Example Initials, you can replace it with user's profile image -->
          <span>{{ userInitials }}</span>
        </div>
        <div class="text-white font-semibold">
          <p>{{ userName }}</p>
        </div>
      </div>

      <!-- Navigation Links -->
      <nav>
        <ul class="space-y-4">
          <li>
            <RouterLink
                to="/user-dashboard"
                :class="[
                isActiveLink('/user-dashboard') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex', 'items-center', 'space-x-3', 'p-2', 'rounded-md', 'transition'
              ]"
            >
              <i class="pi pi-home text-indigo-400"></i>
              <span>Dashboard</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink
                to="/goals"
                :class="[
                isActiveLink('/goals') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex', 'items-center', 'space-x-3', 'p-2', 'rounded-md', 'transition'
              ]"
            >
              <i class="pi pi-bullseye text-indigo-400"></i>
              <span>Goals</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink
                to="/categories"
                :class="[
                isActiveLink('/categories') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex', 'items-center', 'space-x-3', 'p-2', 'rounded-md', 'transition'
              ]"
            >
              <i class="pi pi-th-large text-indigo-400"></i>
              <span>Categories</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink
                to="/income-sources"
                :class="[
                isActiveLink('/income-sources') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex', 'items-center', 'space-x-3', 'p-2', 'rounded-md', 'transition'
              ]"
            >
              <i class="pi pi-wallet text-indigo-400"></i>
              <span>Income Sources</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink
                to="/expenses"
                :class="[
                isActiveLink('/expenses') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex', 'items-center', 'space-x-3', 'p-2', 'rounded-md', 'transition'
              ]"
            >
              <i class="pi pi-credit-card text-indigo-400"></i>
              <span>Expenses</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink
                to="/Settings"
                :class="[
                isActiveLink('/Settings') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex', 'items-center', 'space-x-3', 'p-2', 'rounded-md', 'transition'
              ]"
            >
              <i class="pi pi-cog text-indigo-400"></i>
              <span>Settings</span>
            </RouterLink>
          </li>
        </ul>
      </nav>

      <!-- Logout Section -->
      <div class="mt-auto flex justify-center">
        <button @click="handleLogout" class="flex items-center space-x-2 bg-custom-color text-white py-2 px-4 rounded-md hover:bg-custom-hover-color">
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

/* Custom shadow for the sidebar */
.shadow-xl {
  box-shadow: 4px 0px 12px rgba(0, 0, 0, 0.1), 0px 4px 20px rgba(0, 0, 0, 0.2);
}
</style>
