<template>
	<div>
		<el-form ref="cxlform" :rules="rules" :model="cxlform" label-width="100px" inline>
			<el-form-item label="商品分类" prop="categoryUids">
				<el-cascader v-model="cxlform.categoryUids"
				:options="categories"
				:props="props"
				placeholder="请选择商品分类"
				@change="changeCategory"></el-cascader>
			</el-form-item>
			<el-button plain @click="add" v-if="cxlform.categoryUids.length>0">添加属性</el-button>
			<el-divider content-position="left" v-if="cxlform.spuAttr.length>0">SPU属性</el-divider>
			<el-form-item
			v-for="(item,index) in cxlform.spuAttr" 
			:key="index" 
			:label="item.name" 
			:prop="'spuAttr['+index+'].value'"
			:rules="[
				{required:true,message:'请输入'+item.name,trigger:'blur'}
			]">
				<el-input v-model="item.value" :placeholder="'请输入'+item.name+'名称'">
					<el-popconfirm
					  :title="'确定删除【'+item.name +'】吗？'"
					  slot="append"
					  @confirm="delAttr(cxlform.spuAttr,index)"
					>
					  <i style="cursor: pointer;" class="el-icon-delete" slot="reference"></i>
					</el-popconfirm>
					
				</el-input>
			</el-form-item>
			<el-divider content-position="left" v-if="cxlform.skuAttr.length>0">SKU属性</el-divider>
			<el-form-item
			v-for="(item,index) in cxlform.skuAttr"
			:key="index+1000"
			:label="item.name"
			:prop="'skuAttr['+index+'].value'"
			:rules="[
			  	{required:true,message:'请输入'+item.name,trigger:'blur'}
			  ]">
				<el-input type="textarea" :rows="5" v-model="item.value" :placeholder="'请输入'+item.name+'名称'">
				</el-input>
				<el-popconfirm
				style="position: absolute; right: 5px;bottom: 5px"
				  :title="'确定删除【'+item.name +'】吗？'"
				  @confirm="delAttr(cxlform.skuAttr,index)"
				>
				  <i style="cursor: pointer;" class="el-icon-delete" slot="reference"></i>
				</el-popconfirm>
			</el-form-item>
		</el-form>
		<el-dialog title="添加属性" 
		:visible.sync="show"
		width="400px">
		<el-form :model="dialog" label-width="100px">
			<el-form-item label="类别">
				<el-radio-group v-model="dialog.type">
					<el-radio label="spuAttr">SPU</el-radio>
					<el-radio label="skuAttr">SKU</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="属性名">
				<el-input v-model="dialog.value" placeholder="请输入属性名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button plain @click="save">保存</el-button>
			</el-form-item>
		</el-form>
		</el-dialog>
	</div>
	
</template>

<script>
	import {url} from '../api.js'
	export default{
		props:['cxlform','categories'],
		data(){
			return{
				show:false,
				props:{
					label:'name',
					value:'uid',
					children:'children'
				},
				dialog:{
					type:null,
					value:''
				},
				rules:{
					categoryUids:[
						{required:true,message:'请选择分类',trigger:'blur'},
						]
				}
			}
		},
		methods:{
			changeCategory(value){  //查出分类对应的属性，便于给属性赋值
				this.$get(url.getAttr,{categoryUid:value},content=>{
					this.cxlform.spuAttr.length=0   //清理之前的spu sku
					this.cxlform.spuAttr.length=0
					console.dir(content)
					for(const i in content){
						const json={name:content[i].name,value:''}
						if(content[i].type===1){
							this.cxlform.spuAttr.push(json)
						}
						else{
							this.cxlform.skuAttr.push(json)
						}
					}
				})
			},
			delAttr(array,i){
				array.splice(i,1)
			},
			add(){
				this.show=true
			},
			save(){
				console.dir(this.dialog)
				this.cxlform[this.dialog.type].push({
					name:this.dialog.value,
					value:''
				})
				this.dialog.type=null
				this.dialog.value=''
				this.show=false
			}
		}
	}
</script>

<style>
</style>