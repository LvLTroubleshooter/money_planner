<script setup>
// No filter-related code needed
</script>

<template>
  <div class="bg-white shadow-md fixed top-0 left-64 right-0 z-50">
    <div class="flex items-center justify-between px-4 py-3">
      <!-- Left Side - Title and Breadcrumb -->
      <div class="flex items-center space-x-2">
        <h1 class="text-xl font-bold text-gray-700">Money Planner</h1>
      </div>
    </div>
  </div>
</template>

<style scoped>
.transition-all {
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 200ms;
}

.shadow-sm {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}

.z-50 {
  z-index: 50;
}
</style>
