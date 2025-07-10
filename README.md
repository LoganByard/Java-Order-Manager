# Java Order Manager

Java Order Manager is a cross-platform desktop order management system built with a Spring Boot backend and a Vue 3 + Electron frontend. The application is designed to support role-based access (Admin, Manager, Employee), allowing users to manage customers, products, and orders efficiently through an intuitive GUI.

This is a **work in progress** designed for real-world relevance, applying scalable architectural patterns, robust testing practices, and clean code principles across both the frontend and backend.

---

## Project Structure

### Backend (Spring Boot)

```
backend/
├── src/main/java/com/Logan/Java/Order/Manager/
│   ├── controller/
│   │   ├── CustomerController.java
│   │   ├── EmployeeController.java
│   │   ├── OrderController.java
│   │   ├── OrderItemController.java
│   │   └── ProductController.java
│   ├── dto/
│   │   └── (DTO classes for each entity)
│   ├── mapper/
│   │   ├── CustomerMapper.java
│   │   ├── EmployeeMapper.java
│   │   ├── OrderItemMapper.java
│   │   ├── OrderMapper.java
│   │   └── ProductMapper.java
│   ├── model/
│   │   ├── Customer.java
│   │   ├── Employee.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Product.java
│   │   └── Role.java
│   ├── repository/
│   │   └── (Spring Data JPA Repos)
│   ├── security/
│   │   └── (Spring Security Configuration)
│   └── service/
│       ├── CustomerService.java
│       ├── EmployeeService.java
│       ├── OrderItemService.java
│       ├── OrderService.java
│       └── ProductService.java
└── JavaOrderManagerApplication.java
```

### Testing

```
src/test/java/com/Logan/Java/Order/Manager/
└── Service/
    ├── CustomerServiceUnitTest.java
    ├── EmployeeServiceUnitTest.java
    ├── OrderItemServiceUnitTest.java
    ├── OrderServiceUnitTest.java
    └── ProductServiceUnitTest.java
```

### Tools & Frameworks

- **Spring Boot** (REST API, validation, business logic)
- **Jakarta Persistence (JPA)**
- **MapStruct** (DTO-to-entity mapping)
- **H2 Database** (for testing)
- **JUnit 5** + **Mockito** (service layer unit testing)
- **Spring Security** (with planned JWT-based auth)

---

## Frontend (Vue 3 + Electron)

```
frontend/
├── electron/
│   ├── main.js
│   └── preload.js
├── src/
│   ├── assets/
│   ├── components/
│   ├── router/
│   │   └── index.js
│   ├── views/
│   │   ├── AdminDashboard.vue
│   │   ├── EmployeeDashboard.vue
│   │   ├── ManagerDashboard.vue
│   │   ├── OrderingView.vue
│   │   ├── ProductSearchView.vue
│   │   └── StartOrderView.vue
│   ├── App.vue
│   └── main.ts
├── index.html
└── package.json
```

### Tools & Features

- **Vue 3** (Composition API, SFCs)
- **Electron** (Native desktop deployment)
- **Vite** (Frontend build tool)
- **Vue Router** (SPA routing)
- **TypeScript**

---

## Core Functionalities (Planned or Built)

- Add and search for customers and products using flexible query combinations (e.g., name, email, phone)
- Create and manage customer orders with editable order carts and cost calculations
- Role-based dashboards for Admins, anagers, and Employees with real-world functionality simulating the needs of businesses that manage multiple authorization levels
- Role restricted behavior through Spring Security
- Live product and customer lookup with scrollable results
- Service -layer validation and mapping via MapStruct and DTOs
- Unit tests using JUnit 5 and Mockito for business logic
- Planned integration test suite for front-backend functionality
- Planned support for JWT authentication and CSV exports

---

## Future Enhancements

- JWT authentication and session management
- Export reports as downloadable CSVs
- Automated integration tests
- Dynamic UI loading and enhanced validation
- PostgreSQL or MySQL deployment setup 
- Fully deployed, functional app

---

## Contact

If you'd like to collaborate, contribute, or ask questions, feel free to reach out via GitHub Issues or [Logan Byard](mailto\:lbyard@wisc.edu).

---

> This project is an academic/professional showcase and currently not intended for production use.

