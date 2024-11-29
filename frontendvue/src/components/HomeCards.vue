<script setup>
import Card from "@/components/Card.vue";
</script>

<template>
  <!-- Section Container -->
  <section class="py-4 bg-gray-50">
    <div class="container-xl lg:container m-auto">
      <!-- Grid Layout -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4 p-4 rounded-lg">
        <!-- Income Tracking Section -->
        <Card bg="bg-green-100">
          <h2 class="text-2xl font-bold text-green-800">Track Your Income</h2>
          <p class="mt-2 mb-4 text-green-700">
            Keep a record of all your income sources and stay updated on where your money is coming from.
          </p>
          <!-- Call-to-Action Button -->
          <a
              href="/income"
              class="inline-block bg-green-700 text-white rounded-lg px-4 py-2 hover:bg-green-800"
          >
            Start Tracking
          </a>
        </Card>

        <!-- Expense Management Section -->
        <Card bg="bg-red-100">
          <h2 class="text-2xl font-bold text-red-800">Manage Your Expenses</h2>
          <p class="mt-2 mb-4 text-red-700">
            Categorize and monitor your spending to ensure you stay within your budget.
          </p>
          <!-- Call-to-Action Button -->
          <a
              href="/expenses"
              class="inline-block bg-red-700 text-white rounded-lg px-4 py-2 hover:bg-red-800"
          >
            View Expenses
          </a>
        </Card>

        <!-- Financial Goals Section -->
        <Card bg="bg-blue-100">
          <h2 class="text-2xl font-bold text-blue-800">Set Financial Goals</h2>
          <p class="mt-2 mb-4 text-blue-700">
            Define and achieve your savings or investment goals with ease.
          </p>
          <!-- Call-to-Action Button -->
          <a
              href="/goals"
              class="inline-block bg-blue-700 text-white rounded-lg px-4 py-2 hover:bg-blue-800"
          >
            Create Goals
          </a>
        </Card>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Custom styles for buttons and layout, if needed */
</style>
