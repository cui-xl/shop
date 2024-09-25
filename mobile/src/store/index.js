import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)

export default new Vuex.Store({
	plugins:[
		createPersistedState({
			storage:window.localStorage,
			reducer(val){
				return{
					token:val.token,
				}
			}
		})
	],
  state: {
	  token:'',
  },
  mutations: {
	  SET_TOKEN:(state,token)=>{
		  state.token=token
	  }
  },
  getters:{
	GET_TOKEN:  state=> state.token
  },
  actions: {
  },
  modules: {
  }
})
