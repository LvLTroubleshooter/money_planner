<script setup>
// Import Vue's `defineProps` function for defining component props
import { defineProps } from 'vue';

// Define the component props with default values
defineProps({
  title: {
    type: String,
    default: 'Plan today, thrive tomorrow.', // Default title text
  },
  subtitle: {
    type: String,
    default: 'Start your journey to financial freedom with Money Planner today', // Default subtitle text
  },
});
</script>

<template>
  <!-- Section Container -->
  <section class="bg-blue-950 py-20 mb-4 border-t border-blue-800">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col items-center">
      <div class="text-center">
        <!-- Dynamic Title -->
        <h1 class="text-4xl font-extrabold text-custom-color sm:text-5xl md:text-6xl">
          {{ title }}
        </h1>
        <!-- Dynamic Subtitle -->
        <p class="my-4 text-xl text-white">
          {{ subtitle }}
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Custom background color */
.bg-custom-color {
  background-color: rgba(191, 148, 95, 255);
}

/* Custom hover background color */
.bg-custom-hover {
  background-color: rgba(191, 148, 95, 1);
}

/* Custom border color */
.border-custom-border {
  border-color: rgba(12, 29, 45, 0.5);
}

/* Custom text color */
.text-custom-color {
  color: rgba(191, 148, 95, 1);
}
</style>
