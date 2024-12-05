<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
import DashboardNavbar from "@/components/DashboardNavbar.vue";
import SummaryCard from "@/components/SummaryCard.vue";
import GoalDeadlineOverview from "@/components/GoalDeadlineOverview.vue";
import IncomeExpenseTrends from "@/components/IncomeExpenseTrends.vue";
import LatestTransactions from "@/components/LatestTransactions.vue";
import { ref } from "vue";

// Define filters for dashboard
const selectedFilter = ref("Week");

// Function to update data based on filter
const handleFilterChange = (filter) => {
  selectedFilter.value = filter;
  // Add logic to update graphs/cards here based on the selected filter
};
</script>

<template>
  <div class="flex">
    <!-- Sidebar -->
    <SideNavBar />

    <!-- Main Content Area -->
    <div class="flex-1 ml-64">
      <!-- Navbar -->
      <DashboardNavbar @filter-change="handleFilterChange" />

      <!-- Page Content -->
      <div class="p-4 pt-20 bg-gray-200 min-h-screen">
        <!-- Summary Cards Section -->
        <div class="mb-4">
          <SummaryCard :filter="selectedFilter" />
        </div>

        <!-- Graphs Section -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <div class="bg-white shadow-md rounded-lg p-4">
            <IncomeExpenseTrends :filter="selectedFilter" />
          </div>
          <div class="bg-white shadow-md rounded-lg p-4">
            <GoalDeadlineOverview :filter="selectedFilter" />
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
