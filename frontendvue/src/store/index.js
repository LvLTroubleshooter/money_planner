import { createStore } from "vuex";

export default createStore({
  state: {
    categories: [],
    expenses: [],
  },
  mutations: {
    SET_CATEGORIES(state, categories) {
      state.categories = categories;
    },
    SET_EXPENSES(state, expenses) {
      state.expenses = expenses;
    },
    UPDATE_CATEGORY(state, updatedCategory) {
      const index = state.categories.findIndex(
        (c) => c.categoryId === updatedCategory.categoryId
      );
      if (index !== -1) {
        state.categories[index] = updatedCategory;
      }
      // Update category in expenses
      state.expenses = state.expenses.map((expense) => {
        if (expense.category?.categoryId === updatedCategory.categoryId) {
          return {
            ...expense,
            category: updatedCategory,
          };
        }
        return expense;
      });
    },
  },
  actions: {
    async fetchCategories({ commit }) {
      const userId = localStorage.getItem("userId");
      const response = await axios.get(`/api/categories/user/${userId}`);
      commit("SET_CATEGORIES", response.data);
    },
    async fetchExpenses({ commit }) {
      const userId = localStorage.getItem("userId");
      const response = await axios.get(`/api/expenses/user/${userId}`);
      commit("SET_EXPENSES", response.data);
    },
    async updateCategory({ commit }, categoryData) {
      commit("UPDATE_CATEGORY", categoryData);
    },
  },
});
