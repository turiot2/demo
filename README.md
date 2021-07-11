Demo application of rest services. 

Implemented in Java 11 using Spring Boot.\
2 differents solutions for persistence : JPA (dao) or MapDB (dao2).

To build : mvn clean install -Pjpa (ou -Pmapdb)\
To run :  mvn spring-boot:run -Pjpa (ou -Pmapdb)

cf doc folder for diagrams.\
a swagger documentation is visible at http://localhost:8080/api/v2/api-docs
