## SQK-API-SERVER
- Java 17
- Gradle
- Spring boot 3.0.3
- JPA
- Lombok 1.18.26
- Kubernetes SDK 15.0.1
- Swagger Springdoc 2.0.2


## Dev Tools
1. IDE: https://doorae3-my.sharepoint.com/:u:/g/personal/logan_sqkcloud_com/EVOZTrQoVkRIpLHMcON3cZcB0ddLn6l5NOmIIlX61NBGBA?e=ci0gV0
2. JDK: https://doorae3-my.sharepoint.com/:u:/g/personal/logan_sqkcloud_com/EewdxuIpJjdBgF0sFn35ALcBkx9zPiGt89TmtfxAQhv5gw?e=ANGeeN


## Build & Run

1. CLI : ./gradlew bootRun
2. IntelliJ : Tasks > application > bootRun <br>
   ![Intellij](https://perfectacle.github.io/2020/12/26/intellij-vs-gradle-in-classpath/gradle-boot-run-task.png)
3. http://localhost:8080/swagger-ui/index.html


## Multi Datasource Config
### application.properties
```js
spring.datasource.sc.jdbcUrl=jdbc:mysql://localhost:3306/sc_test
spring.datasource.sc.username=root
spring.datasource.sc.password=password
spring.datasource.sc.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.sciaas.jdbcUrl=jdbc:mysql://localhost:3306/test
spring.datasource.sciaas.username=root
spring.datasource.sciaas.password=password
spring.datasource.sciaas.driver-class-name=com.mysql.cj.jdbc.Driver

# Spring JPA
spring.jpa.database=mysql
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=none
spring.jpa.generate-ddl=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.hibernate=debug

```
### Configuration
```js
e.g.
com.sqk.config.ScDatasourceConfig
com.sqk.api.services.ss.repository (Repository 설정=Table)
com.sqk.api.services.ss.dao (Table Model)

```
<br>   

## 🙌 Todo
- Kubernetes API
- Helm Chart API
- SC, SCIaaS, SQK Console REST API Service


<br>   

