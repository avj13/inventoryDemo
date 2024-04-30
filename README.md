## README

### Inventory Management System

This repository contains a Spring Boot application for an Inventory Management System with support for PostgreSQL database, Prometheus metrics.

### Features
- **Inventory Management**: Add, deduct, and track inventory quantities.
- **PostgreSQL Database**: Store inventory data in a PostgreSQL database.
- **Prometheus Metrics**: Expose custom metrics for monitoring.

### Setup Instructions

#### Prerequisites
- Java 11 or higher installed
- PostgreSQL database installed and running

#### Steps to Run the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/inventory-management.git
   ```

2. Configure PostgreSQL:
   - Create a database and schema using the provided SQL commands in `sqlCommand.sql`.
   - Update `application.properties` with your PostgreSQL database details.

3. Start the Spring Boot application:
   ```bash
   cd inventory-management
   ./mvnw spring-boot:run
   ```
(Optional)

4. Configure Prometheus and Grafana: (Configurations Needed)
   - Start Prometheus and Grafana using Docker:
   - Configure Prometheus to scrape metrics from `localhost:8080` in `prometheus.yml`.
   - Prometheus data source in Grafana can be added

5. Access the Application:
   - Inventory Management System: http://localhost:8080
   - Prometheus Metrics: http://localhost:8080/actuator/prometheus


### Additional Notes
- Only Inventory Repository and functionality have been implemented as per the requirements.
- Code is cusomtizable to addition for Caching and handling the Consistency problems arriving while implementing such layers.
