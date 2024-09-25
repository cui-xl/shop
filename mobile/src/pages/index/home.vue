<template>
<view>
	<view class="home-header">
		<u-search placeholder="请输入商品名" :showAction="false" v-model="name" @search="search"></u-search>
	</view>
	 <u-swiper
	            :list="banners"
				circular
	    ></u-swiper>
	<div class="good-list">
		<section v-for="(item,index) in tableData.records"
		:key="item.id">
			<GoodCard :good="item"></GoodCard>
		</section>
	</div>
</view>
</template>

<script>
	import GoodCard from '../good/card.vue'
	export default{
		onLoad(){
			this.getTableData()
		},
		components:{
			GoodCard
		},
		data(){
			return{
				name:'',
				banners:[
					'/static/banner/585f98efbc680802.jpg!cr_1053x420_4_0!q70.jpg',
					'/static/banner/q70 (1).jpg',
					'/static/banner/q70.jpg',
				],
				query:{
					searchVal:'',
					pageNo:1,
					pageSize:25
				},
				tableData:{}
			}
		},
		methods:{
			search(value){
				this.query.searchVal=value
				this.getTableData()
				this.query.pageNo=1
			},
			getTableData(){
				this.$get('/pmsGood/list',this.query,content=>{
					console.dir(content)
					this.tableData=content 
				})
			}
		}
	}
</script>

<style lang="scss" scoped> 
	.home-header{
		width: 100vw;
		position: fixed;
		top: 7vh;
		padding: 2vh 0;
		background-color: #e43130;
		.u-search{
			width: 70vw;
			margin: 0 auto !important;
		}
	}
	.u-swiper{
		margin-top: 50px;
	}
	.good-list{
		margin: 2vh 3vw;
		display: grid;
		grid-template-columns: repeat(2,43vw);
		grid-row-gap: 2vh;
		grid-column-gap: 5vh;
	}

</style>