<template>
	<view class="good-detail" v-if="good">
		<swiper class="good-swiper" autoplay :interval="2000">
			<swiper-item
			v-for="(item,index) in album"
			:key="index">
				<img :src="item" style="width: 100vw; height: 100%;">
			</swiper-item>
		</swiper>
		<div class="good-info">
			<p class="good-price">
				<span>热卖价</span>
				<span>￥</span>
				<span>{{selectSku.price}}</span>
			</p>
			<p class="good-name">
			<span v-if="good.isHot" class="is-hot">热卖</span>
			<span v-if="good.isNew" class="is-new">新品</span>
			<strong>{{good.name}}</strong>
			</p>
		</div>	
		<div class="good-sku good-container">
			<div class="row">
				<strong class="label">已选</strong>
				<span @click="showSku" style="font-size: 14px;">{{skuTest}}</span>
			</div>
		</div>
		<div class="good-description good-container">
			<section class="good-menu">
				<p :class="{ selected: selectDesc === 0 }" @click="selectDesc = 0">商品介绍</p>
				<p :class="{ selected: selectDesc === 1 }" @click="selectDesc = 1">规格参数</p>
				<!-- <p :class="{ selected: selectDesc === 2 }" @click="selectDesc = 2">售后保障</p> -->
			</section>
			<section class="desc" v-if="selectDesc === 0" v-html="good.description"></section>
			<section v-if="selectDesc === 1">
				<u--form>
					<u-form-item
					  v-for="(item, index) in spuAttr"
						:key="index"
						:label="item.name"
						labelWidth="80px">{{item.value}}</u-form-item>
				</u--form>
			</section>
			<!-- <section v-if="selectDesc === 2">
				null
			</section> -->
		</div>
		<u-popup :show="skuShow" closeable @close="skuShow = false">
			<view>
				<div class="sku-header">
					<img width="100" height="100" :src="album[0]">
					<div style="margin-left: 12px;">
						<p class="good-price" style="color: #f2270c; margin-bottom: 10px;">
							<span>到手价</span>
							<strong style="font-size: 1.25rem;">￥</strong>
							<strong style="font-size: 1.8rem;">{{priceArray[0]}}</strong>
							<strong style="font-size: 1.25rem;">.{{priceArray[1]}}</strong>
						</p>
						<p>
							<span style="color: #8c8c8c; margin-right: 10px;">已选</span>
							<span style="font-size: 1.2rem;">{{skuTest}}</span>
						</p>
					</div>
				</div>
				<div class="sku-list">
					<section 
					  v-for="(item, index) in skuAttr"
						:key="index">
						<p class="sku-name">
							<strong>{{item.name}}</strong>
						</p>
						<p class="sku-values">
							<span
							  v-for="(val, i) in item.value.split('\n')"
							  :key="i"
								:class="{ selected: val === skuItem[item.name] }"
								@click="skuItem[item.name] = val">{{val}}</span>
						</p>
					</section>
				</div>
				<div class="good-count">
					<strong style="line-height: 30px;">数量</strong>
					<u-number-box max="5" v-model="count"></u-number-box>
				</div>
				<u-tabbar :border="false" class="btns">
					<u-tabbar-item>
						<u-button slot="inactive-icon" type="error" shape="circle" text="加入购物车" @click="addCart"></u-button>
					</u-tabbar-item>
				</u-tabbar>
			</view>
		</u-popup>

	</view>
</template>

<script>
	export default{
		onLoad(props){
			console.dir(props)
			this.getById(props.id)
		},
		data(){
			return{
				good:null,
				selectDesc: 0,
				album:[],
				skuItem:{},
				count:1,
				skuShow: false,
				count: 1
			}
		},
		computed:{
			skuAttr () {
				return JSON.parse(this.good.skuAttr)
			},
			selectSku(){
				for(const i in this.good.skuStockList){
					if(JSON.stringify(this.skuItem)===this.good.skuStockList[i].items){
						return this.good.skuStockList[i]
					}
				}
				return {}
			},
			skuTest(){
				let text=''
				for (const key in this.skuItem){
					text=text+this.skuItem[key]+'，'
				}
				text+=this.count+'个'
				return text
			},
			spuAttr () {
				return JSON.parse(this.good.spuAttr)
			},
			priceArray () {
				return (this.selectSku.price * this.count).toFixed(2).split('.')
			},
		},
		methods:{
			showSku(){
				this.skuShow = true
			},
			
			getById(id){
				this.$get('/pmsGood/getById',{id},content=>{
					this.album=JSON.parse(content.album)
					this.skuItem=JSON.parse(content.skuStockList[0].items)
					this.good=content
					console.dir(this.good)
				})
			},
			addCart () {
				const form = {
					productId: this.good.id,
					productName: this.good.name,
					skuId: this.selectSku.id,
					skuName: this.selectSku.items,
					price: this.selectSku.price,
					count: this.count,
					src: this.album[0]
				}
				this.$post('/omsCart/add', form, content => {
					console.dir()
				})
			}
		}
	}
</script>

<style scoped lang="scss">
.good-detail {
	background: #f2f2f2;
}
.good-swiper {
	height: 320px;
}
.good-info {
	background-color: #ffffff;
	padding: 12px 18px;
	border-radius: 0 0 12px 12px;
	.good-price {
		color: #f2270c;
		height: 37px;
		margin-bottom: 12px;
	}
	.good-name {
		strong {
			font-size: 1.25rem;
		}
		span {
			padding: 0.2rem 0.6rem;	
			color: #ffffff;
			margin-right: 0.5rem;
		}
		.is-hot {
			background-color: #ff0000;
		}
		.is-new {
			background-color: #00aaff;
		}
	}
}
.good-container {
	background-color: #ffffff;
	margin-top: 12px;
	border-radius: 12px;
	padding: 0 18px;
}
.row {
	display: flex;
	padding: 18px 0;
	.label {
		width: 35px;
	}
}
.sku-header {
	height: 118px;
	display: flex;
	align-items: flex-end;
	img {
		margin-left: 18px;
		border-radius: 6px;
	}
}
.sku-list {
	font-size: 1.1rem;
	margin: 18px;
	.sku-name {
		height: 40px;
		line-height: 40px;
	}
	.sku-values {
		display: flex;
		flex-wrap: wrap;
		span {
			padding: 7px 18px;
			background: #f2f2f2;
			color: #262626;
			border-radius: 15px;
			margin-right: 12px;
			margin-top: 10px;
		}
		.selected {
			background-color: #fcedeb;
			color: #f2270c;
			border: 1px solid #f2270c;
			font-weight: 600;
		}
	}
}
.good-count {
	display: flex;
	margin-left: 18px;
	justify-content: space-between;
	padding-right: 18px;
}
.btns {
	::v-deep .u-tabbar-item__icon {
		width: 100%;
	}
	.u-button {
		width: 90%;
	}
}
.good-menu {
	display: flex;
	height: 61px;
	line-height: 61px;
	>p {
		flex: 1;
		text-align: center;
		font-size: 1.1rem;
		position: relative;
	}
	>.selected {
		font-weight: bold;
	}
	>.selected::after {
		content: "";
		display: block;
		width: 26px;
		height: 3px;
		background: linear-gradient(90deg, #f5503a, #fad1cb);
		position: absolute;
		bottom: 15px;
		left: 50%;
		transform: translateX(-50%);
	}
}
.desc {
	::v-deep img {
		width: 100%;
	}
}
</style>