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
      <!-- Existing Content -->
      <div class="text-xl font-bold text-gray-700">Money Planner</div>

      <!-- Filters -->
      <div class="flex space-x-4">
        <button
            v-for="filter in filters"
            :key="filter"
            :class="[
            'px-4 py-2 rounded-lg text-sm font-semibold transition-colors',
            activeFilter.current === filter
              ? 'bg-blue-500 text-white'
              : 'bg-gray-200 text-gray-600 hover:bg-blue-100'
          ]"
            @click="selectFilter(filter)"
        >
          {{ filter }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1);
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1);
}
</style>
