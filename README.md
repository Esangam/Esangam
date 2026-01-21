Esangam is an enterprise solution for local communities and welfare activities of those communities.

-----------further information added later----------------------------------

<!---
Esangam/Esangam is a ✨ special ✨ repository because its `README.md` (this file) appears on your GitHub profile.
You can click the Preview link to take a look at your changes.
--->


# 💳 eSangam Loan Management System

A modern, lightweight **loan and member management** system built with **Quarkus**, Hibernate ORM (Panache), and Jakarta REST.

## 🚀 Features

- Manage members with mobile-based unique IDs
- Assign and track loans per member
- Automatically calculate due dates and interest
- REST API endpoints for all major actions
- JPA + Panache-based repositories for clean data access
- Container-ready & production optimized

---

## 📦 Tech Stack

| Layer             | Technology                         |
|------------------|-------------------------------------|
| Backend          | [Quarkus](https://quarkus.io)       |
| ORM              | Hibernate + Panache                 |
| Persistence      | JPA + PostgreSQL/MySQL (configurable)|
| REST API         | Jakarta REST (JAX-RS)               |
| JSON Processing  | Jackson                             |
| Build Tool       | Maven                               |

---

## 🛠️ Setup Instructions

    Configure your database
    Edit src/main/resources/application.properties:

    properties

    quarkus.datasource.db-kind=postgresql
    quarkus.datasource.username=YOUR_DB_USERNAME
    quarkus.datasource.password=YOUR_DB_PASSWORD
    quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/esangam_db

    quarkus.hibernate-orm.database.generation=update
    quarkus.hibernate-orm.log.sql=true
  💡 You can switch to MySQL or H2 by changing db-kind and JDBC URL.

3. Build the project
   
      ./mvnw clean install
   
5. Run in dev mode

      ./mvnw quarkus:dev

Hit the url to start rexploring:

https://esangam-ui.vercel.app/login


Username: 999999999
Password: admin@123


this will opens the page like this 
<img width="1919" height="644" alt="image" src="https://github.com/user-attachments/assets/8641ff4c-c8a1-448b-9ef8-1d81c21ca73c" />

click on sangam and start adding your society then login with your society and add your memebers

