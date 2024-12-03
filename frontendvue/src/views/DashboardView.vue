<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
import SummaryCard from "@/components/SummaryCard.vue";
import GoalDeadlineOverview from "@/components/GoalDeadlineOverview.vue";
import IncomeExpenseTrends from "@/components/IncomeExpenseTrends.vue";
import IncomeDistributionChart from "@/components/IncomeDistributionChart.vue";
import ExpenseDistributionChart from "@/components/ExpenseDistributionChart.vue";
import BudgetAllocationChart from "@/components/BudgetAllocationChart.vue";
import LatestTransactions from "@/components/LatestTransactions.vue";
</script>

<template>
  <div class="flex">
    <!-- Sidebar -->
    <div class="fixed w-64">
      <SideNavBar />
    </div>

    <!-- Main content area -->
    <div class="flex-1 bg-gray-200 min-h-screen ml-64 p-6">
      <!-- Summary Cards Section -->
      <div >
        <SummaryCard />
      </div>

      <!-- Graphs Section -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div class="bg-white shadow-md rounded-lg p-4">
          <IncomeExpenseTrends />
        </div>
        <div class="bg-white shadow-md rounded-lg p-4">
          <GoalDeadlineOverview />
        </div>
      </div>

      <!-- Pie Charts Section -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
        <div class="bg-white shadow-md rounded-lg p-4">
          <IncomeDistributionChart />
        </div>
        <div class="bg-white shadow-md rounded-lg p-4">
          <ExpenseDistributionChart />
        </div>
        <div class="bg-white shadow-md rounded-lg p-4">
          <BudgetAllocationChart />
        </div>
      </div>

      <!-- Latest Transactions -->
      <div class="bg-white shadow-md rounded-lg p-4 mt-6">
        <LatestTransactions />
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

.mb-6 {
  margin-bottom: 1.5rem;
}

.gap-6 {
  gap: 1.5rem;
}

.grid {
  display: grid;
}

.grid-cols-1 {
  grid-template-columns: repeat(1, minmax(0, 1fr));
}

.md\:grid-cols-3 {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.lg\:grid-cols-2 {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}
</style>
