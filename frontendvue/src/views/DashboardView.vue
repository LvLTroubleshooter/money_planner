<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
import DashboardNavbar from "@/components/DashboardNavbar.vue";
import SummaryCard from "@/components/SummaryCard.vue";
import GoalDeadlineOverview from "@/components/GoalDeadlineOverview.vue";
import IncomeExpenseTrends from "@/components/IncomeExpenseTrends.vue";
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

const selectedFilter = ref("Week");
const userId = ref(null);
const userData = ref({});

onMounted(async () => {
  const route = useRoute();
  userId.value = route.params.userId;

  if (!userId.value) {
    console.error("No user ID provided in route");
    return;
  }

  try {
    const response = await axios.get(`http://localhost:8080/api/users/${userId.value}`);
    userData.value = response.data;
  } catch (error) {
    console.error("Failed to fetch user data:", error);
  }
});

const handleFilterChange = (filter) => {
  selectedFilter.value = filter;
};
</script>

<template>
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <DashboardNavbar @filter-change="handleFilterChange" />

      <div class="p-6 pt-24">
        <!-- Main Grid Layout -->
        <div class="grid grid-cols-12 gap-6">
          <!-- Left Column - 8 columns wide -->
          <div class="col-span-12 lg:col-span-8 space-y-6">
            <!-- Quick Stats Row -->
            <div class="grid grid-cols-2 gap-6">
              <div class="bg-white rounded-xl shadow-md p-4 border-l-4 border-emerald-500">
                <h3 class="text-gray-500 text-sm font-medium">Total Income</h3>
                <p class="text-2xl font-bold text-gray-800 mt-2">$4,570.00</p>
                <span class="text-green-500 text-sm mt-2 flex items-center">
                  <i class="fas fa-arrow-up mr-1"></i> +12.5%
                </span>
              </div>

              <div class="bg-white rounded-xl shadow-md p-4 border-l-4 border-red-500">
                <h3 class="text-gray-500 text-sm font-medium">Total Expenses</h3>
                <p class="text-2xl font-bold text-gray-800 mt-2">$2,890.00</p>
                <span class="text-red-500 text-sm mt-2 flex items-center">
                  <i class="fas fa-arrow-down mr-1"></i> -4.2%
                </span>
              </div>
            </div>

            <!-- Main Chart -->
            <div class="bg-white rounded-xl shadow-md overflow-hidden">
              <div class="p-4">
                <div class="w-full h-[400px]">
                  <IncomeExpenseTrends :filter="selectedFilter" />
                </div>
              </div>
            </div>
          </div>

          <!-- Right Column - 4 columns wide -->
          <div class="col-span-12 lg:col-span-4 grid grid-rows-2 gap-6 h-full">
            <!-- Goals Progress -->
            <div class="bg-white rounded-xl shadow-md p-4 flex flex-col">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Goals Progress</h3>
                <button class="text-sm text-gray-500 hover:text-gray-700">View All</button>
              </div>
              <div class="space-y-4 flex-grow overflow-auto">
                <div v-for="(goal, index) in 3" :key="index" class="space-y-2">
                  <div class="flex justify-between items-center">
                    <span class="font-medium text-gray-800">Vacation Fund</span>
                    <span class="text-sm text-gray-500">$2,000 / $5,000</span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div class="bg-blue-500 h-2 rounded-full" :style="{ width: '40%' }"></div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Spending Categories -->
            <div class="bg-white rounded-xl shadow-md p-4 flex flex-col">
              <h3 class="text-lg font-semibold text-gray-800 mb-4">Top Categories</h3>
              <div class="space-y-3 flex-grow overflow-auto">
                <div v-for="(category, index) in ['Shopping', 'Food', 'Transport']" :key="index"
                     class="flex items-center justify-between p-2">
                  <div class="flex items-center space-x-3">
                    <div class="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center">
                      <i class="fas fa-shopping-bag text-gray-500"></i>
                    </div>
                    <span class="font-medium text-gray-700">{{ category }}</span>
                  </div>
                  <span class="text-gray-600">$580.00</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Recent Transactions - Full Width -->
          <div class="col-span-12">
            <div class="bg-white rounded-xl shadow-md p-4">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-gray-800">Recent Transactions</h3>
                <button class="text-sm text-gray-500 hover:text-gray-700">View All</button>
              </div>
              <div class="space-y-3">
                <div v-for="i in 4" :key="i" 
                     class="flex items-center justify-between p-3 hover:bg-gray-50 rounded-lg transition">
                  <div class="flex items-center space-x-3">
                    <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                      <i class="fas fa-shopping-bag text-gray-500"></i>
                    </div>
                    <div>
                      <p class="font-medium text-gray-800">Shopping</p>
                      <p class="text-sm text-gray-500">2 hours ago</p>
                    </div>
                  </div>
                  <span class="text-red-500 font-medium">-$150.00</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-custom-bg {
  background-color: rgba(191, 148, 95, 0.1);
}

.rounded-xl {
  transition: all 0.3s ease;
}

.hover\:shadow-lg:hover {
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  transform: translateY(-2px);
}
</style>
