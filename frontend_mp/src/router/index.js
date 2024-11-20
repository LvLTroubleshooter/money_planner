import { createRouter, createWebHistory } from 'vue-router';
import LandingPage from '../views/LandingPage.vue';
import LoginForm from '../views/LoginForm.vue';
import SignUpForm from '../views/SignUpForm.vue';

const routes = [
    { path: '/', name: 'LandingPage', component: LandingPage },
    { path: '/login', name: 'LoginForm', component: LoginForm },
    { path: '/signup', name: 'SignUpForm', component: SignUpForm },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
