<!-- <script>
  export default{
    data(){
      return{
        msg:'上海'
      }
    }
  }
</script> -->
<script setup>
  import { ref } from 'vue';
  const msg = ref('西安')
  //导入api.vue
  import ApiVue from './api.vue';
  //导入Article.vue
  import ArticleVue from './Article.vue';
</script>

<template>
  <!-- <h1>北京</h1> -->
  <h1>{{ msg }}</h1>
  <br>
  <!-- <ApiVue/> -->
  <ArticleVue/>
</template>

<style scoped>
  h1{
    color: red;
  }
</style> 
