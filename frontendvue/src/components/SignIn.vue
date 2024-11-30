<script setup>
import { RouterLink } from "vue-router";

</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
      <h2 class="text-2xl font-bold text-gray-800 text-center mb-6">Sign Up</h2>
      <form @submit.prevent class="space-y-6">
        <!-- Username -->
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <input
              id="username"
              type="text"
              placeholder="Choose a username"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Email -->
        <div>
          <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
          <input
              id="email"
              type="email"
              placeholder="Enter your email"
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
              placeholder="Create a password"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Re-enter Password -->
        <div>
          <label for="confirm-password" class="block text-sm font-medium text-gray-700">Confirm Password</label>
          <input
              id="confirm-password"
              type="password"
              placeholder="Re-enter your password"
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
            Sign Up
          </button>
        </div>
      </form>

      <!-- Footer Links -->
      <div class="mt-4 text-center">
        <p class="text-sm text-gray-600">
          Already have an account?
          <RouterLink to="/login-page" class="text-blue-600 hover:underline">Login</RouterLink>
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
