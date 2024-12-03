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

const incomeSources = ref([
  { source: 'Salary', amount: 3000 },
  { source: 'Freelance', amount: 1200 },
  { source: 'Investment', amount: 500 },
  { source: 'Side Business', amount: 800 },
]);

const incomeData = {
  labels: incomeSources.value.map((item) => item.source),
  datasets: [
    {
      data: incomeSources.value.map((item) => item.amount),
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
    <h3 class="text-center font-bold mb-4">Income Distribution</h3>
    <div class="h-64">
      <Doughnut :data="incomeData" :options="options" />
    </div>
  </div>
</template>
