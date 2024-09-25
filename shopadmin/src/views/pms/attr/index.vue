<template>
	<div >
		<el-cascader size="small" :options="categories" :props="props" v-model="values" @change="getTableData"></el-cascader>
		<el-button type="success"  plain @click="add" style="margin-left: 10px;" size="small">添加属性</el-button>
		<el-table :data="tabledata" column row-key="id" style="margin-top: 5px;">
			<el-table-column label="属性名称" width="200px" show-overflow-tooltip prop="name"></el-table-column>
			<el-table-column label="属性类别"  width="200px" show-overflow-tooltip prop="type">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.type===1" type="success">SPU</el-tag>
					<el-tag v-else>SKU</el-tag>
				</template>
			</el-table-column>
			
			<!-- <el-table-column label="上级" show-overflow-tooltip prop="parentUid"></el-table-column> -->
			<el-table-column  width="200px" label="操作">
				<template slot-scope="scope">
					<template v-if="values[values.length-1]===scope.row.categoryUid">
						<el-button type="primary" size="small" @click="update(scope.row.id)">修改</el-button>
						<el-popconfirm
						  :title="'删除无法恢复，是否将['+scope.row.name+']删除！！！'"
						  @confirm="del(scope.row.id)"
						>
						 <el-button type="danger" size="small" slot="reference">删除</el-button>
						</el-popconfirm>
					</template>
				</template>
			</el-table-column>	
		</el-table>
		<el-dialog :title="dialog.title"
			:visible.sync="dialog.show"
			:close-on-click-modal="false"
			width="600px"
			@update:dialog="handleCloseDialog">
			<component v-if="dialog.show"
			:parentUid="dialog.parentUid"
			:id="dialog.id" 
			:show.sync="dialog.show"
			:is="dialog.component"
			:categoryUid="values"
			@getTableData="getTableData"
			></component>
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
				values:[],
				all:[],
				categories:[],
				tabledata:[],
				val:'',
				props:{
					label:'name',
					value:'uid',
					checkStrictly:true
				},
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
			getCategories(){
				this.$get(url.getCategories,{},content=>{
					
					this.categories=content
				})
			},
			del(id){
				
				this.$post(url.del,{id},content=>{
					this.getTableData()
					
				})
			},
			update(id){
				this.dialog.show=true
				this.dialog.title='修改属性'
				this.dialog.id=id
				console.dir(id)
				this.getTableData()
			},
			handleCloseDialog(shouldclose){
				this.dialog.show=shouldclose
			},
			add(parentUid){
				if(this.values.length===0){
					Message.error('请先选择分类再添加属性')
				}else{
					this.dialog.parentUid=parentUid
					this.dialog.id=null
					this.dialog.show=true
					this.dialog.title='添加属性'
					this.getTableData()
				}
			},
			getTableData(){
				this.$get(url.list,{categoryUids:this.values},content=>{
					this.tabledata=content
					console.dir(content)
				})
			}
		},
		created(){
			if(this.val){
				this.find(this.val,this.tabledata)
			}
			this.getCategories()
			
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