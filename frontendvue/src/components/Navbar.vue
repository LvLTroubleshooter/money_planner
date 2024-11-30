<script setup>
import logo from '@/assets/img/logo.png';
</script>

<template>
  <!-- Navigation Bar -->
  <nav class="bg-blue-950 border-b border-blue-950">
    <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
      <div class="flex h-16 items-center justify-between">
        <div class="flex items-center ">
          <!-- Logo and Title -->
          <a class="flex flex-shrink-0 items-center mr-4" href="index.html">
            <!-- Display the logo image -->
            <img :src="logo" class="h-20 w-auto" alt="Money Planner" />
            <!-- Display the app name for larger screens -->
            <span class="hidden md:block text-white text-2xl font-bold ml-0">
              Money Planner
            </span>
          </a>
        </div>
        <!-- Navigation Buttons -->
        <div class="flex space-x-4">
          <!-- Login Button -->
          <a
              href="login.html"
              class="text-white  bg-indigo-600 hover:bg-indigo-700 hover:text-white rounded-md px-4 py-2 transition"
          >
            Login
          </a>
          <!-- Get Started Button -->
          <a
              href="get-started.html"
              class="text-white bg-custom-color rounded-md px-4 py-2 transition hover:bg-custom-hover-color"
          >
            Get Started
          </a>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
/* Custom background color for the "Get Started" button */
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1); /* Initial gold color */
}

/* Custom hover background color */
.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1); /* Custom light gold hover color */
}
</style>
