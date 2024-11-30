<script setup>
import BackButton from "@/components/BackButton.vue";
import {RouterLink} from "vue-router";
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
      <h2 class="text-2xl font-bold text-gray-800 text-center mb-6">Log in</h2>
      <form @submit.prevent class="space-y-6">
        <!-- Username -->
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <input
              id="username"
              type="text"
              placeholder="Enter your username"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>
        <!-- Password -->
        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <input
              id="password"
              type="password"
              placeholder="Enter your password"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Submit Button -->
        <div>
          <button
              type="submit"
              class="w-full bg-blue-600 text-white font-medium py-2 px-4 rounded-md hover:bg-blue-700 transition"
          >
            Sign In
          </button>
        </div>
      </form>

      <!-- Footer Links -->
      <div class="mt-4 text-center">
        <p class="text-sm text-gray-600">
          Don't have an account?
          <RouterLink to="/get-started" class="text-blue-600 hover:underline">Sign Up</RouterLink>
        </p>
      </div>
    </div>
  </div>
</template>


<style scoped>

</style>