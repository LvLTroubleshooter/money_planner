import {createRouter, createWebHistory} from 'vue-router';
import LandingPageView from '@/views/LandingPageView.vue'
import GetStartedView from "@/views/GetStartedView.vue";

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
        }

    ]
});

export default router;