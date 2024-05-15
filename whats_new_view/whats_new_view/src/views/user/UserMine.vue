<script setup>
    import { ref } from 'vue'
    import useUserInfoStore from '@/stores/userInfo.js';
    const userInfoStore = useUserInfoStore();
    const userInfo = ref({...userInfoStore.info})
    console.log(userInfo.value)
    const imgUrl= ref(userInfoStore.info.userPic);
    //读取token信息
    import {useTokenStore} from '@/stores/token.js'
    const tokenStore = useTokenStore()

    //图片上传成功的回调
    const uploadSuccess = (result)=>{
        //回显图片
        imgUrl.value = result.data
        //更新pinia中的数据
        userInfoStore.info.userPic=imgUrl.value
    }

    //调用接口，更新头像url
    import {userAvatarUpdateService} from '@/api/user.js'
    import {ElMessage} from 'element-plus'
    const updateAvatar = async ()=>{
        let result = await userAvatarUpdateService(imgUrl.value)
        ElMessage.success(result.message? result.message:'修改成功')
        //更新pinia中的数据
        userInfoStore.info.userPic=imgUrl.value
    
    }

    const rules = {
    nickname: [
        { required: true, message: '请输入用户昵称', trigger: 'blur' },
        {
            pattern: /^\S{2,10}$/,
            message: '昵称必须是2-10位的非空字符串',
            trigger: 'blur'
        }
    ],
    email: [
        { required: true, message: '请输入用户邮箱', trigger: 'blur' },
        { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
    ]
}

//修改用户信息
import {userInfoUpdateService} from '@/api/user.js'
const updateUserInfo = async ()=>{
    let result = await userInfoUpdateService(userInfo.value)
    ElMessage.success(result.message? result.message:'修改成功')
    //更新pinia中的数据
    userInfoStore.info.nickname=userInfo.value.nickname
    userInfoStore.info.email = userInfo.value.email
}
</script>

<template>
    <el-row>
            <el-col :span="12">
                <el-upload 
                    ref="uploadRef"
                    class="avatar-uploader" 
                    :show-file-list="false"
                    :auto-upload="true"
                    action="/api/pics/uploadAvatar"
                    name="file"
                    :headers="{'Authorization':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                    <img v-if="imgUrl" :src="imgUrl" class="avatar" />
                    <img v-else :src="avatar" width="278" />
                </el-upload>
                <br />
                <!-- <el-button type="primary" :icon="Plus" size="large"  @click="uploadRef.$el.querySelector('input').click()">
                    选择图片
                </el-button>
                <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
                    上传头像
                </el-button> -->
            </el-col>
        </el-row>
        <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
                上传头像
        </el-button>

        <el-row class="baseInfo">
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="登录名称">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="用户邮箱" prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
</template>

<style lang="scss" scoped>
    .avatar {
                width: 250px;
                height: 250px;
                display: block;
                
            }
    .baseInfo {
        margin-top: 30px;
    }
</style>