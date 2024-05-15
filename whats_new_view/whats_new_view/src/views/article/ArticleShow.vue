<script setup>
import { ref, onMounted } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const articleModel = ref({})
const newComment = ref({
  "articleId":"",
  "content":""
})
const comments = ref([])
import { useRoute } from 'vue-router';
import { useArticleStore } from '@/stores/article.js';
import { articleViewService } from '@/api/article.js'
import { commentAddService } from '@/api/article.js'
import { commentListService } from '@/api/article.js';
import { ElMessage } from 'element-plus';
const route = useRoute();
const articleStore = useArticleStore();
const id = route.query.id
console.log(id);
const viewArticle = async () => {
  let result = await articleViewService(id);
  articleModel.value = result.data;
  console.log(articleModel.value);
  let commentResult = await commentListService(articleModel.value.id);
  comments.value = commentResult.data;
  console.log(commentResult.data);
}

const postComment = async () => {
  newComment.value.articleId = articleModel.value.id;
  let result = await commentAddService(newComment.value);
  ElMessage.success(result.message? result.message:'评论成功');
  newComment.value.content = "";
  viewArticle();
}



onMounted(() => {
  // 获取路由参数并赋值给 articleModel
  viewArticle();
});
</script>

<template>
  <div class="imgContainer">
    <el-main v-if="articleModel" >
      <img v-if="articleModel.coverImg" :src="articleModel.coverImg" alt="文章封面图片" class="coverImg">
      <p v-else>封面图片未找到</p>
    </el-main>
  </div>

  <div class="mainContent">  
    <!-- 标题 -->
    <h1>{{ articleModel && articleModel.title }}</h1>
    
    <!-- 文章内容 -->
    <el-main v-if="articleModel">
      <div v-html="articleModel.content"></div>
    </el-main>
  </div>

  <div style="font-size: 20px">
    <!-- 由于SVG图标默认不携带任何属性 -->
    <!-- 你需要直接提供它们 -->
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" style="width: 1em; height: 1em; margin-right: 8px"><path fill="currentColor" d="M511.552 128c-35.584 0-64.384 28.8-64.384 64.448v516.48L274.048 570.88a94.272 94.272 0 0 0-112.896-3.456 44.416 44.416 0 0 0-8.96 62.208L332.8 870.4A64 64 0 0 0 384 896h512V575.232a64 64 0 0 0-45.632-61.312l-205.952-61.76A96 96 0 0 1 576 360.192V192.448C576 156.8 547.2 128 511.552 128M359.04 556.8l24.128 19.2V192.448a128.448 128.448 0 1 1 256.832 0v167.744a32 32 0 0 0 22.784 30.656l206.016 61.76A128 128 0 0 1 960 575.232V896a64 64 0 0 1-64 64H384a128 128 0 0 1-102.4-51.2L101.056 668.032A108.416 108.416 0 0 1 128 512.512a158.272 158.272 0 0 1 185.984 8.32z"></path></svg>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" style="width: 1em; height: 1em; margin-right: 8px"><path fill="currentColor" d="m512 747.84 228.16 119.936a6.4 6.4 0 0 0 9.28-6.72l-43.52-254.08 184.512-179.904a6.4 6.4 0 0 0-3.52-10.88l-255.104-37.12L517.76 147.904a6.4 6.4 0 0 0-11.52 0L392.192 379.072l-255.104 37.12a6.4 6.4 0 0 0-3.52 10.88L318.08 606.976l-43.584 254.08a6.4 6.4 0 0 0 9.28 6.72zM313.6 924.48a70.4 70.4 0 0 1-102.144-74.24l37.888-220.928L88.96 472.96A70.4 70.4 0 0 1 128 352.896l221.76-32.256 99.2-200.96a70.4 70.4 0 0 1 126.208 0l99.2 200.96 221.824 32.256a70.4 70.4 0 0 1 39.04 120.064L774.72 629.376l37.888 220.928a70.4 70.4 0 0 1-102.144 74.24L512 820.096l-198.4 104.32z"></path></svg>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" style="width: 1em; height: 1em; margin-right: 8px"><path fill="currentColor" d="m679.872 348.8-301.76 188.608a127.808 127.808 0 0 1 5.12 52.16l279.936 104.96a128 128 0 1 1-22.464 59.904l-279.872-104.96a128 128 0 1 1-16.64-166.272l301.696-188.608a128 128 0 1 1 33.92 54.272z"></path></svg>
  </div>

  
  <div class="commentOps">
    <h3>发布评论</h3>
    <div>
      <el-input
        v-model="newComment.content"
        maxlength="100"
        style="width: 240px" 
        autosize
        placeholder="尽情评论吧~"
        show-word-limit
        type="textarea"
      />
      <button @click="postComment">发表评论</button>
    </div> 
     
    <div>
      <ul>
        <li v-for="comment in comments" :key="comment.id">
          <p>  {{ comment.comment.username }} : {{ comment.comment.content }}</p>
          <small>发表于 {{ comment.comment.createTime }}</small>
          <el-divider border-style="dashed" />
        </li>
      </ul>
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .imgContainer {
  display: grid;
  place-items: center; /* 简写方式，将内容居中显示 */
  height: 300px; /* 设置容器的高度 */
}
  .coverImg {
  max-width: 500px; /* 设置图片最大宽度为 100 像素 */
  max-height: 500px; /* 设置图片最大高度为 100 像素 */
}
</style>