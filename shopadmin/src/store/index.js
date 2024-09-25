import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)

export default new Vuex.Store({
	plugins:[
		createPersistedState({
			storage:window.sessionStorage,
			reducer(val){
				return{
					token:val.token,
					menu:val.menu
				}
			}
		})
	],
  state: {
	  loading:0,
	  token:'',
	  menu:[]
  },
  mutations: {
	  OPEN_LOADING:(state)=>{
		  state.loading++
	  },
	  CLOSE_LOADING:(state)=>{
	  		  state.loading--
	  },
	  SET_TOKEN:(state,token)=>{
		  state.token=token
	  },
	  SET_MENU:(state,menu)=>{
		  state.menu=menu
	  }
	  
  },
  getters:{
	GET_LOADING: state => state.loading ,
	GET_TOKEN:  state=> state.token,
	GET_MENU: state=>state.menu
  },
  actions: {
  },
  modules: {
  }
})
