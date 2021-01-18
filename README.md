# document-tendency



### App architecture

- eureka -> Eureka Server
- gateway -> route apps api
- document-publisher -> microservice to create documents and send it to RabbitMQ Excange
- document-tendency:
  - pull notificiation from RabbitMQ
  - provide API to get most popular documents for last week
  - provide API to get documents by popularity for queried period
  - provide API to get most trending documents for last week
  - provide API to get documents by trending score for queried period
  
  
-------------------
### Endpoints

**- general endpoint:**
http://localhost:9090/api/

**- document-publisher:**


[/documents/create](http://localhost:9090/api/documents/create) -> create 200 documents for previous week

**- document-tendency:**

[/tendencies/popular](http://localhost:9090/api/tendencies/popular) -> get json with 10 most popular documents for last week

[/tendencies/trending](http://localhost:9090/api/tendencies/trending) -> get json with 10 most trending documents for last week

[/tendencies/popular/period](http://localhost:9090/api/tendencies/popular/period?fromDate=2021-01-04&toDate=2021-01-10) -> get json with  trending documents for queried dates, sorted descending

[/tendencies/trending/period](http://localhost:9090/api/tendencies/trending/period?fromDate=2021-01-04&toDate=2021-01-10) -> get json with most trending documents for queried dates, sorted descending

to set period use Query parameters: `fromDate` and `toDate`, value in format: `yyyy-MM-dd`


-------------------
### Tests

- Run TendencyControllerTest.
- Testcontainers are implemented
