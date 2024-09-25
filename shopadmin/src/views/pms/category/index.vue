<template>
	<div >
		<el-input v-model="val" placeholder="请输入要查询的分类信息" class="cxl-input"></el-input>
		
		 <el-button type="primary"  plain class="cxl-button" @click="getTableDataselect()">查询</el-button>

		<el-button type="success"  plain @click="add('')">添加分类</el-button>
		<el-table :data="tabledata" column row-key="id" style="margin-top: 5px;">
			<el-table-column label="分类名称" show-overflow-tooltip prop="name"></el-table-column>
			<!-- <el-table-column label="上级" show-overflow-tooltip prop="parentUid"></el-table-column> -->
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="success" size="small" @click="add(scope.row.uid)">追加下级</el-button>
					<el-button type="primary" size="small" @click="update(scope.row.id)">修改</el-button>
					<el-popconfirm
					  :title="'删除无法恢复，是否将['+scope.row.name+']删除！！！'"
					  @confirm="del(scope.row.id,scope.row.uid)"
					>
					 <el-button type="danger" size="small" slot="reference">删除</el-button>
					</el-popconfirm>
				</template>
			</el-table-column>
			
			
			
		</el-table>
		<el-dialog :title="dialog.title"
			:visible.sync="dialog.show"
			:close-on-click-modal="false"
			
			width="600px"
			@update:dialog="handleCloseDialog">
			<component v-if="dialog.show" :parentUid="dialog.parentUid" :id="dialog.id" :show.sync="dialog.show" :is="dialog.component"
			@getTableData="getTableData"></component>
			</el-dialog>
	</div>
</template>
<script>
	import { request } from '@/plugins/axios.js'
	import { Message } from 'element-ui';
	import {copy} from 'copy-anything'
	import {url} from './api.js';
	import Edit from './edit.vue';
	export default{
		components:{
			Edit
		}, 
		data(){
			return{
				all:[],
				tabledata:[],
				val:'',
				dialog:{
					parentUid:'',
					title:'',
					id:'',
					show:false,
					component: ()=>import('./edit.vue')
				}
			}
		},
		methods:{
			del(id,uid){
				this.$post(url.del,{id,uid},content=>{
					this.getTableData()
					
				})
			},
			find(val, table) {
			    for (let i = 0; i < table.length; i++) {
			        if (table[i].children && table[i].children.length > 0) {
			            this.find(val, table[i].children);
			        }
			        if (!table[i].children || (table[i].name.indexOf(val) === -1 && (!Array.isArray(table[i].children) || table[i].children.length === 0))) {
			            table.splice(i, 1);
			            i--;
			        }
			    }
			},
			// find(val,table){
			// 				for(let i=0;i<table.length;i++){
			// 					 if(table[i].children.length>0){
			// 						 this.find(val,table[i].children)
			// 					 }
			// 					 if(table[i].name.indexOf(val)===-1 && table[i].children.length===0){
			// 						 table.splice(i,1)
			// 						 i--
			// 					 }
			// 				}
			// 			}
			
			update(id){
				this.dialog.show=true
				this.dialog.title='修改分类'
				this.dialog.id=id
				console.dir(id)
				this.getTableData()
			},
			handleCloseDialog(shouldclose){
				this.dialog.show=shouldclose
			},
			add(parentUid){
				this.dialog.parentUid=parentUid
				this.dialog.id=null
				this.dialog.show=true
				this.dialog.title='添加分类'
				this.getTableData()
			},
			getTableData(){
				request(url.list,'get',{},content=>{
					console.dir(content)
					this.tabledata=copy(content)
					this.all=copy(content)
				})
				
			},
			getTableDataselect(){
				this.tabledata=copy(this.all)
				if(this.val){
					this.find(this.val,this.tabledata)
				}
			}
		},
		created(){
			if(this.val){
				this.find(this.val,this.tabledata)
			}
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