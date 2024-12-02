<script setup>
import Card from "@/components/Card.vue";
import {RouterLink} from "vue-router";

</script>

<template>
  <!-- Section Container -->
  <section class="py-0 bg-gray-50">
    <div class="container-xl lg:container m-auto">
      <!-- Grid Layout -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4 p-4 rounded-lg">
        <!-- Income Tracking Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-green-300 via-green-200 to-green-100">
          <h2 class="text-2xl font-bold text-gray-900">Take Control of Your Income</h2>
          <p class="mt-2 mb-4 text-gray-800">
            Track your income to stay on top of your finances. See exactly where your money comes from, and keep everything organized.
          </p>
          <!-- Call-to-Action Link -->
          <RouterLink
              to="/income"
              class="cta-link text-black text-left hover:text-gray-500"
          >
            Learn More
          </RouterLink>
        </Card>

        <!-- Expense Management Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-red-300 via-red-200 to-red-100">
          <h2 class="text-2xl font-bold text-gray-900">Master Your Expenses</h2>
          <p class="mt-2 mb-4 text-gray-800">
            Track your expenses to stay within budget. Our app helps you understand where your money is going and how to optimize spending.
          </p>
          <!-- Call-to-Action Link -->
          <RouterLink
              to="/expenses"
              class="cta-link text-black text-left hover:text-gray-500"
          >
            Learn More
          </RouterLink>
        </Card>

        <!-- Financial Goals Section -->
        <Card class="min-h-full flex flex-col justify-between shadow-lg border rounded-lg p-6 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-blue-300 via-blue-200 to-blue-100">
          <h2 class="text-2xl font-bold text-gray-900">Achieve Your Financial Goals</h2>
          <p class="mt-2 mb-4 text-gray-800">
            Set and track your financial goals easily with our app. Stay motivated by monitoring your progress and reaching milestones along the way.
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
/* Base Styles for CTA Links */
.cta-link {
  display: inline-block;
  font-size: 16px;
  font-weight: 600;
  text-decoration: none;
  letter-spacing: 1px;
  transition: all 0.3s ease;
  margin-top: 16px; /* Space between the text and link */
  padding: 8px 0; /* Add padding to make it look button-like */
}

/* Hover Effect for Cards */
.card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
  transform: translateY(-8px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

/* Make sure all cards are the same height */
.card-container {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

/* Card Styling */
.shadow-lg {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* A more pronounced shadow */
}

.border {
  border: 1px solid rgba(0, 0, 0, 0.1); /* Light border to give it more definition */
}

.rounded-lg {
  border-radius: 10px; /* Smooth rounded corners */
}

.p-6 {
  padding: 24px; /* More padding inside the card */
}

/* Hover Effect - Slight scaling */
.hover\:scale-105:hover {
  transform: scale(1.05); /* Slight scaling on hover for a card lift effect */
}

/* Background Gradients */
.bg-gradient-to-r {
  background: linear-gradient(135deg, rgba(191, 148, 95, 0.7), rgba(191, 148, 95, 0.5));
}

.bg-gradient-to-r:hover {
  background: linear-gradient(135deg, rgba(191, 148, 95, 0.9), rgba(191, 148, 95, 0.7));
}
</style>
