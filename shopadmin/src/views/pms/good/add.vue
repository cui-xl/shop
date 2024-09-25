<template>
	<div>
		<el-steps :active="active" align-center finish-status="success">
			<el-step title="基本信息"></el-step>
			<el-step title="商品相册"></el-step>
			<el-step title="商品属性"></el-step>
			<el-step title="SKU库存"></el-step>
			<el-step title="商品详情"></el-step>
		</el-steps>
		<section class="good-forms">
			<div v-show="active===0">
				<Base ref="base" :cxlform="cxlform" :brands="brands"></Base>
			</div>
			<div v-show="active===1">
				<Album ref="album" :cxlform="cxlform" ></Album>
			</div>
			<div v-show="active===2">
				<Attr ref="attr" :cxlform="cxlform" :categories="categories"></Attr>
			</div>
			<div v-show="active===3">
				<SkuStock ref="skustock" :cxlform="cxlform"></SkuStock>
			</div>
			<div v-show="active===4">
				<DescForm ref="descform" :cxlform="cxlform"></DescForm>
			</div>
		</section>
			<div class="btns">
				<el-button type="primary" v-if="active!==0" @click="active--">上一步</el-button>
				<el-button type="primary" v-if="active<4" @click="next" >下一步</el-button>
				<el-button type="primary" v-else  @click="save">保存</el-button>
			</div>

		
	</div>

</template>

<script>
	import Base from './forms/base.vue'
	import Album from './forms/album.vue'
	import Attr from './forms/attr'
	import SkuStock from './forms/skustock.vue'
	import DescForm from './forms/desc.vue'
	import {url} from './api'
	import {copy} from 'copy-anything'
	
	export default{
		created(){
			this.getData()
		},
		data(){
			return{
				active:0,
				brands:[],
				categories:[],
				cxlform:{   //将添加合并到一个form中，用这个大form发添加请求，其他的子组件通过父子关系获取父组件cxlorm属性
					name:'',
					brandUid:'',
					price:'',
					files:[],
					categoryUids:[],
					spuAttr:[],
					skuAttr:[],
					stock:[],
					description:''
				}
			}
		},
		methods:{
			next(){
				const forms=[
					'base',
					'album',
					'attr',
					'skustock',
					'desc'
				]
				this.$refs[forms[this.active]].$refs.cxlform.validate(valid=>{
					if(valid){
						this.active++
					}
				})
			},
			getData(){
				this.$get(url.getData,{},content=>{
					console.dir(content)
					this.brands=content.brands
					this.categories=content.categories
					console.dir(content)
				})
			},
			save(){
				console.dir(this.cxlform)
				const form=copy(this.cxlform)
				form.spuAttr=JSON.stringify(this.cxlform.spuAttr)
				form.skuAttr=JSON.stringify(this.cxlform.skuAttr)
				form.stock=JSON.stringify(this.cxlform.stock)	
				this.$post(url.add,form,content=>{
					this.$router.push('/good')
				})
			}
		},
		components:{
			Base,Album,Attr,SkuStock,DescForm
		}
	}
</script>

<style>
</style>