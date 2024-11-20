<template>
  <div class="landing-page">
    <header class="landing-header">
      <h1>Welcome to Money Planner</h1>
      <p>Your personal finance management tool to track expenses, incomes, budgets, and goals efficiently.</p>
    </header>
    <div class="cta-buttons">
      <router-link to="/login" class="btn">Login</router-link>
      <router-link to="/signup" class="btn">Sign Up</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "LandingPage",
};
</script>

<style scoped>
.landing-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f9f9f9;
}

.landing-header {
  text-align: center;
  margin-bottom: 20px;
}

.landing-header h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
}

.landing-header p {
  font-size: 1.2rem;
  color: #555;
}

.cta-buttons {
  display: flex;
  gap: 15px;
}

.btn {
  padding: 10px 20px;
  font-size: 1rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  text-decoration: none;
  text-align: center;
  cursor: pointer;
}

.btn:hover {
  background-color: #45a049;
}
</style>
