<template>
 <router-view v-loading.fullscreen.lock="loading>0"></router-view>
</template>

<script>
import { Loading } from "element-ui"
export default {
data(){
	return{
		
	}
	
},
computed:{
			loading(){
				return this.$store.getters.GET_LOADING
			}
		},
		created(){
			// this.$router.push('/index')
		}
		}
</script>

<style>
	*{
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}
	html{
		 font-size: 16px;
		 min-width: 900px;
	}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;

  color: #2c3e50;
  margin-top: 60px;
}
</style>
