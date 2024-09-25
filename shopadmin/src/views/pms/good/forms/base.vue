<template>
	<el-form ref="cxlform" :model="cxlform" :rules="rules" label-width="100px">
		<el-form-item label="商品名称" prop="name">
			<el-input v-model="cxlform.name" style="width: 800px;"  placeholder="请输入商品名称"></el-input>
		</el-form-item>
		<el-form-item label="品牌名称"  prop="brandUid">
			<el-select v-model="cxlform.brandUid">
				<el-option
				v-for="item in brands"
				:key="item.id"
				:label="item.name"
				:value="item.uid">
				</el-option>
			</el-select>
		</el-form-item>
		<el-form-item label="展示价格" prop="price">
			<el-input v-model="cxlform.price" placeholder="输入展示价格" style="width: 300px;"></el-input>
		</el-form-item>
	</el-form>
</template>

<script>
	export default{
		props:['cxlform','brands'],
		data(){
			return{
				rules:{
					name:[
						{required:true,message:'请输入商品名称',trigger:'blur'},
						{max:255,message:'商品名称最多255个字符',trigger:'blur'},
					],
					brandUid:[
						{required:true,message:'请选择品牌',trigger:'blur'}
					],
					price:[
						{required:true,message:'请输入价格',trigger:'blur'},
						{pattern:/^[0-9]+(\.[0-9]{1,2})?$/,message:'展示价格最多两位小数',trigger:'blur'}
					]
				}
			}
		}
		
	}
</script>

<style>
</style>