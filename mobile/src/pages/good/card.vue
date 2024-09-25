<template>
	<div @click="toDetail" style="width: 100%; height: 100%;" class="good-card">
		<img :src="src" style="width:100%; height: 140px;">
		<p class="good-name" v-html="good.name"></p>
		<p class="good-price">￥{{good.price}}</p>
		
	</div>
</template>

<script>
	export default{
		props:['good'],
		created(){
			
		},
		computed:{
			src(){
				return JSON.parse(this.good.album)[0]
			}
		},
		methods:{
			toDetail(){
				uni.navigateTo({
					url:'/pages/good/detail?id='+this.good.id
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.good-card{
		border-radius: 5px;
		overflow: hidden;
		background-color: #ffffff;
	}
	.good-name{
		line-height: 14px;
		height: 28px;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
	}
	.good-price{
		color: #ff0000;
		font-size: 14px;
	}
</style>