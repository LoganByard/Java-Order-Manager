<template>
  <div class="dashboard">
    <header class="header">
      <h1>Employee Dashboard</h1>
    </header>

    <nav class="nav-bar">
      <button @click="navigate('/orders')">Order View</button>
      <button @click="navigate('/customers')">Customer View</button>
      <button @click="navigate('/products')">Product Catalog</button>
      <button @click="navigate('/customers/add')">Add Customer</button>
    </nav>

    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
function navigate(path) {
  router.push(path)
}
</script>

<style scoped>
/* Shared styles across all dashboards */
.dashboard {
  display: flex;
  flex-direction: column;
  height: 100vh;
}
.header {
  background-color: #2c3e50;
  color: white;
  padding: 1rem;
}
.nav-bar {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background-color: #ecf0f1;
}
.main-content {
  flex-grow: 1;
  padding: 1rem;
  overflow-y: auto;
}
button {
  padding: 0.5rem 1rem;
  border: none;
  background-color: #3498db;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #2980b9;
}
</style>
