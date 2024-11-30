<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

// Define reactive variables
const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');

// Access Vue Router to redirect after successful signup
const router = useRouter();

// Handle sign-up logic
const handleSignUp = async () => {
  if (password.value !== confirmPassword.value) {
    alert('Passwords do not match!');
    return;
  }

  try {
    // Make sure to await the axios call
    await axios.post('http://localhost:8080/api/users/signup', {
      username: username.value,
      email: email.value,
      userPassword: password.value,
    });

    alert('Sign Up Successful! Redirecting to Login...');
    router.push('/login-page'); // Redirect to login page
  } catch (error) {
    alert(error.response?.data || 'Sign Up Failed');
  }
};
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
      <h2 class="text-2xl font-bold text-gray-800 text-center mb-6">Sign Up</h2>
      <form @submit.prevent="handleSignUp" class="space-y-6">
        <!-- Username -->
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <input
              id="username"
              v-model="username"
              type="text"
              placeholder="Enter your username"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Email -->
        <div>
          <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
          <input
              id="email"
              v-model="email"
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
              v-model="password"
              type="password"
              placeholder="Enter your password"
              class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
          />
        </div>

        <!-- Confirm Password -->
        <div>
          <label for="confirmPassword" class="block text-sm font-medium text-gray-700">Confirm Password</label>
          <input
              id="confirmPassword"
              v-model="confirmPassword"
              type="password"
              placeholder="Confirm your password"
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
          <router-link to="/login-page" class="text-blue-600 hover:underline">Log In</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Your custom styles here */
</style>
