//定义store
import { defineStore } from "pinia"
import { ref } from "vue";
/*
    第一个参数：名字，唯一
    第二个参数：函数，函数内部可以定义状态所有内容
*/
export const useTokenStore = defineStore('token',()=>{
    //1.定义描述token
    const token = ref('')

    //2.定义修改token的方法
    const setToken = (newToken)=>{
        token.value = newToken
    }
    //3.定义移除token的方法
    const removeToken = ()=>{
        token.value=''
    }
    return {
        token,setToken,removeToken
    }
},
//参数持久化
{persist:true}
)