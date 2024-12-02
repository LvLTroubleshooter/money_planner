<script setup>
import {RouterLink} from "vue-router";
import Card from "@/components/Card.vue";

defineProps({
  title: String,
  value: String,
  description: String,
});
</script>

<template>
  <section class="bg-gray-200">
    <div class="container-xl lg:container m-auto">
      <!-- Grid Layout -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4 p-4 rounded-lg">
        <!-- Current Balance Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-indigo-200 via-indigo-100 to-indigo-50">
          <h2 class="text-1xl font-bold text-gray-900">Current Balance</h2>
          <p class="mt-2 mb-4 text-gray-600 text-5xl">$8200</p>
          <p class="pi pi-arrow-circle-down text-red-600 "> 21%</p>

        </Card>

        <!-- Total Income Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-green-200 via-green-100 to-green-50">
          <h2 class="text-1xl font-bold text-gray-900">Total Income</h2>
          <p class="mt-2 mb-4 text-gray-600 text-5xl">$1500</p>
          <p class="pi pi-arrow-circle-up text-green-600 "> 12%</p>
        </Card>

        <!-- Total Expenses Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-red-200 via-red-100 to-red-50">
          <h2 class="text-1xl font-bold text-gray-900">Total Expenses</h2>
          <p class="mt-2 mb-4 text-gray-600 text-5xl">$5120</p>
          <p class="pi pi-arrow-circle-down text-red-600 "> 5%</p>
        </Card>
      </div>
    </div>
  </section>


</template>

<style scoped>

</style>
