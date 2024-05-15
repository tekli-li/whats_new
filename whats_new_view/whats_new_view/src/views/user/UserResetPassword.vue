<script setup>
    import { ref } from 'vue'
    import useUserInfoStore from '@/stores/userInfo.js';
    const userInfoStore = useUserInfoStore();
    const userInfo = ref({...userInfoStore.info})
    const pwdModel = ref({
        old_pwd:"",
        new_pwd:"",
        re_pwd:""
    })

    import { userPwdUpdateService } from '@/api/user.js';
    const updatePwd = async() => {
        let result = await userPwdUpdateService(pwdModel.value)
        ElMessage.success(result.message? result.message:'修改成功，即将重新登陆')

        //更新pinia中的数据
        userInfoStore.info.password=pwdModel.value.new_pwd

        setTimeout(() => {
        }, 2000);
    }
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="旧密码">
                        <el-input v-model="pwdModel.old_pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="new_pwd">
                        <el-input v-model="pwdModel.new_pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="再次输入" prop="re_pwd">
                        <el-input v-model="pwdModel.re_pwd"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatePwd">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>

</style>