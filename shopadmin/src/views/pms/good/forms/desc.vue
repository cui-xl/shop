<template>
	<div>
	<el-form ref="cxlform" :rules="rules" :model="cxlform" label-width="100px">
		<el-form-item label="商品详情" prop="description">
			<VueEditor 
			v-model="cxlform.description"
			placeholder="请上传商品详情"
			useCustomImageHandler
			@image-added="handleImageAdded"></VueEditor>
		</el-form-item>
	</el-form>
	</div>
	
</template>

<script>
	import {VueEditor} from 'vue2-editor'
	import {url} from '../api.js'
export default{
	props:['cxlform'],
	components:{
		VueEditor
	},
	computed:{
		rules(){
			description:[
				{required:true,message:'请输入内容',trigger:'blur'}
			]
		}
	},
	methods:{
		handleImageAdded(file,Editor,cursorLocation,resetUploader){
			this.$post(url.upload,{file},content=>{
				Editor.insertEmbed(cursorLocation,'image',content)
				resetUploader()
			})
		}
	}
}
</script>

<style>
</style>