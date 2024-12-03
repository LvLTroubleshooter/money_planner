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

const expenses = ref([
  { category: 'Food', amount: 150 },
  { category: 'Rent', amount: 600 },
  { category: 'Entertainment', amount: 100 },
  { category: 'Bills', amount: 200 },
  { category: 'Savings', amount: 50 },
]);

const expenseData = {
  labels: expenses.value.map((item) => item.category),
  datasets: [
    {
      data: expenses.value.map((item) => item.amount),
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF'],
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
    <h3 class="text-center font-bold mb-4">Expense Distribution</h3>
    <div class="h-64">
      <Doughnut :data="expenseData" :options="options" />
    </div>
  </div>
</template>
