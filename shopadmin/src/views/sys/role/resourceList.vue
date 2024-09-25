<template>
<div>
	<p>当前角色为:&nbsp;&nbsp;{{role.name}}</p>
	<el-tree
	ref="cxltree"
	style="margin: 20px 0;"
	:data="resources"
	:props="props"
	show-checkbox
	:default-checked-keys="resourceUids"
	node-key="uid"
	default-expand-all></el-tree>
	<el-button size="medium" type="primary" @click="save">保存</el-button>
	</div>
</template>


<script>
	export default{
		created(){
			console.dir(this.$route.query.roleId)
			this.getData(this.$route.query.roleId)
			 
		},
		data(){
			return{
				props:{
					label:'name',
					children:'children'
				},
				role:{
					
				},
				resourceUids:[],
				resources:[],
			}
		},
		methods:{
				
			save(){	
				const checkNodes=this.$refs.cxltree.getCheckedNodes(false,true)
				const front=[]
				const back=[]
				for(const i in checkNodes){
					if(checkNodes[i].type===1){
						front.push(checkNodes[i].uid)
					}else{
						back.push(checkNodes[i].uid)
					}
				}
				this.$post('/umsRoleResource/save',{roleUid:this.role.uid,front,back},content=>{
					this.$router.push('/role')
				})
				console.dir(checkNodes)
			},
			getData(roleId){
				this.$get('/umsRoleResource/getData',{roleId},content=>{
					console.dir(content)
					this.role=content.role
					this.resources=content.resources
					this.resourceUids=content.resourceUids
				})
				
				
			}
		},
		
	}
</script>

<style>
</style>