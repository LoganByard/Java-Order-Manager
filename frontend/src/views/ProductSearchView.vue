<template>
  <div class="product-search">
    <h2>Search Products</h2>

    <!-- Search Mode Switcher -->
    <div class="search-mode">
      <button :class="{ active: searchMode === 'name' }" @click="searchMode = 'name'">
        Search by Name
      </button>
      <button :class="{ active: searchMode === 'id' }" @click="searchMode = 'id'">
        Search by ID
      </button>
    </div>

    <!-- Input Field -->
    <input
      v-model="query"
      :placeholder="searchMode === 'name' ? 'Enter product name...' : 'Enter product ID...'"
    />
    <button @click="searchProducts">Search</button>
    <button @click="cancel">Cancel</button>

    <!-- Results -->
    <div class="results">
      <h3>Search Results:</h3>
      <ul>
        <li v-for="product in products" :key="product.id" @click="selectProduct(product)">
          <strong>ID:</strong> {{ product.id }}<br />
          <strong>Name:</strong> {{ product.name }}<br />
          <strong>Description:</strong> {{ product.description }}<br />
          <strong>Price:</strong> ${{ product.price }}<br />
          <strong>Stock:</strong> {{ product.stockQuantity }}<br />
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const searchMode = ref('name') // default mode
const query = ref('')
const products = ref([])

function searchProducts() {
  // TODO: Replace with backend search call
  console.log(`Searching by ${searchMode.value}:`, query.value)

  // Mock response
  if (searchMode.value === 'name') {
    products.value = [
      {
        id: 1,
        name: 'Amp Cable',
        description: 'High-quality audio cable',
        price: 15,
        stockQuantity: 42,
      },
      {
        id: 2,
        name: 'Amp Head',
        description: 'Solid-state amplifier head',
        price: 250,
        stockQuantity: 5,
      },
    ]
  } else {
    products.value = [
      {
        id: query.value,
        name: 'Mock Item',
        description: 'Found by ID',
        price: 123,
        stockQuantity: 9,
      },
    ]
  }
}

function selectProduct(product) {
  // TODO: Make API call to add/increment product in cart
  // Redirect back to ordering page for the same customer
  console.log('Selected product:', product)
  router.push('/orders/current') // or use a named route
}

function cancel() {
  router.push('/orders/current')
}
</script>

<style scoped>
.product-search {
  padding: 1rem;
}
.search-mode {
  margin-bottom: 1rem;
  display: flex;
  gap: 1rem;
}
.search-mode button.active {
  background-color: #3498db;
  color: white;
}
input {
  margin: 0.5rem 0;
  padding: 0.5rem;
  width: 300px;
}
button {
  margin: 0.5rem 0.5rem 0.5rem 0;
  padding: 0.5rem 1rem;
  cursor: pointer;
}
.results ul {
  list-style: none;
  padding: 0;
}
.results li {
  margin-bottom: 1rem;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
}
.results li:hover {
  background-color: #f4f4f4;
}
</style>
