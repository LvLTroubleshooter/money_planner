<script setup>
import { reactive, defineEmits } from "vue";

const filters = ["Week", "Month", "Year", "All Time"];
const activeFilter = reactive({ current: "Week" });
const emit = defineEmits(["filter-change"]);

const selectFilter = (filter) => {
  activeFilter.current = filter;
  emit("filter-change", filter);
};
</script>

<template>
  <div class="bg-white shadow-md fixed top-0 left-64 right-0 z-50">
    <div class="flex items-center justify-between px-4 py-3">
      <!-- Left Side - Title and Breadcrumb -->
      <div class="flex items-center space-x-2">
        <h1 class="text-xl font-bold text-gray-700">Money Planner</h1>
        <div class="text-sm text-gray-500 flex items-center">
          <span class="mx-1">/</span>
          <span>{{ activeFilter.current }}</span>
        </div>
      </div>

      <!-- Right Side - Filters -->
      <div class="flex items-center">
        <div class="bg-gray-100 rounded-lg p-0.5">
          <div class="flex space-x-0.5">
            <button
              v-for="filter in filters"
              :key="filter"
              :class="[
                'px-3 py-1.5 rounded-lg text-sm font-medium transition-all duration-200',
                activeFilter.current === filter
                  ? 'bg-white text-gray-800 shadow-sm'
                  : 'text-gray-600 hover:text-gray-800'
              ]"
              @click="selectFilter(filter)"
            >
              {{ filter }}
            </button>
          </div>
        </div>
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
