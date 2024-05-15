<script setup>
    import { ref } from 'vue'
    const categorys = ref([])
    const loadParams = ref({
      limit:5,
      offset:0
    });
    const articles = ref([])
    import { articleCategoryListService } from '@/api/article.js'
    const articleCategoryList = async () => {
        let result = await articleCategoryListService();
        categorys.value = result.data;
    }
    articleCategoryList();
    import { hotestListService } from '@/api/article.js'
    const refreshHotest = async () => {
      let result = await hotestListService(loadParams.value);
      articles.value = result.data;
    }
    refreshHotest();

    import {useRouter} from 'vue-router'
    import { articleViewService } from '@/api/article.js'
    const router = useRouter();
    const viewArticle = async (id) => {
      console.log(id);
      router.push({path:'/article/view', query: { id :id }})
    }

    const load = () => {
      loadParams.value.offset += 5;
      refreshHotest();
    }
</script>

<template>
    <div>
        <el-carousel :interval="4000" type="card" height="300px">
            <el-carousel-item v-for="article in articles" :key="article">
              <el-card style="max-width: 600px" @click="viewArticle(article.id)">
                <template #header>{{article.title}}</template>
                <img
                  :src="article.coverImg" style="width: 100%"
                />
              </el-card>
            </el-carousel-item>
        </el-carousel>
    </div>

    <!-- <div class="infinityScroll">
      <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
        <li v-for="article in articles" :key="article" class="infinite-list-item">{{ article.title }}</li>
      </ul>
    </div> -->
</template>

<style lang="scss" scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>