<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
import SummaryCard from "@/components/SummaryCard.vue";
import GoalDeadlineOverview from "@/components/GoalDeadlineOverview.vue";
import IncomeExpenseTrends from "@/components/IncomeExpenseTrends.vue";
</script>

<template>
  <div class="flex">
    <SideNavBar />

    <!-- Main content area -->
    <div class="flex-1 bg-gray-200 min-h-screen ml-64 p-6">
      <!-- Summary Cards Section -->
      <SummaryCard />

      <!-- Graphs Section -->
      <div class="flex flex-wrap gap-6 mt-6">
        <div class="flex-1 bg-white shadow-md rounded-lg p-4">
          <IncomeExpenseTrends />
        </div>
        <div class="flex-1 bg-white shadow-md rounded-lg p-4">
          <GoalDeadlineOverview />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-gray-200 {
  background-color: #edf2f7;
}

.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.p-6 {
  padding: 1.5rem;
}

.mt-6 {
  margin-top: 1.5rem;
}

.gap-6 {
  gap: 1.5rem;
}

.flex-1 {
  flex: 1;
}

.flex-wrap {
  flex-wrap: wrap;
}
</style>
