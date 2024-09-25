"use strict";

import Vue from 'vue'
import axios from 'axios'
import Qs from 'qs'
import store from '@/store'
// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
	baseURL: 'http://127.0.0.1:8091',
	paramsSerializer: function (params) {
		return Qs.stringify(params, {arrayFormat: 'repeat'})
	}
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
	if (store.getters.GET_TOKEN) {
		config.headers.common.token = store.getters.GET_TOKEN
	}
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
)

export const request = (url, method, data, callback, loading = true) => {
	const axiosArgs = {
		url,
		method
	}
	if (method === 'post') {
		const formData = new FormData ()
		for (const key in data) {
			if (data[key] instanceof Array) {
				for (const i in data[key]) {
					formData.append(key, data[key][i])
				}				
			} else {
				formData.append(key, data[key])
			}			
		}
		axiosArgs.data = formData
	} else {
		axiosArgs.params = data
	}
	if (loading) {
		uni.showLoading()
	}	
	_axios.request(axiosArgs).then(response => {
		if (response.data.code === 200) {
			if (response.data.message) {
				uni.showToast({
					title: response.data.message,
					icon: 'success',
					mask: true
				})
			}
			callback(response.data.content)
		} else {
			if (response.data.code === 401) {
				uni.navigateTo({
					url: '/pages/login/index'
				})
			} else {
				uni.showToast({
					title: response.data.message,
					icon: 'error',
					mask: true
				})
			}		
		}
	}).catch(error => {
		console.dir(error)
		uni.showToast({
			title: error.message,
			icon: 'error',
			mask: true
		})
	}).finally(() => {
		if (loading) {
			uni.hideLoading()
		}		
	})
}
export const get = (url, data, callback) => {
	request(url, 'get', data, callback, true)
}
export const check = (url, data, callback) => {
	request(url, 'get', data, callback, false)
}
export const post = (url, data, callback, form) => {
	if (form) {
		form.validate(valid => {
			if (valid) {
				request(url, 'post', data, callback)
			}
		})
	} else {
		request(url, 'post', data, callback)
	}
}
Vue.prototype.$get = get
Vue.prototype.$post = post
Vue.prototype.$check = check