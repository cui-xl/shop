<template>
	<el-form ref="cxlform" :model="cxlform" label-width="80px" :rules="rules" >
		<el-form-item label="名字" prop="name">
			<el-input v-model="cxlform.name" ></el-input>
		</el-form-item>
		<el-form-item label="电话" prop="phone">
			<el-input v-model="cxlform.phone" ></el-input>
		</el-form-item>
		<el-form-item label="性别" prop="gender">
			<el-radio-group v-model="cxlform.gender">
				<el-radio label="男"></el-radio>
				<el-radio label="女"></el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="邮箱" prop="email">
			<el-input v-model="cxlform.email" ></el-input>
		</el-form-item>
		<el-form-item label="生日" prop="birthday">
			<el-date-picker v-model="cxlform.birthday" value-format="yyyy-MM-dd">
				
			</el-date-picker>
		</el-form-item>
		<el-form-item v-if="this.id===null" label="密码" prop="password">
			<el-input show-password v-model="cxlform.password"></el-input>
		</el-form-item>
		
		<el-form-item label="头像" prop="file">
			<div style="width: 150px; height: 150px;overflow: hidden;">
			<el-upload
			action
			:file-list="FileList"
			:auto-upload="false"
			list-type="picture-card"
			:limit="1"
			:on-change="changeImg"
			:on-remove="removeImg"><i class="el-icon-picture"></i></el-upload>
			</div>
		</el-form-item>
		<el-form-item>
			<el-button size="small" @click="save" plain type="primary" >保存</el-button>
		</el-form-item>
		</el-form>
</template>

<script>
	import {request} from '@/plugins/axios'
	import {Message} from 'element-ui'
	import {url} from './api.js'
	export default{
		props:['id'],
		data(){
			return{
				FileList:[],
			
			cxlform:{
				name:'',
				password:'',
				gender:'',
				birthday:'',
				file:null,
				active:'',
				phone:'',
				email:''	
			},
			rules:{
				name:[
					{required:true,message:'请输入姓名',trigger:'blur'},
					{max: 20,message:'admin最多20个字符',trigger:'blur'}
				
				],
				file:[
					{required:this.id===null,message:'请选择用户头像',trigger:'blur'}
				],
				password:[
					{required:true,message:'请输入密码',trigger:'blur'},
				],
				email:[
					{required:true,message:'请输入邮箱',trigger:'blur'},
					{type:'email',message:'请输入正确邮箱',trigger:'blur'}
				],
				phone:[
					{required:true,message:'请输入手机号',trigger:'blur'},
					{pattern:/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,message:'请输入正确的手机号'}
				],
				birthday:[
					{required:true,message:'请输入生日',trigger:'blur'},
				]
			},
			
			
				
			}
			
		},
		methods:{
			getById(id){
				this.$request(url.getById,'get',{id},content=>{
					console.dir(content)
					for(const key in content){
						if(Object.prototype.hasOwnProperty.call(content,key)){
							this.cxlform[key]=content[key]
							this.cxlform.id=content.id
							console.dir(this.cxlform.id)
						}
					}
					this.FileList.push({
						url:content.icon
					})
				})
			},
			removeImg(file){
				this.cxlform.file=null
			},
			changeImg(file){
				this.cxlform.file=file.raw
				console.dir(file)
			},
			save(){
				
				this.$refs.cxlform.validate(vaild=>{
					if(vaild){
					
						request(this.cxlform.id?url.update:url.add,'post',this.cxlform,content=>{
							console.dir(content)
							this.$emit("update:show",false)
							this.$emit("getTableData")
						})
						
						
					}else{
						Message.error("输入错误")
					}
					
					
					
					
				})
				
			}
		},
		created(){
			if(this.id){
				delete this.cxlform.password
				this.getById(this.id)
			}else{
				console.dir('没有id')
			}
		},
	}
</script>

<style>
</style>