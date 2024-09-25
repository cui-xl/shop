<template>
	<div class="cxl-order">
		<header class="order-header">
			<strong style="padding: 3px;">订单号:{{item.orderNo}}</strong>
		</header>
		<section class="order-status">
			<span v-if="item.status===1">已付款</span>
			<span v-else>未付款</span>
		</section>
		<OrderItem
		  v-for="orderItem in item.items"
		  :key="orderItem.id"
		  :orderItem="orderItem"></OrderItem>
		<div class="btn">
		<u-button hairline v-if="item.status===1" text="购买成功" plain type="success" size="mini"></u-button>
		<u-button hairline v-else text="去付款" plain type="error" size="mini" @click="pay"></u-button>			
		</div>
		<div v-html="content"></div>
	</div>
</template>

<script>
	import OrderItem from '@/pages/order/item.vue'
	export default{
		props:['item'],
		data(){
			return{
				content:''
			}
		},
		components:{
			OrderItem
		},
		methods:{
			pay(){
				this.$post('/omsOrder/pay',{orderNo:this.item.orderNo,total:this.item.total},content=>{
					this.content=content
					this.$nextTick(()=>{
						const dom=document.getElementsByName('punchout_form')[0]
						dom.submit()
					})
				})
			}
		}
	}
	
</script>

<style scoped lang="scss">
	.order-header{
	}
	.order-status{
		text-align: right;
	}
	.cxl-order{
		background-color: #ffffff;
		margin: 10px;
		border-radius: 5px;
		position: relative;
	}
	.btn{
		width: 50px;
		position: absolute;
		bottom: 2px;
		right: 4px;
	}
</style>