<template>
	<div >
		<el-input v-model="val" placeholder="请输入要查询的信息" class="cxl-input"></el-input>
		<el-select placeholder="请选择品牌状态" v-model="active">
			<el-option label="全部" :value="null"></el-option> 
			<el-option label="启用" :value="1"></el-option> <!--导包 -->
			
			<el-option label="禁用" :value="0"></el-option> <!--导包 -->
			<!--导包 -->
		</el-select>
		
		 <el-button type="primary"  plain class="cxl-button" @click="getTableDataselect()">查询</el-button>

		<el-button type="success"  plain @click="add">添加商品</el-button>
		
		<section class="product-list">
			<Card
			v-for="item in tabledata.records"
			:key="item.id"
			:good="item"
			@getTableData="getTableData"></Card>
		</section>
		<el-pagination
		  background
		  layout="prev, pager, next"
		  :current-page.sync="query.pageNo"
		  :page-count="tabledata.pages"
		  @current-change="getTableData()">
		</el-pagination>
	</div>
</template>
<script>
	import Card from'./card/index.vue'
	import { request } from '@/plugins/axios.js'
	import { Message } from 'element-ui';
	import {url} from './api.js';
	import Edit from './add.vue';
	export default{
		components:{
			Edit,Card
		}, 
		data(){
			return{
				tabledata:{},
				val:'',
				active:null,
				query:{
					
					pageNo:1,
					pageSize:8,
					searchVal:'',
					active:''
				},
			}
		},
		methods:{
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
				this.dialog.title='修改品牌信息'
				this.dialog.id=id
				getTableData()
			},
			handleCloseDialog(shouldclose){
				this.dialog.show=shouldclose
			},
			add(){
				this.$router.push('/good/add')
			},
			getTableData(){
				
				request(url.list,'get',this.query,content=>{
					console.dir(content)
					this.tabledata=content
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
	.product-list{
		margin-top: 40px;
		display: grid;
		grid-template-columns: repeat(4,240px);
		grid-row-gap: 10px;
		grid-column-gap:45px;
	}

</style>