<script setup>
import { reactive, defineProps, defineEmits, watch } from "vue";

const props = defineProps({
  goal: {
    type: Object,
    required: true
  }
});

const emit = defineEmits(["close", "save"]);

const updatedGoal = reactive({
  goalId: null,
  goalName: "",
  goalAmount: 0,
  currentAmount: 0,
  deadline: "",
  user: null
});

// Watch for changes in the props.goal and update updatedGoal
watch(() => props.goal, (newGoal) => {
  if (newGoal) {
    updatedGoal.goalId = newGoal.goalId;
    updatedGoal.goalName = newGoal.goalName;
    updatedGoal.goalAmount = newGoal.goalAmount;
    updatedGoal.currentAmount = newGoal.currentAmount;
    updatedGoal.deadline = newGoal.deadline;
    updatedGoal.user = newGoal.user;
  }
}, { immediate: true });

const saveGoal = () => {
  if (updatedGoal.goalName && updatedGoal.goalAmount && updatedGoal.deadline) {
    emit("save", { ...updatedGoal });
    emit("close");
  }
};
</script>

<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
    <div class="bg-white rounded-lg p-6 w-96 shadow-lg">
      <h3 class="text-lg font-bold text-gray-700 mb-4">Edit Goal</h3>
      <div class="mb-4">
        <label class="block text-gray-600 text-sm">Goal Name</label>
        <input
            v-model="updatedGoal.goalName"
            type="text"
            class="w-full p-2 border rounded-lg"
            placeholder="Enter goal name"
        />
      </div>
      <div class="mb-4">
        <label class="block text-gray-600 text-sm">Goal Amount</label>
        <input
            v-model="updatedGoal.goalAmount"
            type="number"
            class="w-full p-2 border rounded-lg"
            placeholder="Enter goal amount"
        />
      </div>
      <div class="mb-4">
        <label class="block text-gray-600 text-sm">Current Amount</label>
        <input
            v-model="updatedGoal.currentAmount"
            type="number"
            class="w-full p-2 border rounded-lg"
            placeholder="Enter current amount"
        />
      </div>
      <div class="mb-4">
        <label class="block text-gray-600 text-sm">Deadline</label>
        <input
            v-model="updatedGoal.deadline"
            type="date"
            class="w-full p-2 border rounded-lg"
        />
      </div>
      <div class="flex justify-end space-x-4">
        <button
            @click="$emit('close')"
            class="px-4 py-2 bg-gray-300 rounded-lg hover:bg-gray-400"
        >
          Cancel
        </button>
        <button
            @click="saveGoal"
            class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
        >
          Save
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>
