// import axios from "axios";
import request from '@/util/request.js';

export function articleGetAllService(){
    return request.get('article/getAll');
}

export function articleSearchService(conditions){
    return request.get('article/search', { params : conditions } );
}