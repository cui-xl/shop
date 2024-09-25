import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
import store from '@/store'
const routes = [
  {
    path: '/index',
    name: 'index',
    component: ()=>import("@/views"),
	children:[
		///role-resource
		
		{path: '/admin',
		 name:123,
		 component: ()=>import("@/views/sys/admin")
		 },
		 {path: '/role',
		  name:1232,
		  component: ()=>import("@/views/sys/role")
		  },
		 {
			 path:'/brand',
			 component:()=>import("@/views/pms/brand")
		 }, 
		 {path: '/role-user',
		  name:12132,
		  component: ()=>import("@/views/sys/role/userList")
		  },
		  {path: '/role-resource',
		   name:1232212,
		   component: ()=>import("@/views/sys/role/resourceList")
		   },
		  {path: '/resource',
		   name:121322,
		   component: ()=>import("@/views/sys/resource")
		   },
		   {path: '/category',
		    name:121322,
		    component: ()=>import("@/views/pms/category/index")
		    },
			{path: '/attr',
			 name:121322,
			 component: ()=>import("@/views/pms/attr/index")
			 },
			 {path: '/good',
			  name:121322,
			  component: ()=>import("@/views/pms/good/index")
			  },
			  {path: '/good/add',
			   name:121322,
			   component: ()=>import("@/views/pms/good/add")
			   },
			   {path: '/good/detail',
			    name:1213221,
			    component: ()=>import("@/views/pms/good/detail")
			    },
	]
  },
  {
	  path: '/login',
	  name: 'login',
	  component: ()=>import("@/views/login"),
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to,from,next)=>{
	if(to.fullPath==='/login'||store.getters.GET_TOKEN){
		next()
		return
	}
	next('/login')
})
export default router
