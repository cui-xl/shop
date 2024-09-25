<template>
	<div>
		<section class="good-header">
			<section class="left">
				<img width="452" height="452" :src="album[imgIndex]" style="border: 1px solid #999999;margin-bottom: 20px;"/>
				<div class="album-list">
					<i class="icon el-icon-arrow-left"></i>
					<section class="images">
						<img 
						v-for="(item,index) in album"
						:key="index"
						:src="item"
						@click="imgIndex=index"/>
					</section>
					<i class="icon el-icon-arrow-right"></i>
				</div>
			</section>
			<section class="right">
				<p class="good-name">{{good.name}}</p>
				<div class="good-price row">
					<span class="label" style="letter-spacing: 1px;">价格</span>
					<p class="price-text">
						<span class="price-icon" style="color: #ff0000; line-height: 40px;">￥</span>
						<span class="text" style="color: #ff0000; font-size: 30px;">{{price}}</span>
					</p>
				</div>
				<el-divider></el-divider>
				<section class="sku-list">
					<div class="row"
					v-for="(item,index) in skuList"
					:key="index">
					<section class="label" style="width: 120px;">选择{{item.name}}</section>
					<section class="values">
						<span class="value-item"
						:class="{selected:text===selected[item.name]}"
						 v-for="(text,i) in item.value.split('\n')"
						:key="i"
						@click="selected[item.name]=text">
						{{text}}</span>
					</section>
					</div>
				</section>
				<img  src="/static/img/wudi.jpg" style="width: 100%;height: 126px;"/>
			</section>
			
		</section>
		<el-divider></el-divider>
		<el-descriptions>
			<el-descriptions-item label="品牌">{{good.brandName}}</el-descriptions-item>
		</el-descriptions>
		<el-descriptions :column="4">
			<el-descriptions-item 
			 v-for="(item,index) in spuList"
				:key="index"
				:label="item.name">{{item.value}}</el-descriptions-item>
		</el-descriptions>
		<el-divider></el-divider>
		<section v-html="good.description" class="centered-images"></section>
	</div>
	
</template>

<script>
	import {url} from './api.js'
	export default{
		created(){
			this.getById(this.$route.query.id)
		},
		data(){
			return{
				album:[],
				imgIndex:0,
				good:{},
				skuList:[],
				selected:[],
				spuList:[]
				
			}
		},
		computed:{
			price(){
				for(const i in this.good.skuStockList){
					if(JSON.stringify(this.selected)===this.good.skuStockList[i].items){
						return this.good.skuStockList[i].price.toFixed(2)
					}
				}
			}
		},
		methods:{
			getById(id){
				this.$get(url.getById,{id},content=>{
					console.dir(content)
					this.good=content
					this.album=JSON.parse(content.album)
					this.skuList=JSON.parse(this.good.skuAttr)
					this.selected=JSON.parse(content.skuStockList[0].items)
					this.spuList=JSON.parse(content.spuAttr)
					
				})
			}
		}
	}
</script>

<style scoped lang="less">
	.centered-images {
	  text-align: center;
	}
	
	.centered-images img {
	  display: inline-block;
	  margin: 0 auto;
	}
	.selected{
		border-color:#cc1049 !important;
	}
	.sku-list{
		margin-top: 70px;
	}
	.values{
		font-size: 14px;
		margin-left: 15px;
		display: flex;
		
		.value-item{
			height: 40px;
			margin-right: 10px;
			border: 1px solid 	#ccc;
			padding: 0 16px;
			cursor: pointer;
		}
		.value-item:hover{
			border-color: #e3393c;
		}
	}
	.row{
		display: flex;
		height: 40px;
		line-height: 40px;
		margin-bottom: 7px;
		.label{
			font-size: 12px;
			color: #999;
			padding-left: 10px;
			width: 60px;
			margin-bottom: 10px;
			margin-right: 20px;
		}
	}
	.good-header{
		height: 100%;
		display:flex;
		 width: 1210px;
		 height: 600px;
		 margin-top: 10px;
		 justify-content: space-between;
		 .left{
			 height: 100%;
			 width: 452px;
			 .album-list{
				 height: 58px;
				 margin-bottom: 18px;
				 display: flex;
				 .icon{
					 width: 36px;
					 line-height: 58px;
					text-align: center;
					font-size: 32px;
					cursor: pointer;
					color: #a7a7a7;
				 }
				 .images{
					 flex: 1;
					 display: flex;
					 >img{
						 width: 50px;
						 height: 58px;
						 border: 2px solid transparent;
						 margin:0 9px;
					 }
					 >img:hover{
						 border-color:#e53e41 ;
					 }
				 }
			 }
			 
		 }
		 .right{
			 height: 100%;
			 width:738px;
			 >.good-name{
				 margin-top: 10px;
				 color: #666666;
				 font-weight: bold;
				 font-size: 32px;
			 }
		 }
	}
</style>