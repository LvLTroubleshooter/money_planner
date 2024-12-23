<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const username = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const errorMessage = ref('');
const isLoading = ref(false);
const router = useRouter();

const handleSignUp = async () => {
  if (password.value !== confirmPassword.value) {
    errorMessage.value = 'Passwords do not match!';
    return;
  }

  isLoading.value = true;
  errorMessage.value = '';
  
  try {
    await axios.post('http://localhost:8080/api/users/signup', {
      username: username.value,
      email: email.value,
      userPassword: password.value,
    });

    setTimeout(() => {
      isLoading.value = false;
      router.push('/login-page');
    }, 2000);
  } catch (error) {
    isLoading.value = false;
    errorMessage.value = error.response?.data || 'Sign Up Failed';
  }
};
</script>

<template>
  <div class="min-h-screen relative bg-gradient-to-b from-gray-50 to-white overflow-hidden">
    <!-- Background Pattern -->
    <div class="absolute inset-0 bg-grid-pattern opacity-5"></div>
    
    <!-- Decorative Elements -->
    <div class="absolute -top-20 -right-20 w-72 h-72 bg-gradient-to-br from-[rgba(191,148,95,0.2)] to-[rgba(191,148,95,0.1)] rounded-full blur-3xl"></div>
    <div class="absolute -bottom-20 -left-20 w-72 h-72 bg-gradient-to-br from-[rgba(191,148,95,0.2)] to-[rgba(191,148,95,0.1)] rounded-full blur-3xl"></div>

    <!-- Main Content -->
    <div class="container mx-auto px-4 py-12 relative flex flex-col justify-center min-h-screen">
      <!-- Header -->
      <div class="text-center mb-6">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">Create Account</h1>
        <p class="text-gray-600">
          Join us to start managing your finances
        </p>
      </div>

      <!-- Sign Up Form -->
      <div class="max-w-md mx-auto w-full">
        <div class="bg-white rounded-2xl shadow-xl p-6 relative">
          <!-- Subtle top border with brand color -->
          <div class="absolute top-0 left-0 right-0 h-1 bg-gradient-to-r from-[rgba(191,148,95,0.5)] to-[rgba(191,148,95,1)] rounded-t-2xl"></div>
          
          <form @submit.prevent="handleSignUp" class="space-y-4">
            <!-- Username Field -->
            <div class="space-y-1.5">
              <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="pi pi-user text-gray-400 text-sm"></i>
                </div>
                <input
                  id="username"
                  v-model="username"
                  type="text"
                  placeholder="Choose a username"
                  class="input-field"
                  required
                />
              </div>
            </div>

            <!-- Email Field -->
            <div class="space-y-1.5">
              <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="pi pi-envelope text-gray-400 text-sm"></i>
                </div>
                <input
                  id="email"
                  v-model="email"
                  type="email"
                  placeholder="Enter your email"
                  class="input-field"
                  required
                />
              </div>
            </div>

            <!-- Password Field -->
            <div class="space-y-1.5">
              <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="pi pi-lock text-gray-400 text-sm"></i>
                </div>
                <input
                  id="password"
                  v-model="password"
                  type="password"
                  placeholder="Create a password"
                  class="input-field"
                  required
                />
              </div>
            </div>

            <!-- Confirm Password Field -->
            <div class="space-y-1.5">
              <label for="confirmPassword" class="block text-sm font-medium text-gray-700">Confirm Password</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="pi pi-lock text-gray-400 text-sm"></i>
                </div>
                <input
                  id="confirmPassword"
                  v-model="confirmPassword"
                  type="password"
                  placeholder="Confirm your password"
                  class="input-field"
                  required
                />
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
              <span v-if="!isLoading">Create Account</span>
              <div v-else class="flex items-center justify-center">
                <div class="loader mr-2"></div>
                Creating account...
              </div>
            </button>

            <!-- Login Link -->
            <div class="text-center mt-4">
              <p class="text-sm text-gray-600">
                Already have an account?
                <router-link to="/login-page" class="font-medium text-custom-color hover:text-custom-hover transition-colors">
                  Login here
                </router-link>
              </p>
            </div>
          </form>
        </div>

        <!-- Additional Info -->
        <div class="mt-6 text-center space-y-3">
          <div class="text-sm text-gray-600">
            By signing up, you agree to our
            <a href="#" class="text-custom-color hover:text-custom-hover font-medium">Terms of Service</a>
            and
            <a href="#" class="text-custom-color hover:text-custom-hover font-medium">Privacy Policy</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Background Pattern */
.bg-grid-pattern {
  background-image: 
    linear-gradient(to right, rgba(191, 148, 95, 0.05) 1px, transparent 1px),
    linear-gradient(to bottom, rgba(191, 148, 95, 0.05) 1px, transparent 1px);
  background-size: 32px 32px;
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
  @apply w-full py-2.5 px-4 text-white font-medium rounded-lg transition-all duration-300 
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

/* Text Colors */
.text-custom-color {
  color: rgba(191, 148, 95, 1);
}

.hover\:text-custom-hover:hover {
  color: rgba(164, 120, 65, 1);
}

/* Animations */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.container {
  animation: fadeIn 0.5s ease-out;
}

/* Card Hover Effect */
.bg-white {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.bg-white:hover {
  transform: translateY(-2px);
  box-shadow: 0 20px 40px rgba(191, 148, 95, 0.1);
}

/* Custom Selection */
::selection {
  background: rgba(191, 148, 95, 0.2);
  color: rgba(191, 148, 95, 1);
}

/* Responsive Adjustments */
@media (max-width: 640px) {
  .container {
    padding-left: 1rem;
    padding-right: 1rem;
  }

  .bg-white {
    padding: 1.5rem;
  }
}
</style>

