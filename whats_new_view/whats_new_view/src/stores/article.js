//定义store
import { defineStore } from "pinia"
import { ref } from "vue";

/*
    第一个参数：名字，唯一
    第二个参数：函数，函数内部可以定义状态所有内容
*/
export const useArticleStore = defineStore('articleInfo',()=>{
    //1.定义描述articleModel
    const articleModel = ref({})

    //2.定义修改token的方法
    const setArticleModel = (article)=>{
        articleModel.value = article
    }

    //3.定义移除token的方法
    const removeArticleModel = ()=>{
        articleModel.value={}
    }
    return {
        articleModel,setArticleModel,removeArticleModel
    }
}
,
//参数持久化
{
    persist:false
}
)