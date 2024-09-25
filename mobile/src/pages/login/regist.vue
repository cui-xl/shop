<template>
	<view>
		<u-navbar
		  title="用户注册"
			:autoBack="true"></u-navbar>
		<u--form ref="wyform" :rules="rules" class="wy-form" :model="wyform" labelWidth="60px">
			<u-form-item label="姓名" prop="name">
				<u--input v-model="wyform.name" placeholder="请输入姓名"></u--input>
			</u-form-item>
			<u-form-item label="昵称" prop="nickyName">
				<u--input v-model="wyform.nickyName" placeholder="请输入昵称"></u--input>
			</u-form-item>
			<u-form-item label="身份证" prop="idCard">
				<u--input @blur="inputGender" v-model="wyform.idCard" placeholder="请输入身份证号"></u--input>
			</u-form-item>
			<u-form-item label="性别">
				<u-radio-group disabled v-model="wyform.gender">
					<u-radio label="男" :name="1"></u-radio>
					<u-radio style="margin-left: 5px;" label="女" :name="0"></u-radio>
				</u-radio-group>
			</u-form-item>
			<u-form-item label="手机号" prop="phone">
				<u--input v-model="wyform.phone" placeholder="请输入手机号"></u--input>
			</u-form-item>
			<u-form-item label="邮箱" prop="email">
				<u--input v-model="wyform.email" placeholder="请输入电子邮箱"></u--input>
			</u-form-item>
			<u-form-item label="密码" prop="password">
				<u--input type="password" v-model="wyform.password" placeholder="请输入登陆密码"></u--input>
			</u-form-item>
			<u-form-item>
				<u-button type="primary" text="保存" @click="save"></u-button>
			</u-form-item>
		</u--form>
	</view>
</template>

<script>
export default {
	data () {
		return {
			wyform: {
				name: '',
				nickyName: '',
				idCard: '',
				gender: null,
				phone: '',
				email: '',
				file: null,
				password: ''
			},
			rules: {
				name: [
					{ required: true, message: '请输入用户名', trigger: 'blur' },
					{ max: 20, message: '用户姓名最多20字符', trigger: 'blur' }
				],
				nickyName: [
					{ required: true, message: '请输入用户昵称', trigger: 'blur' },
					{ max: 20, message: '用户昵称最多20字符', trigger: 'blur' }
				],
				idCard: [
					{ required: true, message: '请输入身份证号', trigger: 'blur' },
					{ min: 18, max: 18, message: '身份证号只能18位', trigger: 'blur' }
				],
				phone: [
					{ required: true, message: '请输入手机号', trigger: 'blur' },
					{ pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message:'请输入正确的手机号', trigger: 'blur'}
				],
				email: [
					{ required: true, message: '请输入电子邮箱', trigger: 'blur' },
					{ type: 'email', message: '请输入正确的电子邮箱', trigger: 'blur' }
				],
				password: [
					{ required: true, message: '请输入登陆密码', trigger: 'blur' }
				]
			}
		}
	},
	methods: {
		save () {
			this.$refs.wyform.validate().then(response => {
				if (response) {
					this.$post('/umsCustomer/add',this.wyform,content=>{
						uni.navigateBack()
					})
				}
			}).catch(error => {
				
			})
		},
		inputGender () {
			this.$refs.wyform.validateField('idCard', errorsRes => {
				if (errorsRes.length === 0) {
					const n = parseInt(this.wyform.idCard.substring(16, 17))
					this.wyform.gender = n % 2
				}
			})
		}
	}
}
</script>

<style scoped lang="scss">
.wy-form {
	margin-top: 60px;
	padding: 0 20px;
}
</style>