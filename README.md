# document-tendency



### App architecture

- eureka -> Eureka Server
- gateway -> route apps api
- document-publisher -> microservice to create documents and send it to RabbitMQ Excange
- document-tendency:
  - pull notificiation from RabbitMQ
  - provide API to get most popular documents for last week
  - provide API to get trending documents for last week
-------------------
### Endpoints

**- api endpoint:**
http://localhost:9090/api/

**- document-publisher:**


[/documents/create](http://localhost:9090/api/documents/create) -> create 200 documents for previous week

**- document-tendency:**

[/tendencies/popular](http://localhost:9090/api/tendencies/popular) -> get json with 10 most popular documents for last week

to reduce limits use Query parameter: (Integer) resultLimit

[/tendencies/trending](http://localhost:9090/api//tendencies/trending) -> get json with most trending documents

-------------------
### Tests

- Run TendencyControllerTest.
- Testcontainers are implemented
