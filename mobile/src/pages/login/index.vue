<template>
	<view>
		<u-navbar
		  title="登陆注册"
			autoback></u-navbar>
		<u--form style="margin: 60px 30px 0px;" :model="wyform">
			<u-form-item
			  prop="wyform.username"
				borderBottom>
			  <u--input border="none" v-model="wyform.username" placeholder="请输入邮箱/手机号"></u--input>
			</u-form-item>
			<u-form-item
			  prop="wyform.code"
				borderBottom>
			  <u--input border="none" v-model="wyform.code" placeholder="请输入验证码">
					<template slot="suffix">
						<u-code ref="wycode" @change="codeChange"></u-code>
						<u-button type="success" @tap="getCode">{{tips}}</u-button>
					</template>
				</u--input>
			</u-form-item>
			<u-form-item>
				<u-button type="error" shape="circle" @click="login" text="登录"></u-button>
			</u-form-item>
			<u-form-item>
				<section class="wy-go">
					<span>账号密码登录</span>
					<span @click="goRegist">手机快速注册</span>
				</section>		
			</u-form-item>
		</u--form>
	</view>
</template>

<script>
import store from '@/store'
export default {
	data () {
		return {
			tips: '获取验证码',
			wyform: {
				username: '',
				code: ''
			}
		}
	},
	methods: {
		login(){
			this.$post('/umsCustomer/login',this.wyform,content=>{
				store.commit('SET_TOKEN',content)
				uni.navigateBack()
			})
		},
		getCode () {
			if (this.wyform.username.trim() === '') {
				uni.showToast({
					title: '请输入邮箱/手机号',
					icon: 'error'
				})
			} else {
				this.$get('/umsCustomer/sendCode', this.wyform, content => {
					this.$refs.wycode.start()
				})
			}
		},
		codeChange (text) {
			this.tips = text
		},
		goRegist () {
			uni.navigateTo({
				url: '/pages/login/regist'
			})
		}
	}
}
</script>

<style scoped lang="scss">
.wy-go {
	width: 100%;
	display: flex;
	justify-content: space-between;
	>span {
		color: rgba(0, 0, 0, 0.4)
	}
}
</style>