import {createRouter, createWebHistory} from 'vue-router';
import LandingPageView from '@/views/LandingPageView.vue'
import GetStartedView from "@/views/GetStartedView.vue";
import LoginPageView from "@/views/LoginPageView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import DashboardView from "@/views/DashboardView.vue";
import GoalsView from "@/views/GoalsView.vue";

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
        },
        {
            path: '/user-dashboard/:userId',
            name: 'UserDashboard',
            component: DashboardView,
            props: true,
        },
        {
            path: '/:catchAll(.*)',
            name: 'NotFound',
            component: NotFoundView
        },
        {
            path: '/goals_page',
            name: 'GoalsPage',
            component: GoalsView
        }

    ]
});

export default router;