<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const username = ref("");
const password = ref("");
const errorMessage = ref("");
const isLoading = ref(false);
const router = useRouter();

const handleLogin = async () => {
  errorMessage.value = ""; // Reset error message
  isLoading.value = true; // Activate loading animation

  try {
    const response = await axios.post("http://localhost:8080/api/users/login", {
      username: username.value,
      password: password.value,
    });

    console.log("Login response:", response.data); // Debugging log

    if (response.data && response.data.userId && response.data.username) {
      const { userId, username } = response.data;

      localStorage.setItem("userId", userId);
      localStorage.setItem("username", username);

      setTimeout(async () => {
        isLoading.value = false;
        await router.push(`/user-dashboard/${userId}`);
      }, 2000);
    } else {
      errorMessage.value = "Unexpected response format. Please try again.";
      isLoading.value = false;
    }
  } catch (error) {
    console.error("Login Error:", error.response?.data || error.message); // Debugging log
    isLoading.value = false; // Deactivate loading animation
    errorMessage.value = error.response?.data || "Invalid username or password. Try again.";
  }
};





</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
      <h2 class="text-2xl font-bold text-gray-800 text-center mb-6">Log in</h2>
      <form @submit.prevent="handleLogin" class="space-y-6">
        <!-- Username -->
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">
            Username
          </label>
          <input
              id="username"
              type="text"
              v-model="username"
              placeholder="Enter your username"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Password -->
        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">
            Password
          </label>
          <input
              id="password"
              type="password"
              v-model="password"
              placeholder="Enter your password"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Error Message -->
        <div v-if="errorMessage" class="text-red-600 text-sm text-center">
          {{ errorMessage }}
        </div>

        <!-- Submit Button -->
        <div>
          <button
              type="submit"
              :class="[isLoading ? 'animate-pulse cursor-not-allowed opacity-75' : 'hover:bg-blue-700', 'w-full bg-blue-600 text-white font-medium py-2 px-4 rounded-md transition']"
              :disabled="isLoading"
          >
            {{ isLoading ? "Logging in..." : "Log in" }}
          </button>
        </div>
      </form>

      <!-- Footer Links -->
      <div class="mt-4 text-center">
        <p class="text-sm text-gray-600">
          Don't have an account?
          <RouterLink to="/get-started" class="text-blue-600 hover:underline">
            Sign Up
          </RouterLink>
        </p>
      </div>
    </div>
  </div>
</template>
