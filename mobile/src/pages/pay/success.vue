<template>
	<view>
    <img class="srr" src="../../../public/static/20200618162857587.png" />
		<div style="font-size: 20px;margin-left: 110px">支付成功</div>
	</view>
</template>

<script>
	export default{
		onLoad(props){
			console.dir(props)
			this.$post('/omsOrder/updateStatus',props,content=>{
				console.dir(content)
			})
		}
	}
</script>

<style>
.srr{
  margin: 75px;
}
</style>