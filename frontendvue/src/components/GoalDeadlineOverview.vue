<script setup>
import { ref } from 'vue';
import { Bar } from 'vue-chartjs';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const labels = ref(['Vacation Fund', 'Emergency Fund', 'Car Purchase', 'Home Renovation', 'Retirement Fund']);
const goalAmounts = ref([2000, 3000, 10000, 8000, 15000]);
const currentAmounts = ref([500, 1500, 2000, 1500, 4000]);

const data = ref({
  labels: labels.value,
  datasets: [
    {
      label: 'Goal Amount',
      data: goalAmounts.value,
      backgroundColor: '#6366F1',
    },
    {
      label: 'Current Amount',
      data: currentAmounts.value,
      backgroundColor: '#34D399',
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
      text: 'Goal Deadline Overview',
      font: {
        size: 18,
      },
    },
  },
  scales: {
    x: {
      title: {
        display: true,
        text: 'Goals',
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
    <Bar :data="data" :options="options" />
  </div>
</template>

<style scoped>
/* Ensuring clean padding */
div {
  padding: 1rem;
}
</style>
