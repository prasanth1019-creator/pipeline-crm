# pipeline-crm

Practice CRM project: companies, contacts, deals. Spring Boot 3, Java 17, Maven, H2.

## Run (IntelliJ)
1. File > Open > select the `pipeline-crm` folder (the one with `pom.xml`). Trust the project.
2. Wait for Maven to finish downloading (bottom-right progress bar).
3. Open `PipelineCrmApplication.java`, click the green arrow next to `main`.
4. Browser: http://localhost:8080/api/companies

## Run (terminal)
    mvn spring-boot:run

## Useful URLs
- http://localhost:8080/api/companies
- http://localhost:8080/api/contacts
- http://localhost:8080/api/deals
- http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:pipelinecrm, user: sa, empty password)
