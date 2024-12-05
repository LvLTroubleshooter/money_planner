<script setup>
import { defineEmits } from "vue";

const emit = defineEmits(["add-goal"]);
</script>

<template>
  <div class="bg-white shadow-md fixed top-0 left-64 right-0 z-50">
    <div class="flex items-center justify-between px-4 py-3">
      <!-- Title -->
      <div class="text-xl font-bold text-gray-700">Money Planner</div>

      <!-- Add Goal Button -->
      <button
          @click="$emit('add-goal')"
          class="bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600 transition"
      >
        Add Goal
      </button>
    </div>
  </div>
</template>

<style scoped>
</style>
