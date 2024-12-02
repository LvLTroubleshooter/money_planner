<script setup>
import { ref } from 'vue';

// Sample transactions, with type indicating whether it's income or expense
const transactions = ref([
  {
    id: 1,
    name: 'Sebastian Kingsley',
    date: '02/06/2023',
    amount: 550.2,
    type: 'income', // income or expense
  },
  {
    id: 2,
    name: 'Amazon Prime',
    date: '02/06/2023',
    amount: 43.9,
    type: 'expense',
  },
  {
    id: 3,
    name: 'Penelope Everly',
    date: '06/06/2023',
    amount: 34.9,
    type: 'expense',
  },
  {
    id: 4,
    name: 'Samwise Baggins',
    date: '06/12/2023',
    amount: 100,
    type: 'expense',
  },
  {
    id: 5,
    name: 'Selvio Corlioni',
    date: '12/12/2023',
    amount: 1030,
    type: 'income',
  },

]);

// Method to format the amount
const formatAmount = (amount, type) => {
  return {
    formatted: `$${amount.toFixed(2)}`,
    color: type === 'income' ? 'text-green-500' : 'text-red-500',
  };
};
</script>

<template>
  <!-- Header with Title and Filter Dropdown (above the table) -->
  <div class="px-2 py-2 border-b">
    <div class="flex justify-between items-center">
      <h2 class="text-lg font-bold">Latest Transactions</h2> <!-- Bold Title -->
      <div class="flex items-center space-x-4">
        <!-- Filter Dropdown -->
        <select class="px-4 py-2 rounded-md bg-gray-100 border border-gray-300 shadow-sm">
          <option value="expenses">Expenses</option>
          <option value="income">Income</option>
          <option value="all">All</option>
        </select>
      </div>
    </div>
  </div>

  <!-- Transactions Table -->
  <div class="bg-white shadow-md rounded-lg overflow-hidden px-4 py-4"> <!-- Added padding here -->
    <div class="overflow-x-auto max-h-max">
      <table class="w-full table-auto pt-4"> <!-- Added padding-top here -->
        <!-- Table Header: Name, Date, Amount -->
        <thead class="bg-blue-950">
        <tr>
          <th class="px-1 py-1 font-medium text-white">Transaction name</th>
          <th class="px-1 py-1 font-medium text-white">Date</th>
          <th class="px-1 py-1 font-medium text-white">Amount</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(transaction, index) in transactions" :key="transaction.id"
            :class="index % 2 === 0 ? 'bg-gray-100' : 'bg-gray-300'" class="border-b hover:bg-gray-50">
          <td class="px-1 py-1">{{ transaction.name }}</td>
          <td class="px-1 py-1">{{ transaction.date }}</td>
          <td
              class="px-1 py-1"
              :class="formatAmount(transaction.amount, transaction.type).color"
          >
            {{ formatAmount(transaction.amount, transaction.type).formatted }}
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.bg-white {
  background-color: #fff;
}

.table-auto {
  width: 100%;
  border-collapse: collapse;
  border: none;
}

th, td {

  text-align: center; /* Center the content horizontally */
  vertical-align: middle; /* Center the content vertically */
}

th {
  background-color: white; /* Indigo for header background */
  font-weight: 600;
  color: black; /* White text for header */
  border: none;
  border-bottom: black solid 1px;
}

td {
  color: #444;
  padding: 12px;
  border: none;
}

input,
select {
  width: 200px;
}

button {
  transition: background-color 0.3s ease;
}

/* Custom colors to match Money-planner theme */
.text-green-500 {
  color: rgba(34, 197, 94, 1);
}

.text-red-500 {
  color: rgba(239, 68, 68, 1);
}

/* Ensure table container is only as large as needed */
.overflow-x-auto {
  overflow-x: auto;
  overflow-y: visible;
}

.max-h-max {
  max-height: none; /* No fixed height */
  height: auto; /* Adjust height based on content */
}

.bg-gray-100 {
  background-color: #f7fafc; /* Light gray background for even rows */
}

.bg-gray-300 {
  background-color: #e2e8f0; /* Darker gray background for odd rows */
}

.shadow-sm {
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05); /* Light shadow for select */
}

/* Fix focus issue on select element */
select:focus {
  outline: none; /* Remove default focus outline */
  border-color: #d1d5db; /* Set a fixed light gray border color */
}
</style>
