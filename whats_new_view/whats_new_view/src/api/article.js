import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const articleCategoryListService = ()=>{
    // const tokenStore = useTokenStore();
    //pinia中定义的响应式数据不需要value
    return request.get('/category/getAllCategory')
}

//添加文章分类
export const articleCategoryAddService = (categoryModel) => {
    return request.post('/category/addCategory', categoryModel)
}

//修改分类
export const articleCategoryUpdateService = (categoryModel)=>{
    return request.put('/category/updateCat',categoryModel)
}

//删除分类
export const articleCategoryDeleteService = (id) => {
    return request.delete('/category/deleteCat?id='+id)
}

//文章列表查询
export const articleListService = (params) => {
    return request.get('/article/listArticle', { params: params })
}

//添加文章
export const articleAddService = (articleModel)=>{
    return request.post('/article/addArticle',articleModel)
}

//修改文章
export const articleUpdateService = (articleModel)=>{
    return request.put('/article/updateArticle',articleModel)
}

//修改文章
export const articleUpdateCoverImgService = (articleModel)=>{
    return request.put('/article/updateCoverImg',articleModel)
}

//推荐文章
export const articleRecommendService = (params)=>{
    return request.get('/article/recommend',{ params: params })
}

//浏览文章
export const articleViewService = (articleId)=>{
    return request.get('/article/view?articleId='+articleId)
}

//推荐文章
export const articleHistoryService = (params)=>{
    return request.get('/user/getViewRecord',{ params: params })
}

//推荐文章
export const articleFavoriteService = (params)=>{
    return request.get('/user/getFavorite',{ params: params })
}

//添加评论
export const commentAddService = (comment)=>{
    return request.post('/article/addArticleComment',comment)
}

//获取评论列表
export const commentListService = (articleId)=>{
    return request.get('/article/getCommentsByArticle?articleId='+articleId)
}

//获取最热
export const hotestListService = (loadParams)=>{
    return request.post('/article/hotest',loadParams)
}