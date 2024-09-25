<template>
	<el-form ref="cxlform" :model="cxlform" label-width="80px" :rules="rules" >
		<el-form-item label="资源名称" prop="name">
			<el-input v-model="cxlform.name" ></el-input>
		</el-form-item>
		
		<el-form-item label="类别" prop="type">
			<el-radio-group v-model="cxlform.type">
				<el-radio :label="1">目录</el-radio>
				<el-radio :label="0">按钮</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="前端地址" prop="frontUrl" :rules="{required:this.cxlform.type===1,message:'请输入前端地址',trigger:'blur'}">
			<el-input v-model="cxlform.frontUrl" ></el-input>
		</el-form-item>
		<el-form-item label="后端地址" prop="backUrl" :rules="{required:this.cxlform.type===0,message:'请输入后端地址',trigger:'blur'}">
			<el-input v-model="cxlform.backUrl" ></el-input>
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
		props:['id','parentUid'],
		data(){
			return{
				FileList:[],
			
			cxlform:{
				name:'',
				frontUrl:'',
				backUrl:'',
				type:'',
				parentUid:'',
			},
			rules:{
				name:[
					{required:true,message:'请输入姓名',trigger:'blur'},
					{max: 20,message:'admin最多20个字符',trigger:'blur'}
				
				],
				type:[
					{required:true,message:'请选择类型',trigger:'blur'}
				],
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

				})
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
			this.cxlform.parentUid=this.parentUid
		},
	}
</script>

<style>
</style>