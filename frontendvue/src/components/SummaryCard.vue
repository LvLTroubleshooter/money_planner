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
          <h2 class="text-2xl font-bold text-gray-900">Current Balance</h2>
          <p class="mt-2 mb-4 text-gray-800">
            8200$
          </p>
          <!-- Call-to-Action Link -->
          <RouterLink
              to="/income"
              class="cta-link text-black text-left hover:text-gray-500"
          >
            Learn More
          </RouterLink>
        </Card>

        <!-- Total Income Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-green-200 via-green-100 to-green-50">
          <h2 class="text-2xl font-bold text-gray-900">Total Income</h2>
          <p class="mt-2 mb-4 text-gray-800">
            1500$
          </p>
          <!-- Call-to-Action Link -->
          <RouterLink
              to="/expenses"
              class="cta-link text-black text-left hover:text-gray-500"
          >
            Learn More
          </RouterLink>
        </Card>

        <!-- Total Expenses Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-red-200 via-red-100 to-red-50">
          <h2 class="text-2xl font-bold text-gray-900">Total Expenses</h2>
          <p class="mt-2 mb-4 text-gray-800">
            5120$
          </p>
          <!-- Call-to-Action Link -->
          <RouterLink
              to="/goals"
              class="cta-link text-black text-left hover:text-gray-500"
          >
            Learn More
          </RouterLink>
        </Card>
      </div>
    </div>
  </section>


</template>

<style scoped>

</style>
