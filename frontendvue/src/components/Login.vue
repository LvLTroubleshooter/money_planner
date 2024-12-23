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
  errorMessage.value = "";
  isLoading.value = true;

  try {
    const response = await axios.post("http://localhost:8080/api/users/login", {
      username: username.value,
      password: password.value,
    });

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
    console.error("Login Error:", error.response?.data || error.message);
    isLoading.value = false;
    errorMessage.value = error.response?.data || "Invalid username or password. Try again.";
  }
};
</script>

<template>
  <form @submit.prevent="handleLogin" class="space-y-4">
    <!-- Username Field -->
    <div class="space-y-1.5">
      <label for="username" class="block text-sm font-medium text-gray-700">
        Username
      </label>
      <div class="relative">
        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
          <i class="pi pi-user text-gray-400 text-sm"></i>
        </div>
        <input
          id="username"
          type="text"
          v-model="username"
          placeholder="Enter your username"
          class="input-field pl-10"
          required
        />
      </div>
    </div>

    <!-- Password Field -->
    <div class="space-y-1.5">
      <label for="password" class="block text-sm font-medium text-gray-700">
        Password
      </label>
      <div class="relative">
        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
          <i class="pi pi-lock text-gray-400 text-sm"></i>
        </div>
        <input
          id="password"
          type="password"
          v-model="password"
          placeholder="Enter your password"
          class="input-field pl-10"
          required
        />
      </div>
    </div>

    <!-- Remember Me & Forgot Password -->
    <div class="flex items-center justify-between mt-2">
      <div class="flex items-center">
        <input
          id="remember-me"
          type="checkbox"
          class="h-4 w-4 text-custom-color focus:ring-custom-color border-gray-300 rounded cursor-pointer"
        />
        <label for="remember-me" class="ml-2 block text-sm text-gray-700 cursor-pointer">
          Remember me
        </label>
      </div>
      <div class="text-sm">
        <a href="#" class="font-medium text-custom-color hover:text-custom-hover transition-colors">
          Forgot password?
        </a>
      </div>
    </div>

    <!-- Error Message -->
    <div v-if="errorMessage" class="error-message mt-2">
      <i class="pi pi-exclamation-circle mr-2"></i>
      {{ errorMessage }}
    </div>

    <!-- Submit Button -->
    <button
      type="submit"
      :disabled="isLoading"
      class="submit-button mt-4"
      :class="{ 'opacity-75 cursor-not-allowed': isLoading }"
    >
      <span v-if="!isLoading">Login In</span>
      <div v-else class="flex items-center justify-center">
        <div class="loader mr-2"></div>
        Login in...
      </div>
    </button>

    <!-- Sign Up Link -->
    <div class="text-center mt-4">
      <p class="text-sm text-gray-600">
        Don't have an account?
        <RouterLink to="/get-started" class="font-medium text-custom-color hover:text-custom-hover transition-colors">
          Create an account
        </RouterLink>
      </p>
    </div>
  </form>
</template>

<style scoped>
/* Custom Colors */
:root {
  --custom-color: rgba(191, 148, 95, 1);
  --custom-hover: rgba(164, 120, 65, 1);
}

/* Input Fields */
.input-field {
  @apply block w-full px-3 py-2.5 bg-gray-50 border border-gray-300 rounded-lg shadow-sm
  focus:ring-2 focus:ring-[rgba(191,148,95,0.2)] focus:border-[rgba(191,148,95,1)] 
  transition-all duration-200 placeholder-gray-400;
  padding-left: 2.5rem;
}

.input-field:hover {
  @apply border-gray-400;
}

/* Submit Button */
.submit-button {
  @apply w-full py-2 px-4 text-white font-medium rounded-lg transition-all duration-300 
  hover:shadow-lg focus:outline-none focus:ring-2 focus:ring-[rgba(191,148,95,0.5)] 
  active:scale-[0.98];
  background: linear-gradient(to right, rgba(191, 148, 95, 1), rgba(164, 120, 65, 1));
}

.submit-button:hover {
  box-shadow: 0 8px 16px rgba(191, 148, 95, 0.2);
}

/* Error Message */
.error-message {
  @apply flex items-center px-4 py-3 text-sm text-red-600 bg-red-50 
  rounded-lg border border-red-200;
}

/* Loading Animation */
.loader {
  @apply w-4 h-4 border-2 border-white rounded-full border-t-transparent animate-spin;
}

/* Custom Checkbox */
input[type="checkbox"] {
  @apply h-4 w-4 rounded border-gray-300 cursor-pointer;
  accent-color: rgba(191, 148, 95, 1);
}

input[type="checkbox"]:focus {
  @apply ring-2 ring-[rgba(191,148,95,0.5)] ring-offset-2;
}

/* Text Colors */
.text-custom-color {
  color: rgba(191, 148, 95, 1);
}

.hover\:text-custom-hover:hover {
  color: rgba(164, 120, 65, 1);
}

/* Focus Styles */
*:focus {
  outline: none;
}

/* Transitions */
.transition-all {
  transition: all 0.3s ease;
}

/* Custom Selection */
::selection {
  background: rgba(191, 148, 95, 0.2);
  color: rgba(191, 148, 95, 1);
}

/* Hover Effects */
.input-field:focus {
  border-color: rgba(191, 148, 95, 1);
  box-shadow: 0 0 0 4px rgba(191, 148, 95, 0.1);
}

/* Responsive Adjustments */
@media (max-width: 640px) {
  .input-field, .submit-button {
    @apply py-2 text-sm;
  }
}

/* Add specific placeholder styling */
.input-field::placeholder {
  font-size: 0.875rem;
  color: #9CA3AF;
}

/* Adjust icon container */
.absolute {
  display: flex;
  align-items: center;
  height: 100%;
}

.pi {
  font-size: 1rem;
  line-height: 1;
}
</style>
