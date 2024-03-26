<script lang="js" setup>
import { ref } from 'vue'

const currentPage1 = ref(5)
const currentPage2 = ref(5)
const currentPage3 = ref(5)
const currentPage4 = ref(4)
const pageSize2 = ref(100)
const pageSize3 = ref(100)
const pageSize4 = ref(100)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)

import {
    Delete,
    Edit,
} from '@element-plus/icons-vue'
const tableData = [
    {
        title: 'title1',
        categoty: '时事',
        time: '2024-1-1',
        state: '已发布',
    },
    {
        title: 'title1',
        categoty: '时事',
        time: '2024-1-1',
        state: '已发布',
    },
    {
        title: 'title1',
        categoty: '时事',
        time: '2024-1-1',
        state: '已发布',
    },
    {
        title: 'title1',
        categoty: '时事',
        time: '2024-1-1',
        state: '已发布',
    },
]
</script>
<template>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="title" label="文章标题" />
        <el-table-column prop="category" label="分类" />
        <el-table-column prop="time" label="发表时间" />
        <el-table-column prop="state" label="状态" />
        <el-table-column label="操作" width="180">
            <el-button type="primary" :icon="Edit" circle />
            <el-button type="danger" :icon="Delete" circle />
        </el-table-column>
    </el-table>
    <el-pagination class="el-p" v-model:current-page="currentPage4" v-model:page-size="pageSize4"
        :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
        layout="jumper, total, sizes, prev, pager, next" :total="400" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
</template>

<style scoped>
.el-p {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
}
</style>
  


  