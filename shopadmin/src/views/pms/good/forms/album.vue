<template>
	<el-form ref="cxlform" :model="cxlform" :rules="rules" label-width="100px">
		<el-form-item label="商品相册" prop="files">
			<el-upload 
			:auto-upload="false"
			action="#"
			list-type="picture-card"
			multiple
			:limit="10"
			:on-change="changeImg"
			:on-remove="changeImg">
			<i class="el-icon-picture"></i>
			</el-upload>
		</el-form-item>
	</el-form>
</template>

<script>
export default{
	props:['cxlform'],
	data(){
		return{
		rules:{
			files:[
				{required:true,message:'请上传商品相册',trigger:'blur'}
			]
		}	
		}
	},
	methods:{
		changeImg(file,fileList){
			this.cxlform.files.length=0
			for (const i in fileList){
				this.cxlform.files.push(fileList[i].raw)
			}
		},
	}
}
</script>

<style>
</style>