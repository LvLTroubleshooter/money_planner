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
const userId = ref(null); // Declare userId as a ref for reactivity
const userData = ref({});

// Fetch user data
onMounted(async () => {
  const route = useRoute();
  userId.value = route.params.userId; // Initialize userId from route params

  console.log("User ID from route:", userId.value); // Debugging log

  if (!userId.value) {
    console.error("No user ID provided in route");
    return;
  }

  try {
    const response = await axios.get(`http://localhost:8080/api/users/${userId.value}`);
    userData.value = response.data;
    console.log("Fetched user data:", userData.value); // Debugging log
  } catch (error) {
    console.error("Failed to fetch user data:", error);
  }
});

// Function to update data based on filter
const handleFilterChange = (filter) => {
  selectedFilter.value = filter;
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
        <div class="mb-4">
          <SummaryCard :filter="selectedFilter" />
        </div>
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
/* Styling unchanged */
</style>
