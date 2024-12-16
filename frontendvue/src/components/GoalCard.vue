<script setup>
defineProps({
  goal: {
    type: Object,
    required: true,
    validator: (goal) => {
      return goal && 
             typeof goal.goalName === 'string' &&
             typeof goal.goalAmount === 'number' &&
             typeof goal.currentAmount === 'number' &&
             goal.deadline !== undefined &&
             goal.goalId !== undefined;
    }
  }
});

const progress = (currentAmount, goalAmount) =>
    Math.min((currentAmount / goalAmount) * 100, 100).toFixed(0);

const emit = defineEmits(["edit", "delete"]);
</script>

<template>
  <div class="bg-white shadow-md rounded-lg p-4 relative">
    <!-- Goal Info -->
    <h3 class="text-lg font-bold text-gray-700">{{ goal.goalName }}</h3>
    <p class="text-sm text-gray-500 mt-1">Goal Amount: ${{ goal.goalAmount.toFixed(2) }}</p>
    <p class="text-sm text-gray-500">Current Amount: ${{ goal.currentAmount.toFixed(2) }}</p>
    <p class="text-sm text-gray-500">Deadline: {{ new Date(goal.deadline).toLocaleDateString() }}</p>
    <div class="mt-4">
      <div class="w-full bg-gray-300 h-2 rounded-lg overflow-hidden">
        <div
            class="bg-green-500 h-2"
            :style="{ width: progress(goal.currentAmount, goal.goalAmount) + '%' }"
        ></div>
      </div>
      <p class="text-right text-xs text-gray-500 mt-1">
        {{ progress(goal.currentAmount, goal.goalAmount) }}% Complete
      </p>
    </div>

    <!-- Action Buttons -->
    <div class="absolute top-2 right-2 flex space-x-2">
      <button
          class="bg-yellow-500 text-white px-2 py-1 rounded text-sm hover:bg-yellow-600"
          @click="$emit('edit', goal)"
      >
        Edit
      </button>
      <button
          class="bg-red-500 text-white px-2 py-1 rounded text-sm hover:bg-red-600"
          @click="$emit('delete', goal.goalId)"
      >
        Delete
      </button>
    </div>
  </div>
</template>

<style scoped>
</style>
