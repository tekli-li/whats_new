//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';

import { ElMessage } from 'element-plus'
// import { useRouter } from 'vue-router';
import router from '@/router';
//导入token状态
import { useTokenStore } from '@/stores/token.js';
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})
// const router = useRouter();

//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //判断业务状态码
        if (result.data.code === 0) {
            return result.data;
        }
        //操作失败
        ElMessage.error(result.data.msg?result.data.msg:'操作失败');
        return Promise.reject(result.data);
    },
    err=>{
        if (err.response.status === 401) {
            ElMessage.error('请先登录')
            router.push('/login')
        } else {
            ElMessage.error('服务异常')
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)
//添加请求拦截器
instance.interceptors.request.use(
    (config)=>{
        //在发送请求之前做什么
        let tokenStore = useTokenStore()
        //如果token中有值，在携带
        if(tokenStore.token){
            config.headers.Authorization=tokenStore.token
        }
        return config;
    },
    (err)=>{
        //如果请求错误做什么
        Promise.reject(err)
    }
)

export default instance;