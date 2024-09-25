<template>
	<div >
		<el-input v-model="val" placeholder="请输入要角色的信息" class="cxl-input"></el-input>
		<el-select placeholder="请选择状态" v-model="active">
			<el-option label="全部" :value="null"></el-option> 
			<el-option label="启用" :value="1"></el-option> <!--导包 -->
			
			<el-option label="禁用" :value="0"></el-option> <!--导包 -->
			<!--导包 -->
		</el-select>
		
		 <el-button type="primary"  plain class="cxl-button" @click="getTableDataselect()">查询</el-button>

		<el-button type="success"  plain @click="add">添加角色</el-button>
		<el-table :data="tabledata.records" column style="margin-top: 5px;">
			<el-table-column label="角色名" show-overflow-tooltip prop="name">
				<!-- <template slot-scope="scope">
					<el-popover>
						<span slot="reference">{{scope.row.name}}</span>
						<img width="150"  :src="scope.row.icon"/>
					</el-popover>
				</template> -->
			</el-table-column>
			<el-table-column label="状态" show-overflow-tooltip prop="active">
				<template slot-scope="scope">
					
					<span v-if="scope.row.active">启用</span>
					<span v-else>禁用</span>
				</template>
			</el-table-column>
			<el-table-column label="操作"  width="500">
				<template slot-scope="scope" >
					
						<template v-if="scope.row.active">
							<el-button type="primary" size="small" @click="update(scope.row.id)">修改</el-button>
							<el-popconfirm
							  :title="'是否将'+scope.row.name+'设置为禁用状态'"
							  @confirm="handle(scope.row.id,0)"
							>
							 <el-button style="margin-left: 5px;" slot="reference" type="danger" size="small">禁用</el-button>
							</el-popconfirm>
							<el-button type="warning" size="small" style="margin-left: 5px;" @click="userList(scope.row.id)">分配用户</el-button>
							<el-button type="warning" size="small" style="margin-left: 5px;" @click="resourceList(scope.row.id)">分配资源</el-button>
							
						</template>
						
						<template v-else>
							<el-popconfirm
							  :title="'是否将'+scope.row.name+'设置为启用状态'"
							  @confirm="handle(scope.row.id,1)"
							>
							 <el-button slot="reference" type="danger" size="small">启用</el-button>
							</el-popconfirm>
						</template>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
		  background
		  layout="prev, pager, next"
		  :current-page.sync="query.pageNo"
		  :page-count="tabledata.pages"
		  @current-change="getTableData()">
		</el-pagination>
		<el-dialog :title="dialog.title"
			:visible.sync="dialog.show"
			:close-on-click-modal="false"
			
			width="600px"
			@update:dialog="handleCloseDialog">
			<component v-if="dialog.show" :id="dialog.id" :show.sync="dialog.show" :is="dialog.component"
			@getTableData="getTableData"></component>
			</el-dialog>
	</div>
</template>
<script>
	import { request } from '@/plugins/axios.js'
	import { Message } from 'element-ui';
	import {url} from './api.js';
	import Edit from './edit.vue';
	export default{
		components:{
			Edit
		}, 
		data(){
			return{
				tabledata:{},
				val:'',
				active:null,
				query:{
					
					pageNo:1,
					pageSize:6,
					searchVal:'',
					active:''
				},
				dialog:{
					title:'',
					id:'',
					show:false,
					component: ()=>import('./edit.vue')
				}
			}
		},
		methods:{
			resourceList(id){
				this.$router.push({
					path:'/role-resource',
					query:{
						roleId:id
					}
				})
			},
			userList(id){
				this.$router.push({
					path:'/role-user',
					query:{
						roleId:id
					}
				})		//json传参
			},
			handle(id,active){
				this.$post(url.del,{active,id},content=>{
					console.dir(content)
					console.dir(111)
				}
				
			)
			this.getTableData()
			},
			update(id){
				this.dialog.show=true
				this.dialog.title='修改信息'
				this.dialog.id=id
			},
			handleCloseDialog(shouldclose){
				this.dialog.show=shouldclose
			},
			add(){
				this.dialog.id=null
				this.dialog.show=true
				this.dialog.title='添加信息'
			},
			getTableData(){
				
				request(url.list,'get',this.query,content=>{
					this.tabledata=content
					console.dir(content)
				})
				
			},
			getTableDataselect(){
				this.query.searchVal=this.val
				this.query.active=this.active
				this.getTableData()
			}
		},
		created(){
			this.getTableData();
		}
	}
</script>

<style scoped lang="less">
	.cxl-input{
		width: 200px;
	}
	.cxl-button{
		margin-left: 5px;
		height: 40px;
	}
	.el-select{
		margin-right: 5px;
	}
</style>