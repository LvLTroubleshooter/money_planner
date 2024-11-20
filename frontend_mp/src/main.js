import { createApp } from 'vue';
import './style.css';
import App from './App.vue';
import router from './router/index.js'; // Import the router

createApp(App).use(router).mount('#app');
