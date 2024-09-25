<template>
	<view class="cart-list">
		<u-checkbox-group
		  v-model="skuIds"
		  placement="column">
		  
		<section
			class="cart-item"
			v-for="(item,index) in tableData"
			:key="item.id">
			<u-checkbox :name="index"></u-checkbox>
			<img width="100" height="100" :src="item.src">
			<section style="width: 170px;">
				<p class="good-name">
					<strong>{{item.productName}}</strong>
				</p>
				<p class="sku-name">
					{{getSkuName(item.skuName)}}
				</p>
				<section class="sku-price">
					<p>
						<span>￥</span>
						<strong style="font-size: 16px;">{{getPrice(item.price)[0]}}</strong>
						<span>.{{getPrice(item.price)[1]}}到手</span>
					</p>
					<div>
						<u-number-box :max="9" v-model="item.count">
							<view
								slot="minus"
								class="minus"
							>
								<u-icon
									name="minus"
									size="12"
								></u-icon>
							</view>
							<text
								slot="input"
								style="text-align: center;"
								class="input"
							>{{item.count}}</text>
							<view
								slot="plus"
								class="plus"
							>
								<u-icon
									name="plus"
									color="#FFFFFF"
									size="12"
								></u-icon>
							</view>
						</u-number-box>
					</div>
				</section>
				<section style="color: #ff0000;">总计：{{sum(item.price,item.count)}}</section>
			</section>
		</section>
		</u-checkbox-group>
		<view class="cart-bottom">
			<u-checkbox-group>
				<u-checkbox label="全选" labelSize="12" size="12" @change="changeCheck"></u-checkbox>
			</u-checkbox-group>
			<span style="margin-left: 10px;">已选{{skuIds.length}}种,</span>
			<span>合计{{sumPrice}}</span>
			<div style="width: 80px; height: 40px; position: absolute; right: 10px;">
				<u-button type="error" text="去结算"  shape="circle" @click="orderAdd"></u-button>
			</div>
			
		</view>
		<div v-html="content"></div>
	</view>
</template>

<script>
	export default{
		onLoad(){
			this.getTableData()
		},
		computed:{
			sumPrice(){
				let sum=0
				for (const i in this.skuIds){
					const index = parseInt(this.skuIds[i])
					sum+=this.tableData[index].price*this.tableData[index].count
				}
				return sum.toFixed(2)
			}
		},
		data(){
			return{
				tableData:[],
				skuIds:[],
				checkAll:false,
				content:''
			}
		},
		methods:{
			changeCheck(val){
				this.skuIds=[]
				if(val){
					for(const i in this.tableData){
						this.skuIds.push(parseInt(i))
				}	
				}
				this.sumPrice
			},
			sum(price,count){
				return (price*count).toFixed(2)
			},
			getPrice(price){
				const priceItem = price.toFixed(2)
				return priceItem.split('.')
			},
			getSkuName(sku){
				const json=JSON.parse(sku)
				const array=[]
				for (const key in json){
					array.push(json[key])
				}
				return array.join(',')
			},
			orderAdd(){
				if(this.skuIds.length===0){
					uni.showToast({
						title:'还未选择商品，无法下单',
						icon:'error'
					})
				}else{
					let array=[]
					for (const i in this.skuIds){
						const index = parseInt(this.skuIds[i])
						array.push(this.tableData[index])
					}
					this.$post('/omsOrder/add',{total:this.sumPrice,items:JSON.stringify(array)},content=>{
						this.content=content
						this.$nextTick(()=>{
							const dom=document.getElementsByName('punchout_form')[0]
							dom.submit()
						})
						
					})
				}
			},
			getTableData(){
				this.$get('/omsCart/list',{},content=>{
					console.dir(content)
					this.tableData=content
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.cart-item{
		display: flex;
		padding: 0 10px;
		margin: 10px;
		img{
			margin-right: 8px;
		}
		.sku-price{
			display: flex;
			color: #ff1048;
			justify-content: space-between;
		}
		.good-name{
			font-size: 16px;
			line-height: 16px;
			height: 16px;
			overflow: hidden;
		}
		.sku-name{
			margin: 5px;
			background-color: #ededed;
			padding: 3px 15px;
			border-radius: 5px;
		}
	}
		.minus {
		width: 22px;
		height: 22px;
		border-width: 1px;
		border-color: #E6E6E6;
		border-style: solid;
		border-top-left-radius: 100px;
		border-top-right-radius: 100px;
		border-bottom-left-radius: 100px;
		border-bottom-right-radius: 100px;
		@include flex;
		justify-content: center;
		align-items: center;
	}

	.input {
		padding: 0 10px;
	}

	.plus {
		width: 22px;
		height: 22px;
		background-color: #FF0000;
		border-radius: 50%;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		justify-content: center;
		align-items: center;
	}
	.minus {
		width: 22px;
		height: 22px;
		border-width: 1px;
		border-color: #E6E6E6;
		border-style: solid;
		border-top-left-radius: 100px;
		border-top-right-radius: 100px;
		border-bottom-left-radius: 100px;
		border-bottom-right-radius: 100px;
		@include flex;
		justify-content: center;
		align-items: center;
	}

	.input {
		padding: 0 10px;
	}

	.plus {
		width: 22px;
		height: 22px;
		background-color: #FF0000;
		border-radius: 50%;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		justify-content: center;
		align-items: center;
	}
	.cart-bottom{
		position: fixed;
		bottom: 50px;
		background-color: #e5e5e5;
		width: 100%;
		height:40px;
		line-height: 40px;
		display: flex;
	}
</style>