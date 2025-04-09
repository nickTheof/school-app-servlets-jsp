# 🏫 **School Management App**

## 📋 Description

The **School Management App** is a web-based application designed to manage core school resources.  
This version currently focuses on **Teacher and Student Management**.

The project is built with:

- **Server-Side Rendering (SSR)**
- **Session-Based Authentication**
- **Clean Architecture & Service-Oriented Design**
- **Model-View-Controller (MVC)** architecture

**Controllers** are implemented using **Jakarta Servlets**, and views are rendered with **Java Server Pages (JSP)**.  
For styling, it utilizes **Tailwind CSS**, following a **responsive mobile-first design**.

---

## ✅ Features

- ✔️ **Manage Teachers** – Add, update, delete teacher records
- ✔️ **Manage Students** – Full CRUD operations on student data
- ✔️ **Session-based Login System**
- ✔️ **Separation of Concerns** using SOA and MVC
- ✔️ **Reusable Services and DAOs**
- ✔️ **Mobile-Responsive UI** powered by Tailwind CSS
- ✔️ **Secure Password Hashing** using `BCrypt`

---

## 🛠 Tech Stack

| Layer            | Technology                           |
| ---------------- | ------------------------------------ |
| Language         | Java 17                              |
| Web Framework    | Jakarta EE (Servlets, JSP, EL, JSTL) |
| Frontend Styling | Tailwind CSS                         |
| Build Tool       | Maven                                |
| Database         | MySQL                                |
| Connection Pool  | Apache Commons DBCP2                 |
| Authentication   | BCrypt                               |
| App Server       | Apache Tomcat                        |

---

## 📁 Project Structure

src/
├── main/
│ ├── java/
├── authentication/ # Authentication related classes
├── controller/ # MVC controllers
├── core/ # Core application components
├── dao/ # Data Access Objects (database layer)
├── dto/ # Data Transfer Objects
├── exceptions/ # Custom exception classes
├── filter/ # Servlet filters
├── mapper/ # Object mapping/transformation
├── model/ # Domain models/entities
├── security/ # Security configuration
├── service/ # Business logic services
├── util/ # Utility/helper classes
└── validator/ # Validation logic
webapp/
├── img/ # Image assets
├── js/ # JavaScript files
├── WEB-INF/ # Protected resources
│ ├── jsp/ # JSP view files
│ └── web.xml # Deployment descriptor
└── index.jsp # Application entry point
├── pom.xml # Maven build file
└── README.md # Project documentation

## **Installation & Deployment**

### **Prerequisites**

- **Java 17+** (Ensure Java is installed: `java -version`)
- **Apache Maven** (Verify installation: `mvn -version`)
- **MySQL Database** (Required for data storage)
- **Apache Tomcat 10.1**

### **Setup Instructions**

1. **Clone the Repository**

   ```bash
   git clone git@github.com:nickTheof/school-app-servlets-jsp.git
   cd school-app-servlets-jsp

   ```

2. **Database setup**

    1. **Create a MySQL User**  
       In order to connect to the MySQL database, you must create a new user with the following credentials:

        - **Username**: `user7`
        - **Password**: (Choose a secure password for the user)

    2. **Step 2: Set the User Password in the Environment Variable**

        - **Environment Variable Name**: `PASSWD_USER7`
        - **Environment Variable Value**: The password you used when creating the `user7` MySQL user.

    3. **Step 3: Database Schema and Data**
        - You should create a database schema with name school7dbstaging
        - You can import db schema and data from the dump file you can will in the src/main/resources/sql

3. **Build the Application**

   ```bash
   mvn clean package

   ```

4. **Deploy to Tomcat**
   Copy the generated .war from target/ to Tomcat's webapps/ folder:

   ```bash
   cp target/ssr-servlets-schoolapp.war /path/to/tomcat/webapps/
   ```

   Start Tomcat and access:

   ```bash
   http://localhost:8080/ssr-servlets-schoolapp/
   ```

## 📸 **Screenshots**
