<template>
	<el-form ref="cxlform" :model="cxlform" label-width="80px" :rules="rules" >
		<el-form-item label="属性名称" prop="name">
			<el-input v-model="cxlform.name" ></el-input>
		</el-form-item>
		<el-form-item label="属性类别" prop="type">
			<el-radio-group v-model="cxlform.type">
				<el-radio :label="1">SPU</el-radio>
				<el-radio :label="0">SKU</el-radio>
				
			</el-radio-group>
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
		props:['id','categoryUid'],  //categoryuid接的是数组
		data(){
			return{
				FileList:[],
			
			cxlform:{
				name:'',
				type:null,
				categoryUid:''   
			},
			rules:{
				name:[
					{required:true,message:'请输入属性名称',trigger:'blur'},
					{max: 10,message:'属性名称最多10个字符',trigger:'blur'},
					// {validator:this.check,message:'分类名已存在',trigger:'blur'}
				],
				type:[
					{required:true,message:'请选择属性类别',trigger:'blur'}
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

				})
			},
			check(rule,value,callback){
				this.$check(url.check,{field:rule.field,value},content=>{
					if(content){
						callback()
					}else{
						callback(new Error())
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
			this.cxlform.categoryUid=this.categoryUid[this.categoryUid.length-1]
			console.dir(this.cxlform.categoryUid)
		},
	}
</script>

<style>
</style>