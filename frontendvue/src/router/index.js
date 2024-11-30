import {createRouter, createWebHistory} from 'vue-router';
import LandingPageView from '@/views/LandingPageView.vue'
import GetStartedView from "@/views/GetStartedView.vue";
import LoginPageView from "@/views/LoginPageView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Landing',
            component: LandingPageView
        },
        {
            path: '/get-started',
            name: 'GetStarted',
            component: GetStartedView
        },
        {
            path: '/login-page',
            name: 'Login',
            component: LoginPageView
        }

    ]
});

export default router;