<template>
  <footer class="bg-blue-950 text-white py-12">
    <div class="container mx-auto px-6">
      <!-- Centered Content Section -->
      <div class="text-center mb-12">
        <h2 class="text-3xl font-semibold mb-2">Let's Connect</h2>
        <p class="text-sm mb-6">
          Our users stay up to date with the latest product updates & announcements.
        </p>
        <form @submit.prevent class="space-y-4 flex flex-col items-center">
          <!-- Input field with rounded corners -->
          <input
              type="email"
              placeholder="Enter your email"
              class="w-80 p-3 pl-4 pr-4 text-black rounded-full focus:outline-none mb-1"
              required
          />
          <!-- Join button underneath the input (width same as input) -->
          <button
              type="submit"
              class="w-80 py-3 bg-custom-color hover:bg-custom-hover-color text-white rounded-full"
          >
            Join
          </button>
        </form>
      </div>

      <!-- Social Media Section -->
      <div class="text-center mb-8">
        <div class="flex justify-center space-x-8">
          <a href="#" class="text-indigo-400 hover:text-indigo-500">
            <i class="pi pi-facebook text-3xl"></i>
          </a>
          <a href="#" class="text-indigo-400 hover:text-indigo-500">
            <i class="pi pi-instagram text-3xl"></i>
          </a>
          <a href="#" class="text-indigo-400 hover:text-indigo-500">
            <i class="pi pi-linkedin text-3xl"></i>
          </a>
          <a href="#" class="text-indigo-400 hover:text-indigo-500">
            <i class="pi pi-twitter text-3xl"></i>
          </a>
          <a href="#" class="text-indigo-400 hover:text-indigo-500">
            <i class="pi pi-youtube text-3xl"></i>
          </a>
        </div>
      </div>

      <!-- Footer Bottom Section -->
      <div class="flex justify-between items-center text-sm border-t border-blue-800 pt-4">
        <div>© 2024 Money Planner. All rights reserved.</div>
        <div class="flex items-center space-x-4">
          <img src="@/assets/img/logo.png" alt="Logo" class="w-8 h-8" />
          <span class="font-semibold">Money Planner</span>
        </div>

      </div>
    </div>
  </footer>
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

<script setup lang="ts">
</script>