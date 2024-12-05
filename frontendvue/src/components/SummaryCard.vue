<script setup>
import { reactive } from "vue";
import Card from "@/components/Card.vue";

const data = reactive({
  currentBalance: { title: "Current Balance", value: "$8200", change: "21%", icon: "pi pi-wallet", color: "text-indigo-600" },
  totalIncome: { title: "Total Income", value: "$1500", change: "12%", icon: "pi pi-money-bill", color: "text-green-600" },
  totalExpenses: { title: "Total Expenses", value: "$5120", change: "5%", icon: "pi pi-credit-card", color: "text-red-600" },
  totalSavings: { title: "Total Savings", value: "$3000", change: "10%", icon: "pi pi-save", color: "text-yellow-600" },
});

const views = reactive(["This Month", "This Year", "Overall"]);
const activeView = reactive({
  currentBalance: 0,
  totalIncome: 0,
  totalExpenses: 0,
  totalSavings: 0,
});

const toggleView = (cardKey) => {
  activeView[cardKey] = (activeView[cardKey] + 1) % views.length;
};
</script>

<template>
  <section class="bg-gray-200">
    <div class="container-xl lg:container m-auto">
      <!-- Grid Layout -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 p-2 rounded-lg">
        <!-- Current Balance Section -->
        <Card
            class="flex flex-col shadow-lg rounded-lg p-4 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-indigo-200 via-indigo-100 to-indigo-50 cursor-pointer"
            @click="toggleView('currentBalance')"
        >
          <div class="flex justify-between items-center">
            <div>
              <h1 class="text-sm font-bold text-gray-900">{{ data.currentBalance.title }}</h1>
              <p class="mt-2 mb-2 text-gray-600 text-2xl">{{ data.currentBalance.value }}</p>
              <p :class="data.currentBalance.color">{{ views[activeView.currentBalance] }}</p>
            </div>
            <i :class="data.currentBalance.icon" class="text-5xl text-indigo-400"></i>
          </div>
        </Card>

        <!-- Total Income Section -->
        <Card
            class="flex flex-col shadow-lg rounded-lg p-4 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-green-200 via-green-100 to-green-50 cursor-pointer"
            @click="toggleView('totalIncome')"
        >
          <div class="flex justify-between items-center">
            <div>
              <h1 class="text-sm font-bold text-gray-900">{{ data.totalIncome.title }}</h1>
              <p class="mt-2 mb-2 text-gray-600 text-2xl">{{ data.totalIncome.value }}</p>
              <p :class="data.totalIncome.color">{{ views[activeView.totalIncome] }}</p>
            </div>
            <i :class="data.totalIncome.icon" class="text-5xl text-green-400"></i>
          </div>
        </Card>

        <!-- Total Expenses Section -->
        <Card
            class="flex flex-col shadow-lg rounded-lg p-4 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-red-200 via-red-100 to-red-50 cursor-pointer"
            @click="toggleView('totalExpenses')"
        >
          <div class="flex justify-between items-center">
            <div>
              <h1 class="text-sm font-bold text-gray-900">{{ data.totalExpenses.title }}</h1>
              <p class="mt-2 mb-2 text-gray-600 text-2xl">{{ data.totalExpenses.value }}</p>
              <p :class="data.totalExpenses.color">{{ views[activeView.totalExpenses] }}</p>
            </div>
            <i :class="data.totalExpenses.icon" class="text-5xl text-red-400"></i>
          </div>
        </Card>

        <!-- Total Savings Section -->
        <Card
            class="flex flex-col shadow-lg rounded-lg p-4 transition-transform transform hover:scale-105 hover:shadow-xl bg-gradient-to-r from-yellow-200 via-yellow-100 to-yellow-50 cursor-pointer"
            @click="toggleView('totalSavings')"
        >
          <div class="flex justify-between items-center">
            <div>
              <h1 class="text-sm font-bold text-gray-900">{{ data.totalSavings.title }}</h1>
              <p class="mt-2 mb-2 text-gray-600 text-2xl">{{ data.totalSavings.value }}</p>
              <p :class="data.totalSavings.color">{{ views[activeView.totalSavings] }}</p>
            </div>
            <i :class="data.totalSavings.icon" class="text-5xl text-yellow-400"></i>
          </div>
        </Card>
      </div>
    </div>
  </section>
</template>

<style scoped>
.cursor-pointer {
  cursor: pointer;
}
</style>
