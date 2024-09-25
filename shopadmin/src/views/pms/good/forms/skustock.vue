<template>
	<div>
		<el-form ref="cxlform" :rules="rules" :model="cxlform">
			<el-form-item label="组合按扭">
				<el-button type="primary" plain @click="zuhe">组合SKU</el-button>
			</el-form-item>
			<el-table :data="cxlform.stock">
				<el-table-column v-for="(item,index) in cxlform.skuAttr"
				:key="index"
				:label="item.name">     
				<template slot-scope="scope">{{scope.row.items[item.name]}}</template>
				</el-table-column>
				<el-table-column label="售价" >
					<template slot-scope="scope">
						<el-form-item
						:prop="'stock['+ scope.$index+'].price'"
						:rules="[
							{required:true,message:'请输入售价',trigger:'blur'},
							{pattern:/^[0-9]+(\.[0-9]{1,2})?$/,message:'价格最多两位小数',trigger:'blur'}
						]">
							<el-input v-model="scope.row.price"></el-input>
						</el-form-item>
					</template>
				</el-table-column>
				<el-table-column label="库存数">
					<template slot-scope="scope">
						<el-form-item 
						:prop="'stock['+ scope.$index+'].stock'"
						:rules="[
							{required:true,message:'请输入库存',trigger:'blur'},
							{type:'integer',message:'库存数必须是整数',trigger:'blur'}
						]">
							<el-input v-model.number="scope.row.stock"></el-input>
						</el-form-item>
					</template>
				</el-table-column>
			</el-table>
		</el-form>
		
	</div>
</template>

<script>
	import {copy} from  'copy-anything'
	export default{
		props:['cxlform'],
		data(){
			return{
				rules:{
					
				}
			}
		},
		methods:{
			zuhe(){
				for (const i in this.cxlform.skuAttr){
					this.dika(this.cxlform.skuAttr[i])
				}
				const  array=[]
				for (const i in this.cxlform.stock){
					array.push({
						items:this.cxlform.stock[i],
						price:this.cxlform.price,
						stock:0  //库存数
					})
				}
				this.cxlform.stock=array
			},
			dika(item){
				const values=item.value.split('\n')
				if(this.cxlform.stock.length===0){
					for (const i in values){
						if(values[i].trim()!==''){
						const json={}
						json[item.name]=values[i]
						this.cxlform.stock.push(json)
						}
					}
				}else{
					const array=[]
					for(const i in values){  //此时有第一次添加的值了
						for (const j in this.cxlform.stock){
							if (values[i].trim()!==''){
							const json =copy(this.cxlform.stock[j])  //json是引用类型，所以得再copy一份
							json[item.name]=values[i]
							array.push(json)
							}
						}
					}
					this.cxlform.stock=array
				}
			}
		},
	}
</script>

<style>
</style>