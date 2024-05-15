<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus';
import { ElMessage } from 'element-plus';
const router = useRouter()

import { userInfoService } from '@/api/user.js';
import useUserInfoStore from '@/stores/userInfo.js'
import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const getUserInfo = async () => {
    let result = await userInfoService();
    //数据存储到pinia
    userInfoStore.setInfo(result.data);

}
getUserInfo();

const clickRecommend = () => {
    router.push('/article/recommend');
}

const clickCat = () => {
    router.push('/article/category');
}

const clickHistory = () => {
    router.push('/user/history');
}

const clickMine = () => {
    router.push('/user/mine');
}

const clickFav = () => {
    router.push('/article/favorite');
}
</script>

<template>
    <div class="background-container">
        <el-container class="user-layout-container">
            <el-header class="user-header">
                <el-row type="flex" justify="center" align="middle">
                    <el-menu class="el-menu" mode="horizontal" background-color="transparent" text-color="#000"
                        active-text-color="blue">
                        <el-menu-item index="1" @click="clickRecommend()">推荐</el-menu-item>
                        <el-menu-item index="2" @click="clickCat()">最热</el-menu-item>
                        <el-menu-item index="3" @click="clickFav()">收藏</el-menu-item>
                        <el-menu-item index="4" @click="clickHistory()">历史</el-menu-item>
                        <el-menu-item index="5" @click="clickMine()">我的</el-menu-item>
                    </el-menu>
                    <el-input placeholder="搜索..." style="width: 200px; background-color: transparent"></el-input>
                    <el-button type="primary" style="background-color: transparent; color: #000;">搜索</el-button>

                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                </el-row>
            </el-header>

            <el-main class="el-main">
                <router-view>
                </router-view>
            </el-main>
        </el-container>
    </div>

</template>

<style lang="scss" scoped>
.background-container {
    // background-image: url('@/assets/background.jpg');
    // background-size: cover;
    // background-position: center;
    background-color: azure;
    height: 100vh;
    /* 使背景图片充满整个视口高度 */
}

.user-header {
    margin-bottom: 20px;

    .el-menu {
        width: 500px;
        // max-width: min-content;
        margin-right: 20px;
    }

    .el-input {
        margin-right: 2px; // 调整搜索框与按钮之间的距离
    }

    .el-dropdown__box {
            margin-left: 10px;
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
}

.el-main {
    margin-left: 15%;
    margin-right: 15%;
}
</style>