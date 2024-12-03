<script setup>
import { ref } from 'vue';
import { Doughnut } from 'vue-chartjs';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, ArcElement);

const budgets = ref([
  { category: 'Food', amount: 500 },
  { category: 'Rent', amount: 700 },
  { category: 'Entertainment', amount: 300 },
  { category: 'Bills', amount: 400 },
]);

const budgetData = {
  labels: budgets.value.map((item) => item.category),
  datasets: [
    {
      data: budgets.value.map((item) => item.amount),
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
    },
  ],
};

const options = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top',
    },
  },
};
</script>

<template>
  <div class="p-4 bg-white rounded-lg shadow-md">
    <h3 class="text-center font-bold mb-4">Budget Allocation</h3>
    <div class="h-64">
      <Doughnut :data="budgetData" :options="options" />
    </div>
  </div>
</template>
