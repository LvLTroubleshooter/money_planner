<script setup>
import { ref } from 'vue';
import { Line } from 'vue-chartjs';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  Filler
} from 'chart.js';

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  Filler
);

const labels = ref(['January', 'February', 'March', 'April']);
const incomeData = ref([3000, 4000, 3500, 5000]);
const expenseData = ref([2000, 2500, 2000, 3000]);

const data = ref({
  labels: labels.value,
  datasets: [
    {
      label: 'Income',
      data: incomeData.value,
      borderColor: '#10B981',
      backgroundColor: 'rgba(16, 185, 129, 0.2)',
      fill: true,
      tension: 0.4,
    },
    {
      label: 'Expenses',
      data: expenseData.value,
      borderColor: '#EF4444',
      backgroundColor: 'rgba(239, 68, 68, 0.2)',
      fill: true,
      tension: 0.4,
    },
  ],
});

const options = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top',
    },
    title: {
      display: true,
      text: 'Income and Expense Trends',
      font: {
        size: 18,
      },
    },
  },
  scales: {
    x: {
      title: {
        display: true,
        text: 'Months',
      },
    },
    y: {
      title: {
        display: true,
        text: 'Amount ($)',
      },
    },
  },
});
</script>

<template>
  <div style="height: 400px;">
    <Line :data="data" :options="options" />
  </div>
</template>

<style scoped>
/* Ensuring clean padding */
div {
  padding: 1rem;
}
</style>
