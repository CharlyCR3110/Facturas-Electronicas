### Simplified Electronic Invoice Web System

### Description
The project consists of the creation of a web system in Java for the management of electronic invoices, storing the data in a MySql database. The system will allow suppliers of goods or services to register and make electronic invoicing of sales to their customers, with features such as supplier registration, profile configuration, customer and product registration, invoicing, viewing of invoices in PDF and XML, and system administration.

### Functionalities
1. Supplier Registration
2. Supplier Profile Setup
3. Register Customers
4. Register Products
5. Invoice
6. View Invoices
7. Manage System

### Notes
- As a challenge, the use of JavaScript is prohibited; server-side rendering technique must be used exclusively.
- The system must be implemented with Spring MVC Web framework and Thymeleaf as template engine.
- Per-session access control must be implemented on the server.
- All pages must be based on a template with menu options and user identification.

Certainly! Here's a step-by-step installation process for the Simplified Electronic Invoice Web System:

### Installation Process

#### Prerequisites
1. Java Development Kit (JDK) installed (version 8 or higher).
2. Apache Maven installed.
3. MySQL server installed and running.
4. A compatible web server such as Apache Tomcat.\
5. Intellij IDEA or your preferred IDE for Java development.

#### Steps:

1. **Download the Project Source Code**
   - Clone the project repository from the designated location.
   ```bash
   git clone https://github.com/CharlyCR3110/Facturas-Electronicas.git
   ```

2. **Configure MySQL Database**
   - Execute the SQL script provided in the project's `database` directory to create the necessary database and tables.

   ``` bash
    mysql -u <your_mysql_username> -p < database/01_CreateTables.sql
    ```

    ``` bash
    mysql -u <your_mysql_username> -p < database/02_Triggers.sql
    ```

    Optional: 
    ``` bash
    mysql -u <your_mysql_username> -p < database/03_TestData.sql
    ```
    
    - You can also use a MySQL client tool to execute the script.

3. **Update Database Configuration**
   - Navigate to the project's configuration directory.
   ```bash
   cd electronic-invoice-system/src/main/resources/
   ```
   - Open `.env` file and update the database connection properties.
   ```properties
   APP_NAME=Electronic_Invoice_System
   DB_URL=jdbc:mysql://localhost:3306/facturas_electronicas
   DB_USERNAME=your_mysql_username
   DB_PASSWORD=your_mysql_password
   ```

4. **Build the Project**
   - Navigate to the root directory of the project.
   ```bash
   cd electronic-invoice-system/
   ```
   - Execute Maven build to compile the project.
   ```bash
   mvn clean package
   ```

5. **Run the aplication**
   - Start the application server using your IDE or your preferred method.

7. **Access the Application**
   - Once the server has started, open a web browser and navigate to:
   ```
   http://localhost:8080/
   ```

8. **Initial Setup**
   - Follow the on-screen instructions to complete the initial setup of the system, including creating the admin account and configuring basic settings.

9. **Enjoy Using the Electronic Invoice System!**
   - You can now use the system to manage electronic invoices, register suppliers, customers, products, and more.

### Note:
- Ensure that your MySQL server is running before starting the installation process.
- Customize the installation steps as per your specific environment and requirements.