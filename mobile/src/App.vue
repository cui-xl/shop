<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import "uview-ui/index.scss";
*{
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}
html{
	font-size: 12px;
	font-family: '微软雅黑';
}
</style>
