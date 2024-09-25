"use strict";

import Vue from 'vue';
import {Message} from 'element-ui';
import axios from "axios";
import store from '@/store';
import Qs from 'qs';
// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
	baseURL: 'http://localhost:8090',
	paramsSerializer:function(params){
		return Qs.stringify(params,{arrayFormat:'repeat'})
	}
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
	  if(store.getters.GET_TOKEN){
	  	config.headers.common.token=store.getters.GET_TOKEN
	  }
    // Do something before request is sent
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  function(response) {
    // Do something with response data
    return response;
  },
  function(error) {
    // Do something with response error
    return Promise.reject(error);
  }
);

Plugin.install = function(Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;
export const get = (url, data, callback) => {
  // 使用request函数发送GET请求
  request(url, 'get', data, callback,true);
}
export const post = (url, data, callback,form) => {
  // 使用request函数发送GET请求
  request(url, 'post', data, callback,true);
}
export const check = (url, data, callback) => {
  // 使用request函数发送GET请求
  request(url, 'get', data, callback,false);
}
export const request=(url,method,data,callback,loading=true)=>{
	if(loading){
		store.commit('OPEN_LOADING')	
	}
	const axiosParam={
		url,
		method,
	}
	if(method==='post'){
		const formData=new FormData()
		const name=['1','2','3','4']
		
		for (const key in data){
			if(data[key] instanceof Array){
				for(const i in data[key]){
					formData.append(key,data[key][i])
				}
			}else{
				formData.append(key,data[key])
			}
		}
		axiosParam.data=formData
	}else{
		axiosParam.params=data
	}
	
	_axios.request(axiosParam).then(response=>{
		if(response.data.code===200){
		if(response.data.message){
			Message.success(response.data.message)
		}
		callback(response.data.content)
		}else{
			Message.error(response.data.message)
		}
	}).catch((error)=>{
		Message.error(error)
	}).finally(()=>{
		if(loading){
			store.commit('CLOSE_LOADING')
		}
	})
}
Vue.prototype.$post=post
Vue.prototype.$get=get
Vue.prototype.$request=request
Vue.prototype.$check=check