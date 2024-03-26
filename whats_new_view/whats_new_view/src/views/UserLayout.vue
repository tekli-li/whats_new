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

import {useRouter} from 'vue-router'
import { ElMessageBox } from 'element-plus';
import {ElMessage} from 'element-plus';
const router = useRouter()

const clickCat = ()=>{
    router.push('/article/category');
}
</script>

<template>
    <div class="background-container">
        <el-container class="user-layout-container">
            <el-header class="user-header">
                <el-row type="flex" justify="center" align="middle">
                <el-menu class="el-menu" mode="horizontal" background-color="transparent" text-color="#000" active-text-color="blue">
                    <el-menu-item index="1">推荐</el-menu-item>
                    <el-menu-item index="2" @click="clickCat()">分类</el-menu-item>
                    <el-menu-item index="3">收藏</el-menu-item>
                    <el-menu-item index="4">历史</el-menu-item>
                    <el-menu-item index="5">我的</el-menu-item>
                </el-menu>
                <el-input placeholder="搜索..." style="width: 200px; background-color: transparent" ></el-input>
                <el-button type="primary" style="background-color: transparent; color: #000;" >搜索</el-button>
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
        background-color:azure;
        height: 100vh; /* 使背景图片充满整个视口高度 */
    }

    .user-header {
      margin-bottom: 20px;
  
    .el-menu {
        width: 500px;
        // max-width: min-content;
        margin-right: 20px;
    }
  
    .el-input {
        margin-right: 10px; // 调整搜索框与按钮之间的距离
      }
    }

    .el-main {
        margin-left: 17.5%;
        margin-right: 17.5%;
    }
  </style>