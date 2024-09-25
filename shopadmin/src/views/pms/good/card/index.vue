<template>
	<div class="product-card">
		<img :src="src" width="220" height="220" @click="goDetail"/>
		<p class="product-price">
			<span class="price-icon">￥</span>
			<span class="price-text">{{good.price.toFixed(2)}}</span>
		</p>
		<p class="product-name" @click="goDetail">
			{{good.name}}
		</p>
		<el-switch
		v-model="good.isHot"
		active-text="热卖品"
		inactive-text="非热卖"
		active-color="#13ce66"
		inactive-color="#ff4949"
		@change="changeStatus('isHot','热卖')">
		</el-switch>
		<el-switch
		v-model="good.isNew"
		active-text="新品"
		inactive-text="非新品"
		active-color="#13ce66"
		inactive-color="#ff4949"
		@change="changeStatus('isNew','新品')">
		</el-switch>
		<el-switch
		v-model="good.isPublish"
		active-text="上架"
		inactive-text="下架"
		active-color="#13ce66"
		inactive-color="#ff4949"
		@change="changeStatus('isPublish','上架')">
		</el-switch>
		<el-switch
		v-model="good.active"
		active-text="未删除"
		inactive-text="已删除"
		active-color="#13ce66"
		inactive-color="#ff4949"
		@change="changeStatus('active','未删除')">
		</el-switch>
	</div>
</template>

<script>
	import {MessageBox} from 'element-ui'
	import {url} from '../api.js'
	export default{
		props:['good'],
		created(){
			console.dir(this.good)
			console.dir(JSON.parse(this.good.album))
		},
		computed:{
			src(){
				return JSON.parse(this.good.album)[0]
			}
		},
			
		methods:{
			goDetail(){
				this.$router.push({
					path:'/good/detail',
					query:{
						id:this.good.id,
						
					}
				})
			},
			changeStatus(field,text){
				MessageBox.confirm('确定要更新【'+text+'】状态嘛?','提示',{
					type:'warning',
				}).then(()=>{
					this.$post(url.changeStatus,{id:this.good.id,field,value:this.good[field],text},content=>{
						this.$emit("getTableData")
					})
					
				}).catch(()=>{
					this.good[field]=!this.good[field]
				})
			}
		}
	}
</script>

<style scoped lang="less">
	.product-card{
		height: 474px;
		padding: 39px 10px 11px;
	} 
	.product-card:hover{
		box-shadow: 0 0 10px #cccccc;
		cursor: pointer;
	}
	.product-price{
		color: #E4393C;
		font-weight: 600;
		font-size: 20px;
		margin-top: 20px;
	}
	.el-switch{
		margin-top:10px;
		/deep/ .el-switch__label{
		width: 60px;
		
	}
	}
	
</style>