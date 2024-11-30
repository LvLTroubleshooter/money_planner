<script setup>
import { RouterLink } from "vue-router";
import logo from '@/assets/img/logo.png';
</script>

<template>

  <nav class="bg-blue-950 border-b border-blue-950">
    <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
      <div class="flex h-16 items-center justify-between">
        <div class="flex items-center ">

          <a class="flex flex-shrink-0 items-center mr-4" href="/">

            <img :src="logo" class="h-20 w-auto" alt="Money Planner" />

            <span class="hidden md:block text-white text-2xl font-bold ml-0">
              Money Planner
            </span>
          </a>
        </div>

        <div class="flex space-x-4">
          <!-- Login Button -->
          <RouterLink
              to="/login-page"
              class="text-white  bg-indigo-600 hover:bg-indigo-700 hover:text-white rounded-md px-4 py-2 transition"
          >
            Log in
          </RouterLink>
          <!-- Get Started Button -->
          <RouterLink
              to="/get-started"
              class="text-white bg-custom-color rounded-md px-4 py-2 transition hover:bg-custom-hover-color"
          >
            Get Started
          </RouterLink>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>

.bg-custom-color {
  background-color: rgba(191, 148, 95, 1);
}


.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1);
}
</style>
