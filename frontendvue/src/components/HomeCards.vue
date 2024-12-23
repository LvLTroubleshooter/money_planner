<script setup>
import Card from "@/components/Card.vue";
import { RouterLink } from "vue-router";
</script>

<template>
  <section class="py-16 bg-transparent">
    <div class="container mx-auto px-4">
      <!-- Section Header -->
      <div class="text-center mb-12">
        <h2 class="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
          Smart Financial Management
        </h2>
        <p class="text-lg text-gray-600 max-w-2xl mx-auto">
          Take control of your finances with our comprehensive tools and features
        </p>
      </div>

      <!-- Cards Grid -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
        <!-- Income Card -->
        <Card class="feature-card bg-white">
          <div class="p-6 flex flex-col h-full">
            <div class="flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-br from-custom-light to-custom-color mb-6">
              <i class="pi pi-wallet text-2xl text-white"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900 mb-4">Income Tracking</h3>
            <p class="text-gray-600 mb-6 flex-grow">
              Track your income sources effortlessly. Get clear insights into your earnings and maintain organized financial records.
            </p>
            <a 
              href="https://www.moneyhelper.org.uk/en/everyday-money/budgeting/beginners-guide-to-managing-your-money"
              target="_blank" 
              rel="noopener noreferrer" 
              class="cta-button"
            >
              <span>Manage Income</span>
              <i class="pi pi-external-link ml-2"></i>
            </a>
          </div>
        </Card>

        <!-- Expenses Card -->
        <Card class="feature-card bg-white">
          <div class="p-6 flex flex-col h-full">
            <div class="flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-br from-custom-light to-custom-color mb-6">
              <i class="pi pi-chart-line text-2xl text-white"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900 mb-4">Expense Management</h3>
            <p class="text-gray-600 mb-6 flex-grow">
              Keep track of your spending habits. Categorize expenses and identify areas where you can save more effectively.
            </p>
            <a 
              href="https://www.nerdwallet.com/article/finance/tracking-monthly-expenses"
              target="_blank" 
              rel="noopener noreferrer" 
              class="cta-button"
            >
              <span>Track Expenses</span>
              <i class="pi pi-external-link ml-2"></i>
            </a>
          </div>
        </Card>

        <!-- Goals Card -->
        <Card class="feature-card bg-white">
          <div class="p-6 flex flex-col h-full">
            <div class="flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-br from-custom-light to-custom-color mb-6">
              <i class="pi pi-flag text-2xl text-white"></i>
            </div>
            <h3 class="text-2xl font-bold text-gray-900 mb-4">Financial Goals</h3>
            <p class="text-gray-600 mb-6 flex-grow">
              Set and achieve your financial goals. Monitor your progress and celebrate milestones along your journey.
            </p>
            <a 
              href="https://www.usbank.com/wealth-management/financial-perspectives/financial-planning/how-to-set-financial-goals.html"
              target="_blank" 
              rel="noopener noreferrer" 
              class="cta-button"
            >
              <span>Set Goals</span>
              <i class="pi pi-external-link ml-2"></i>
            </a>
          </div>
        </Card>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Custom Colors */
:root {
  --custom-color: rgba(191, 148, 95, 1);
  --custom-light: rgba(191, 148, 95, 0.7);
  --custom-hover: rgba(164, 120, 65, 1);
}

/* Feature Cards */
.feature-card {
  @apply rounded-2xl shadow-md transition-all duration-300;
  border: 1px solid rgba(191, 148, 95, 0.1);
}

.feature-card:hover {
  @apply shadow-xl transform -translate-y-1;
  border-color: rgba(191, 148, 95, 0.3);
}

/* CTA Buttons */
.cta-button {
  @apply inline-flex items-center justify-center px-6 py-3 rounded-lg font-semibold text-white transition-all duration-300;
  background: linear-gradient(135deg, rgba(191, 148, 95, 0.9), rgba(191, 148, 95, 1));
}

.cta-button:hover {
  background: linear-gradient(135deg, rgba(164, 120, 65, 1), rgba(191, 148, 95, 0.9));
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(191, 148, 95, 0.2);
}

/* Icon Backgrounds */
.bg-gradient-to-br {
  background: linear-gradient(135deg, rgba(191, 148, 95, 0.7), rgba(191, 148, 95, 1));
}

/* Responsive Adjustments */
@media (max-width: 768px) {
  .container {
    padding-left: 1.5rem;
    padding-right: 1.5rem;
  }

  .feature-card {
    margin-bottom: 1.5rem;
  }
}

/* Animations */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.feature-card {
  animation: fadeInUp 0.5s ease-out;
  animation-fill-mode: both;
}

.feature-card:nth-child(1) {
  animation-delay: 0.1s;
}

.feature-card:nth-child(2) {
  animation-delay: 0.2s;
}

.feature-card:nth-child(3) {
  animation-delay: 0.3s;
}

/* Hover Effects */
.feature-card:hover .pi {
  transform: scale(1.1);
  transition: transform 0.3s ease;
}

.cta-button:hover .pi-arrow-right {
  transform: translateX(4px);
  transition: transform 0.3s ease;
}
</style>
