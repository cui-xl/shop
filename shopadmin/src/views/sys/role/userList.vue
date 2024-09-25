<template>
<div>
	<p>当前角色：<strong>{{role.name}}</strong></p>
	<el-transfer filterable style="margin:30px 0" :titles="['未关联用户','已关联用户']" :data="users" :props="props" v-model="values"></el-transfer>
	<el-button type="primary" @click="save">保存</el-button>
</div>
</template>

<script>
	import {url} from './api'
	export default{
		created() {
		  this.getAllData(this.$route.query.roleId)
		},
		
		data(){
			return{
				values:[],
				users:[],
				role:{
					id: null,
					name: '',
					uid:'',
				},
				props:{
					key:'uid',
					label:'name',
				}
			}
			
		},
		methods:{
			save(){
				this.$post(url.saveRoleAdmin,{RoleUid:this.role.uid,adminUids:this.values},Content=>{
					this.$router.push('/role')
				})
				
			},
			getAdmin(){
				this.$get(url.getAdmin,{},content=>{
					console.dir(content)
					this.users=content
					
				})
			},
			getAdminUid(){
				this.$get(url.getAdminUid,{RoleUid:this.role.uid},content=>{
					console.dir(this.role.uid)
					this.values=content
				})
			},
			getById(id){
				this.$get(url.getById,{id},content=>{
					console.dir(content)
					this.role.id=content.id
					this.role.name=content.name
					this.role.uid=content.uid
				})
			},
			getAllData(roleId){
				this.$get(url.getAllData,{roleId},content=>{
					console.dir(content)
					this.role.name=content.umsRole.name
					this.role.id=content.umsRole.id
					this.role.uid=content.umsRole.uid
					this.users=content.admins
					this.values=content.adminUids
				})
			}
			
		}
	};

</script>

<style>
</style>