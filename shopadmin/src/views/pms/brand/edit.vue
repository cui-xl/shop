<template>
	<el-form ref="cxlform" :model="cxlform" label-width="80px" :rules="rules" >
		<el-form-item label="名字" prop="name">
			<el-input v-model="cxlform.name" ></el-input>
		</el-form-item>
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
		<el-form-item label="描述" prop="description">
			<el-input v-model="cxlform.description" ></el-input>
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
				description:'',
				file:null,
				active:'',	
			},
			rules:{
				name:[
					{required:true,message:'请输入品牌名称名',trigger:'blur'},
					{max: 20,message:'名称最多20个字符',trigger:'blur'}
				
				],
				file:[
					{required:this.id===null,message:'请选择品牌Logo',trigger:'blur'}
				],
				description:[
					{required:true,message:'请输入品牌说明',trigger:'blur'},
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
						url:content.logo
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