<script setup>
import { ref } from 'vue'

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    getArticles()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    getArticles()
}

const articleModel = ref({})

const articles = ref([]);

import { articleHistoryService } from '@/api/article.js'
const getArticles = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
    }
    let result = await articleHistoryService(params);
    console.log(articles.value)
    //渲染列表数据
    articles.value = result.data.items
    //渲染总条数
    total.value=result.data.total
}
getArticles();

import {useRouter} from 'vue-router'
import { articleViewService } from '@/api/article.js'
const router = useRouter();
const viewArticle = async (id) => {
    console.log(id);
    router.push({path:'/article/view', query: { id :id }})
}
getArticles();
</script>

<template>
    <div class="common-layout">
      <el-container>
        <el-main>
            <div>
                <el-row>
                    <el-col :span="8" v-for="article in articles" :key="article.id">
                        <el-card style="max-width: 95%" @click="viewArticle(article.id)">
                            <template #header>{{ article.title }}</template>
                            <img :src="article.coverImg" style="width: 100%" />
                            <div style="margin-top: 10px">views: {{ article.viewNum }}</div>
                        </el-card>
                    </el-col>
                 </el-row>
            </div>
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
        </el-main>
      </el-container>
    </div>
</template>



<style lang="scss" scoped>

</style>