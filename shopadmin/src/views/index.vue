<template>
  <el-container class="cxl-wrap">
    <el-header class="header">
      <span>欢迎来到线上超市后台管理系统</span>
    </el-header>
    <el-container>
      <el-aside class="aside" style="background-color: #4789bc; ">
        <el-menu
          class="menu"
          background-color="#4f95d1"
          active-text-color="#ffaa00"
          text-color="#fff"
          router
        >
          <el-submenu
            v-for="item in menu"
            router
            :key="item.id"
            :index="item.frontUrl"
          >
            <template slot="title">{{ item.name }}</template>
            <el-menu-item
              v-for="child in item.children"
              :key="child.id"
              :index="child.frontUrl"
            >
              {{ child.name }}
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container style="background-color: #f0f2f5;">
        <el-main><router-view></router-view></el-main>
        <el-footer style="background-color: #4789bc;letter-spacing: 3px; color: white; text-align: right;padding-top: 30px;font-size: 22px;">
          后台管理界面
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>


<script>
	import Menu from '@/views/sys/admin'
	export default{
		data(){
			return{
				menu:[]
			}
		},
		components:{
			Menu
		},
		created(){
			this.menu=this.$store.getters.GET_MENU
			console.dir(this.menu)
		},
	}
</script>

<style scoped lang="less">
	@cxlborder: 1px solid #cccccc;
	.cxl-wrap{
		height: 100vh;
		
	}
	.el-submenu{
		width:300px
	}
	.header{
		background-color: #4789bc;
		text-align: center;
		span{
			font-size: 40px;
			line-height: 60px;
			font-weight: 700px;
			letter-spacing: 25px;
			font-family: 'SimHei', sans-serif;
			color: white
		}
	}
</style>