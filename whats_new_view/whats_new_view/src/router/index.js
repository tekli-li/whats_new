//导入vue-router
import { createRouter, createWebHistory } from 'vue-router'
//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
import ArticleManageVue from '@/views/article/ArticleManage.vue'
import UserAvatarVUe from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import UserLayoutVue from '@/views/UserLayout.vue'
import UserCategoryVue from '@/views/article/UserCategory.vue'

//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: UserLayoutVue,
        children: [
            {path: '/article/category', component: UserCategoryVue},
        ]

    },
    {
        path: '/admin', component: LayoutVue,
        //重定向
        redirect: '/admin/article/manage',
        //子路由
        children: [
            { path: '/admin/article/category', component: ArticleCategoryVue },
            { path: '/admin/article/manage', component: ArticleManageVue },
            { path: '/admin/user/info', component: UserInfoVue },
            { path: '/admin/user/avatar', component: UserAvatarVUe },
            { path: '/admin/user/password', component: UserResetPasswordVue },
        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

export default router