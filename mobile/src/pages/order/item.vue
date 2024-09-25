<template>
	<div class="order-item">
		<img :src="orderItem.src"/>
		<p class="product-name">{{orderItem.productName}}</p>
		
		<div class="item-price">
			<p style="color: #ff0000; position: absolute; right: 0;">
				<strong style="font-size: 12px;">￥</strong>
				<strong style="font-size: 16px;">{{totalArray[0]}}</strong>
				<strong style="font-size: 12px;">.{{totalArray[1]}}</strong>
			</p>
			
		</div>
	</div>
</template>

<script>
	export default{
		props:['orderItem'],
		computed:{
			total(){
				return this.orderItem.price*this.orderItem.count
			},
			totalArray(){
				return this.total.toFixed(2).split('.')
			}
		}
	}
</script>

<style scoped lang="scss">
	.order-item{
		position: relative;
		display: flex;
		align-items: center;
		>img{
			width: 80px;
			height: 80px;
			margin: 10px;
		}
		.product-name{
			height: 40px;
			line-height: 20px;
			overflow: hidden;
			
		}
	}
</style>