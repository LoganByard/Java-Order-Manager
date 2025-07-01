import { createRouter, createWebHistory } from 'vue-router'

// Import views
import StartOrderView from '../views/StartOrderView.vue'
import OrderingView from '../views/OrderingView.vue'
import ProductSearchView from '../views/ProductSearchView.vue'
//import AddCustomerView from '../views/AddCustomerView.vue' // if created
import EmployeeDashboard from '../views/EmployeeDashboard.vue'
import ManagerDashboard from '../views/ManagerDashboard.vue'
import AdminDashboard from '../views/AdminDashboard.vue'

const routes = [
  { path: '/', redirect: '/dashboard/employee' }, // Default to StartOrderView

  // Core views
  { path: '/start-order', component: StartOrderView },
  { path: '/orders/current', component: OrderingView, name: 'OrderingPage' },
  { path: '/products/search', component: ProductSearchView },

  // Dashboards
  { path: '/dashboard/employee', component: EmployeeDashboard },
  { path: '/dashboard/manager', component: ManagerDashboard },
  { path: '/dashboard/admin', component: AdminDashboard },

  //{ path: '/customers/add', component: AddCustomerView },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
