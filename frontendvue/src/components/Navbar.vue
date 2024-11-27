<script setup>
import logo from '@/assets/img/logo.png'

</script>

<template>
  <nav class="bg-blue-950 border-b border-blue-950">
    <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
      <div class="flex h-16 items-center justify-between">
        <div class="flex items-center">
          <!-- Logo -->
          <a class="flex flex-shrink-0 items-center mr-4" href="index.html">
            <img :src="logo" class="h-20 w-auto" alt="Money Planner" />
            <span class="hidden md:block text-white text-2xl font-bold ml-0">
            Money Planner
          </span>
          </a>
        </div>
        <div class="flex space-x-2">
          <a
              href="index.html"
              class="text-white bg-indigo-950 hover:bg-indigo-950 hover:text-white rounded-md px-3 py-2"
          >
            Home
          </a>
          <a
              href="jobs.html"
              class="text-white hover:bg-indigo-950 hover:text-white rounded-md px-3 py-2"
          >
            Jobs
          </a>
          <a
              href="add-job.html"
              class="text-white hover:bg-indigo-950 hover:text-white rounded-md px-3 py-2"
          >
            Add Job
          </a>
        </div>
      </div>
    </div>
  </nav>
</template>

