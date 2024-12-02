<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
import LatestTransactions from "@/components/LatestTransactions.vue";
import SummaryCard from "@/components/SummaryCard.vue";
import BackButton from "@/components/BackButton.vue";
</script>

<template>
  <div class="flex">
    <SideNavBar/>

    <!-- Main content area -->
    <div class="flex-1 bg-gray-200 min-h-screen ml-64 p-6">
      <SummaryCard />

   <LatestTransactions />





    </div>
  </div>
</template>

<style scoped>
</style>
