<!-- OrderingView.vue -->
<template>
  <div class="ordering-view">
    <header>
      <h2>Ordering for {{ customer.firstName }} {{ customer.lastName }}</h2>
    </header>

    <div class="layout">
      <!-- Left Tabs -->
      <aside>
        <button @click="activeTab = 'details'">Customer Details</button>
        <button @click="activeTab = 'cart'">Order Cart ({{ cart.length }})</button>
      </aside>

      <!-- Main Content -->
      <section>
        <div v-if="activeTab === 'details'">
          <!-- TODO: Load from API -->
          <p><strong>Email:</strong> {{ customer.email }}</p>
          <p><strong>Phone:</strong> {{ customer.phoneNumber }}</p>
          <p><strong>Address:</strong> {{ customer.address }}</p>
        </div>

        <div v-else>
          <h3>Cart Items</h3>
          <ul>
            <li v-for="(item, index) in cart" :key="index">
              {{ item.name }} (x{{ item.quantity }}) - ${{ item.price }}
              <button @click="removeFromCart(index)">Remove</button>
            </li>
          </ul>
          <p><strong>Total:</strong> ${{ totalPrice }}</p>
        </div>
      </section>

      <!-- Product Search + Add -->
      <aside>
        <h4>Add Products</h4>
        <input v-model="productSearch" placeholder="Search product name" />
        <button @click="searchProducts">Search</button>
        <ul>
          <li v-for="product in products" :key="product.id">
            {{ product.name }} - ${{ product.price }}
            <button @click="addToCart(product)">Add</button>
          </li>
        </ul>
      </aside>
    </div>

    <footer>
      <button @click="cancelOrder">Cancel Order</button>
      <button @click="checkout">Checkout</button>
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// TODO: Load from backend based on customerId
const customer = ref({
  id: route.params.customerId,
  firstName: 'Logan',
  lastName: 'Byard',
  email: 'logan@example.com',
  phoneNumber: '555-1234',
  address: '123 Main St',
})

const activeTab = ref('details')
const cart = ref([])
const products = ref([])
const productSearch = ref('')

function searchProducts() {
  // TODO: Implement API call to search products by name
  products.value = [
    { id: 1, name: 'Widget A', price: 10 },
    { id: 2, name: 'Widget B', price: 15 },
  ]
}

function addToCart(product) {
  cart.value.push({ ...product, quantity: 1 })
}

function removeFromCart(index) {
  cart.value.splice(index, 1)
}

const totalPrice = computed(() => {
  return cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
})

function cancelOrder() {
  router.push('/')
}

function checkout() {
  // TODO: Finalize order API call
  router.push('/orders/finalize')
}
</script>

<style scoped>
.ordering-view {
  padding: 1rem;
}
.layout {
  display: flex;
  gap: 2rem;
}
aside {
  width: 20%;
}
section {
  flex-grow: 1;
}
footer {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
}
</style>
