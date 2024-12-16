<script setup>
import SideNavBar from "@/components/SideNavBar.vue";
</script>

<template>
  <div class="flex bg-gray-200 min-h-screen">
    <!-- Sidebar -->
    <SideNavBar />

    <!-- Main Content -->
    <div class="flex-1 ml-64">
      <div class="p-6 pt-20">
        <h1 class="text-2xl font-bold mb-6">Expenses</h1>
        <!-- Add your expenses content here -->
      </div>
    </div>
  </div>
</template> 