

🛒 Ecommerce Microservices Project
=================================

This project is a complete eCommerce backend system designed using a microservices architecture. It demonstrates core principles like service discovery, centralized configuration, async messaging, API Gateway routing, distributed tracing, and secure authentication.

> Built with Spring Boot, Apache Kafka, Eureka, Zipkin, PostgreSQL, and Spring Cloud Config.

------------------------------------------------------------
🚀 Features
------------------------------------------------------------

- 🧩 Modular Microservices: Each business function is a separate microservice
- 📦 Service Discovery using Eureka
- 🔄 Async Communication via Apache Kafka
- 🔐 Authentication Server for securing endpoints using OAuth2/JWT
- 🔀 API Gateway to route and filter requests
- ⚙️ Centralized Config via Spring Cloud Config Server
- 🔍 Distributed Tracing via Zipkin
- 🗄️ PostgreSQL used for persistence across services

------------------------------------------------------------
📁 Microservices Structure
------------------------------------------------------------

- AsyncDataUpdateService
- AuthServer
- configServer
- Eureka
- gatewayServer
- orderProcessing
- paymentService
- product-catalog

Each folder is a standalone Spring Boot Maven project.

------------------------------------------------------------
🧰 Tech Stack
------------------------------------------------------------

| Layer             | Technology            |
|------------------|------------------------|
| API Gateway       | Spring Cloud Gateway   |
| Service Registry  | Netflix Eureka         |
| Messaging Queue   | Apache Kafka           |
| Auth Service      | Spring Security + JWT  |
| Config Management | Spring Cloud Config    |
| Tracing           | Zipkin                 |
| Database          | PostgreSQL             |
| Build Tool        | Maven                  |

------------------------------------------------------------
⚙️ Setup Instructions
------------------------------------------------------------

1. 📥 Clone Repositories
```
git clone https://github.com/DarshanAguru/ecommerceMicroservice.git
```
2. 🐘 Setup PostgreSQL

- Install PostgreSQL and create necessary schemas.
- Update DB credentials in the application.yml files or externalized configs.

3. ⚙️ Setup Kafka

- Download Apache Kafka binary: https://kafka.apache.org/downloads
- Extract and set environment variable pointing to Kafka's bin folder
- Add the "setupKafka.py" script to the Kafka root directory
- In the Kafka root directory:
```
python setupKafka.py [topic-name]  # Only needed on first run
```
4. 🔍 Setup Zipkin

- Download Zipkin JAR: https://zipkin.io/pages/quickstart
- Run with:
```
java -jar zipkin.jar
```
Default Zipkin UI: http://localhost:9411/

5. 🔧 Run Microservices

- Open each folder (e.g., AuthServer, orderProcessing, etc.) in your preferred IDE
- Run them as Spring Boot applications
- Ensure Eureka (http://localhost:8761) and Config Server are running first

6. 🛠 Config Server Setup

- The Config Server pulls from:
  https://github.com/DarshanAguru/ecommerceMicroserviceConfigs
  Its a public repo, feel free to clone it...

------------------------------------------------------------
🔗 Service Dependencies
------------------------------------------------------------

| Service                  | Depends On                    |
|--------------------------|-------------------------------|
| AuthServer               | Config Server, PostgreSQL     |
| AsyncDataUpdateService   | Kafka, Config Server          |
| orderProcessing          | Kafka, PostgreSQL, Config     |
| paymentService           | PostgreSQL, Config            |
| product-catalog          | Kafka, PostgreSQL, Config     |
| gatewayServer            | Eureka, Config, AuthServer    |
| configServer             | Config Repo                   |
| Eureka                   | (Core registry)               |

------------------------------------------------------------
📝 Future Improvements
------------------------------------------------------------

- Add Prometheus + Grafana for monitoring
- Dockerize all services
- Add Redis for caching
- Add CI/CD pipeline (e.g., GitHub Actions)

------------------------------------------------------------
🤝 Contribution
------------------------------------------------------------

Feel free to fork the repo, raise issues, or submit pull requests!  
This project is open for learning, collaboration, and enhancements.

------------------------------------------------------------
📬 Contact
------------------------------------------------------------

Darshan Aguru  
GitHub: https://github.com/DarshanAguru  
Devfolio: https://agurudarshan.tech





