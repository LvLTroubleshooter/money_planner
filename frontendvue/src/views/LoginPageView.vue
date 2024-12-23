<script setup>
import Login from '@/components/Login.vue'
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
        <h1 class="text-3xl font-bold text-gray-900 mb-2">Welcome Back</h1>
        <p class="text-gray-600">
          Login in to continue managing your finances
        </p>
      </div>

      <!-- Login Component -->
      <div class="max-w-md mx-auto w-full">
        <div class="bg-white rounded-2xl shadow-xl p-6 relative">
          <!-- Subtle top border with brand color -->
          <div class="absolute top-0 left-0 right-0 h-1 bg-gradient-to-r from-[rgba(191,148,95,0.5)] to-[rgba(191,148,95,1)] rounded-t-2xl"></div>
          
          <Login />
        </div>

        <!-- Additional Info -->
        <div class="mt-6 text-center space-y-3">
          <div class="text-sm text-gray-600">
            Need help? <a href="#" class="text-custom-color hover:text-custom-hover font-medium">Contact Support</a>
          </div>
          <div class="flex items-center justify-center space-x-4 text-sm text-gray-500">
            <a href="#" class="hover:text-gray-700 transition-colors">Privacy Policy</a>
            <span>•</span>
            <a href="#" class="hover:text-gray-700 transition-colors">Terms of Service</a>
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

/* Custom Colors */
.text-custom-color {
  color: rgba(191, 148, 95, 1);
}

.text-custom-hover:hover {
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

/* Focus Styles */
a:focus {
  outline: 2px solid rgba(191, 148, 95, 0.5);
  outline-offset: 2px;
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

/* Custom Selection */
::selection {
  background: rgba(191, 148, 95, 0.2);
  color: rgba(191, 148, 95, 1);
}
</style>
