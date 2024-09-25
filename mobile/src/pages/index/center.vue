<template>
	<view>
		<section class="order-list">
			<CxlOrder
			  v-for="(item,index) in tableData"
			  :key="index"
			  :item="item"></CxlOrder>
		</section>
	</view>
</template>

<script>
	import CxlOrder from '@/pages/order'
	export default{
		data(){
			return{
				tableData:[]
			}
		},
		onLoad(){
			this.getTableData()
		},
		components:{
			CxlOrder
		},
		methods:{
			getTableData(){
				this.$get('/omsOrder/list',{},content=>{
					console.dir(content)
					this.tableData=content
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.order-list{
		min-height: 100vh;
		background-color: #e2e2e2;
		border-top: 1px solid #e2e2e2;
	}
</style>