<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from "@/utils/axios";
import { Line } from 'vue-chartjs';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler
);

const selectedFilter = ref('Week');
const filterOptions = [
  { label: 'Week', value: 'Week' },
  { label: 'Month', value: 'Month' },
  { label: 'Year', value: 'Year' }
];

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: 'Income',
      borderColor: '#10B981',
      backgroundColor: 'rgba(16, 185, 129, 0.15)',
      borderWidth: 3,
      tension: 0.4,
      fill: true,
      data: [],
      pointBackgroundColor: '#10B981',
      pointBorderColor: '#FFFFFF',
      pointBorderWidth: 2,
      pointRadius: 4,
      pointHoverRadius: 6,
      pointHoverBorderWidth: 3,
      pointHoverBackgroundColor: '#10B981',
      pointHoverBorderColor: '#FFFFFF'
    },
    {
      label: 'Expenses',
      borderColor: '#EF4444',
      backgroundColor: 'rgba(239, 68, 68, 0.15)',
      borderWidth: 3,
      tension: 0.4,
      fill: true,
      data: [],
      pointBackgroundColor: '#EF4444',
      pointBorderColor: '#FFFFFF',
      pointBorderWidth: 2,
      pointRadius: 4,
      pointHoverRadius: 6,
      pointHoverBorderWidth: 3,
      pointHoverBackgroundColor: '#EF4444',
      pointHoverBorderColor: '#FFFFFF'
    }
  ]
});

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  animation: {
    duration: 1000,
    easing: 'easeInOutQuart'
  },
  scales: {
    y: {
      beginAtZero: true,
      grid: {
        color: 'rgba(0, 0, 0, 0.05)',
        drawBorder: false
      },
      border: {
        display: false
      },
      ticks: {
        callback: (value) => `$${value.toLocaleString()}`,
        font: {
          size: 12,
          family: "'Inter', sans-serif"
        },
        color: '#64748B',
        padding: 8
      }
    },
    x: {
      grid: {
        display: false,
        drawBorder: false
      },
      border: {
        display: false
      },
      ticks: {
        font: {
          size: 12,
          family: "'Inter', sans-serif"
        },
        color: '#64748B',
        padding: 8
      }
    }
  },
  plugins: {
    legend: {
      position: 'top',
      align: 'end',
      labels: {
        usePointStyle: true,
        pointStyle: 'circle',
        padding: 20,
        font: {
          size: 12,
          family: "'Inter', sans-serif",
          weight: '600'
        },
        color: '#64748B',
        boxWidth: 8,
        boxHeight: 8
      }
    },
    tooltip: {
      mode: 'index',
      intersect: false,
      backgroundColor: 'rgba(255, 255, 255, 0.95)',
      titleColor: '#1E293B',
      bodyColor: '#475569',
      borderColor: 'rgba(0, 0, 0, 0.1)',
      borderWidth: 1,
      padding: 12,
      cornerRadius: 8,
      titleFont: {
        size: 14,
        weight: '600',
        family: "'Inter', sans-serif"
      },
      bodyFont: {
        size: 13,
        family: "'Inter', sans-serif"
      },
      callbacks: {
        label: (context) => {
          let label = context.dataset.label || '';
          if (label) {
            label += ': ';
          }
          if (context.parsed.y !== null) {
            label += `$${context.parsed.y.toLocaleString()}`;
          }
          return label;
        },
        title: (tooltipItems) => {
          return tooltipItems[0].label;
        }
      },
      displayColors: true,
      boxWidth: 8,
      boxHeight: 8,
      usePointStyle: true,
      bodySpacing: 8
    }
  },
  interaction: {
    intersect: false,
    mode: 'index'
  },
  elements: {
    line: {
      borderJoinStyle: 'round'
    }
  }
};

const fetchChartData = async () => {
  try {
    const userId = localStorage.getItem('userId');
    if (!userId) return;

    const response = await axios.get(`/api/transactions/user/${userId}/trends?period=${selectedFilter.value.toLowerCase()}`);
    const { incomeData, expenseData } = response.data;

    chartData.value = {
      labels: incomeData.map(item => item.date),
      datasets: [
        {
          ...chartData.value.datasets[0],
          data: incomeData.map(item => item.amount)
        },
        {
          ...chartData.value.datasets[1],
          data: expenseData.map(item => item.amount)
        }
      ]
    };
  } catch (error) {
    console.error('Failed to fetch chart data:', error);
  }
};

watch(() => selectedFilter.value, () => {
  fetchChartData();
});

onMounted(() => {
  fetchChartData();
});
</script>

<template>
  <div class="bg-white rounded-xl shadow-sm p-4 border border-gray-100">
    <div class="flex justify-between items-center mb-4">
      <h2 class="text-lg font-semibold text-gray-800">Income vs Expenses</h2>
      <div class="flex gap-2">
        <button
          v-for="option in filterOptions"
          :key="option.value"
          @click="selectedFilter = option.value"
          :class="[
            'px-3 py-1.5 text-sm font-medium rounded-lg transition-colors',
            selectedFilter === option.value
              ? 'bg-primary text-white'
              : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
          ]"
        >
          {{ option.label }}
        </button>
      </div>
    </div>
    <div class="h-[300px]">
      <Line :data="chartData" :options="chartOptions" />
    </div>
  </div>
</template>

<style scoped>
.bg-white {
  background: linear-gradient(to bottom, #ffffff, #fafafa);
}

/* Custom scrollbar for webkit browsers */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.2);
}

.bg-primary {
  background-color: #10B981;
}

button {
  transition: all 0.2s ease;
}

button:hover {
  transform: translateY(-1px);
}

button:active {
  transform: translateY(0);
}
</style>
