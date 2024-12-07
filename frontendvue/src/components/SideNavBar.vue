<script setup>
import { onMounted, ref } from "vue";
import { useRouter, useRoute, RouterLink } from "vue-router";

const userName = ref(""); // Dynamic username
const userInitials = ref(""); // Dynamic initials

const router = useRouter();

// Fetch user details from localStorage on mount
onMounted(() => {
  const name = localStorage.getItem("username") || "Guest";
  userName.value = name;
  userInitials.value = name.charAt(0).toUpperCase();
});

// Handle user logout
const handleLogout = () => {
  localStorage.removeItem("userId");
  localStorage.removeItem("username");
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
        <div
            class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center text-xl font-semibold"
        >
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
                to="/user-dashboard/${userId}"
                :class="[
                isActiveLink('/user-dashboard/${userId}') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex',
                'items-center',
                'space-x-3',
                'p-2',
                'rounded-md',
                'transition'
              ]"
            >
              <i class="pi pi-home text-indigo-400"></i>
              <span>Dashboard</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink
                to="/goals_page"
                :class="[
                isActiveLink('/goals_page') ? 'bg-indigo-700' : 'hover:bg-indigo-700',
                'flex',
                'items-center',
                'space-x-3',
                'p-2',
                'rounded-md',
                'transition'
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
                'flex',
                'items-center',
                'space-x-3',
                'p-2',
                'rounded-md',
                'transition'
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
                'flex',
                'items-center',
                'space-x-3',
                'p-2',
                'rounded-md',
                'transition'
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
                'flex',
                'items-center',
                'space-x-3',
                'p-2',
                'rounded-md',
                'transition'
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
                'flex',
                'items-center',
                'space-x-3',
                'p-2',
                'rounded-md',
                'transition'
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
        <button
            @click="handleLogout"
            class="flex items-center space-x-2 bg-custom-color text-white py-2 px-4 rounded-md hover:bg-custom-hover-color"
        >
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
