<template>
  <div class="start-order">
    <h2>Start New Order</h2>

    <!-- Search Form -->
    <form @submit.prevent="searchCustomers">
      <div class="form-group">
        <label>First Name:</label>
        <input v-model="firstName" type="text" />
      </div>
      <div class="form-group">
        <label>Last Name:</label>
        <input v-model="lastName" type="text" />
      </div>
      <div class="form-group">
        <label>Email:</label>
        <input v-model="email" type="email" />
      </div>
      <div class="form-group">
        <label>Phone Number:</label>
        <input v-model="phoneNumber" type="text" />
      </div>
      <div class="form-group">
        <label>Customer ID:</label>
        <input v-model="customerId" type="text" />
      </div>
      <button type="submit">Search</button>
      <button type="button" @click="navigateToAddCustomer">Add Customer</button>
      <button type="button" @click="cancel">Cancel</button>
    </form>

    <!-- Customer Results -->
    <div class="results">
      <h3>Matching Customers:</h3>
      <ul>
        <li v-for="customer in customers" :key="customer.id" @click="selectCustomer(customer)">
          {{ customer.firstName }} {{ customer.lastName }} - {{ customer.email }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const firstName = ref('')
const lastName = ref('')
const email = ref('')
const phoneNumber = ref('')
const customerId = ref('')
const customers = ref([])

function searchCustomers() {
  // TODO: Replace with backend search API
  console.log('Searching with:', {
    firstName: firstName.value,
    lastName: lastName.value,
    email: email.value,
    phoneNumber: phoneNumber.value,
    customerId: customerId.value,
  })
  // Mock results
  customers.value = [{ id: 1, firstName: 'Jhon', lastName: 'Doe', email: 'john@example.com' }]
}

function navigateToAddCustomer() {
  router.push('/customers/add')
}

function selectCustomer(customer) {
  // TODO: Set selected customer
  router.push({ name: 'OrderingPage', params: { customerId: customer.id } })
}

function cancel() {
  router.push('/')
}
</script>

<style scoped>
.start-order {
  padding: 1rem;
}
.form-group {
  margin-bottom: 1rem;
}
.results ul {
  list-style: none;
  padding: 0;
}
.results li {
  padding: 0.5rem;
  border: 1px solid #ccc;
  margin-bottom: 0.5rem;
  cursor: pointer;
}
.results li:hover {
  background-color: #f0f0f0;
}
</style>
