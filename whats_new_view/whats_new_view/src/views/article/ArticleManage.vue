<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

const title = ref('')

//文章分类数据模型
const categorys = ref([
])

//用户搜索时选中的分类id
const categoryId=ref('');

//用户搜索时选中的发布状态
const state=ref('');

//文章列表数据模型
const articles = ref([
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数


//文章列表查询
import { articleCategoryListService } from '@/api/article.js'
const getArticleCategoryList = async () => {
    //获取所有分类
    let resultC = await articleCategoryListService();
    categorys.value = resultC.data
}


//文章列表查询
import { articleListService } from '@/api/article.js'
const getArticles = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value ? categoryId.value : null,
        state: state.value ? state.value : null
    }
    let result = await articleListService(params);
    //渲染列表数据
    articles.value = result.data.items
    //为列表中添加categoryName属性
    for(let i=0;i<articles.value.length;i++){
        let article = articles.value[i];
        for(let j=0;j<categorys.value.length;j++){
            if(article.categoryId===categorys.value[j].id){
                article.categoryName=categorys.value[j].categoryName
            }
        }
    }
    //渲染总条数
    total.value=result.data.total
}
getArticleCategoryList();
getArticles();
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

import {Plus} from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content:'',
    state:'',
    likes:'',
    favorites:'',
    viewNum:''
})
//上传封面
import { useTokenStore } from '@/stores/token';
import {articleUpdateCoverImgService} from '@/api/article.js'
const tokenStore = useTokenStore();
const uploadSuccess = async (result)=>{
    articleModel.value.coverImg = result.data;
    let result1 = await articleUpdateCoverImgService(articleModel.value);
    console.log(result.data);
}

//添加文章
import {articleAddService} from '@/api/article.js'
import { ElMessage } from 'element-plus';
const addArticle=async (state)=>{
    articleModel.value.state = state
    let result = await articleAddService(articleModel.value);
    ElMessage.success(result.message? result.message:'添加成功')
    //再次调用getArticles,获取文章
    getArticles()
    //隐藏抽屉
    visibleDrawer.value=false
}

//修改分类回显
const updateArticleEcho = (row) => {
    title.value = '修改文章'
    visibleDrawer.value = true
    //将row中的数据赋值给categoryModel
    articleModel.value.title=row.title
    articleModel.value.categoryId=row.categoryId
    articleModel.value.coverImg=row.coverImg
    articleModel.value.content=row.content
    articleModel.value.state=row.state
    //修改的时候必须传递分类的id，所以扩展一个id属性
    articleModel.value.id=row.id
}
import {articleUpdateService} from '@/api/article.js'
//修改文章
const updateArticle=async (state)=>{
    articleModel.value.state=state
    let result = await articleUpdateService(articleModel.value)
    ElMessage.success(result.message? result.message:'修改成功')
    //再次访问后台接口，查询所有分类
    getArticles()
    visibleDrawer.value=false
}

//清空模型数据
const clearArticleModel = ()=>{
    articleModel.value.title=''
    articleModel.value.categoryId=''
    articleModel.value.coverImg=''
    articleModel.value.content=''
    articleModel.value.state=''
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer=true; title='添加文章'; clearArticleModel()">添加文章</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="文章分类："  style="width:30%">
                <el-select placeholder="请选择" v-model="categoryId">
                    <el-option 
                        v-for="c in categorys" 
                        :key="c.id" 
                        :label="c.categoryName"
                        :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="发布状态：" style="width:30%">
                <el-select placeholder="请选择" v-model="state">
                    <el-option label="已发布" value="已发布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getArticles">搜索</el-button>
                <el-button @click="categoryId='';state=''">重置</el-button>
            </el-form-item> 
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="文章标题" width="400" prop="title"></el-table-column>
            <el-table-column label="分类" prop="categoryName"></el-table-column>
            <el-table-column label="发表时间" prop="createTime"> </el-table-column>
            <el-table-column label="状态" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="updateArticleEcho(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="articleModel" label-width="100px" >
                <el-form-item label="文章标题" >
                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="文章分类" style="width:30%">
                    <el-select placeholder="请选择" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章封面">

                    <!-- 
                        auto-upload:是否自动上传
                        action：设置服务器接口路径
                        name：设置字段名
                        headers：设置请求头
                        on-success：上传成功回调函数
                     -->
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/pics/uploadAvatar"
                    name="file"
                    :headers="{'Authorization':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="文章内容">
                    <div class="editor">
                        <quill-editor
                                    theme="snow"
                                    v-model:content="articleModel.content"
                                    contentType="html"
                                    >
                        </quill-editor>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="title==='添加文章'? addArticle('已发布'):updateArticle('已发布')">发布</el-button>
                    <el-button type="info" @click="title==='添加文章'? addArticle('草稿'):updateArticle('草稿')">草稿</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}   
</style>