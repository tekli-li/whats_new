<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const registerData = ref({
    username:'',
    password:'',
    rePassword:'',
    role:''
})
const checkRePasssword = (rule, value, callback)=>{
    if(value===''){
        callback(new Error('请再次输入密码'))
    } else if(value !== registerData.value.password){
        callback(new Error('请确保两次输入的密码相同'))
    } else {
        callback()
    }
}

const rules = {
    username:[
        {required:true,message:'请输入正确用户名',trigger:'blur'},
        {min:5,max:16,message:'长度为5-16位非空字符',trigger:'blur'},
    ],
    password:[
        {required:true,message:'请输入密码',trigger:'blur'},
        {min:5,max:16,message:'长度为5-16位非空字符',trigger:'blur'},
    ],
    rePassword:[
        {validator:checkRePasssword,trigger:'blur'}
    ]
}
import {userRegisterService, userLoginService} from '@/api/user.js'

const register = async()=>{
    let result = await userRegisterService(registerData.value);
    // if (result.code === 0) {
    //     alert(result.msg? msg:'注册成功');
    // } else {
    //     alert(result.msg? msg:'注册失败');
    // }

    ElMessage.success(result.msg? msg:'注册成功')
}
import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();
const login = async()=>{
    let result = await userLoginService(registerData.value);
    // if (result.code === 0) {
    //     alert(result.msg? msg:'登录成功');
    // } else {
    //     alert(result.msg? msg:'登录失败');
    // }

    ElMessage.success(result.msg? msg:'登录成功');
    //把token存储到pinia
    tokenStore.setToken(result.data);
    if (registerData.value.role === 'ADMIN') {
        router.push('/admin');
    } else {
        router.push('/');
    }
    
}
const clearData = ()=>{
    registerData.value = {
        username:'',
        password:'',
        rePassword:'',
        role:''
    }
}
</script>

<template>
    <el-row class="login-page">
        <el-col  class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item class="register">
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false; clearData()" class="register" >
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
                <el-form-item class="login">
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true; clearData()" class="register">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;



    .form {
        background: linear-gradient(to right, #3498db, #ff6b81) no-repeat center / cover,
            url('../assets/logo2.png') no-repeat 60% center / 240px auto;
        height: 100%;
        width: 100%; /* 使.bg占据整个页面宽度 */
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;
        padding: 0 600px; /* 左右各添加 100px 的内边距 */

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
        .login {
            display: flex;
            justify-content: flex-end;
        }
        .register{
            color: #221827;
        }
    }
}
</style>