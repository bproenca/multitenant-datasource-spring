# Read Me First
The following was discovered as part of building this project:

* The original package name 'br.com.bcp.hybrid-multitenancy-spring' is invalid and this project uses 'br.com.bcp.hybridmultitenancyspring' instead.

# Install

### Start Oracle Container
1. Create database DIR volume
	- ex: `mkdir -p /dados/docker/volumes/oracle`
2. Set permission
	- `sudo chown -R 1000 <dir>`
3. Start container
	- `docker run --name oracle11g -d -p 1521:1521 -v /dados/docker/volumes/oracle:/u01/app/oracle/oradata/oracle11g-data/ -e ORACLE_ALLOW_REMOTE=true bproenca/bcp-oracle`
4. Commands **stop/start** container
	- `docker stop oracle11g`
	- `docker start oracle11g`

### Database creation:

```sql
CREATE USER DBONE PROFILE default IDENTIFIED BY "DBONE" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP" ACCOUNT UNLOCK;
GRANT "CONNECT" TO DBONE;
GRANT CREATE TABLE TO DBONE;
GRANT SELECT ANY DICTIONARY TO DBONE;
ALTER USER DBONE QUOTA UNLIMITED ON USERS;

CREATE USER DBTWO PROFILE default IDENTIFIED BY "DBTWO" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP" ACCOUNT UNLOCK;
GRANT "CONNECT" TO DBTWO;
GRANT CREATE TABLE TO DBTWO;
GRANT SELECT ANY DICTIONARY TO DBTWO;
ALTER USER DBTWO QUOTA UNLIMITED ON USERS;

CREATE USER DBMAIN PROFILE default IDENTIFIED BY "DBMAIN" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP" ACCOUNT UNLOCK;
GRANT "CONNECT" TO DBMAIN;
GRANT CREATE TABLE TO DBMAIN;
GRANT SELECT ANY DICTIONARY TO DBMAIN;
ALTER USER DBMAIN QUOTA UNLIMITED ON USERS;
```

DML and DDL:
```sql
-- ONE
CREATE TABLE ABCD (TENANT VARCHAR2(20) , UPDAT DATE , XNAME VARCHAR2(200) );
INSERT INTO ABCD (TENANT, UPDAT, XNAME) VALUES ('DBONE', sysdate, 'Maria');
INSERT INTO ABCD (TENANT, UPDAT, XNAME) VALUES ('DBONE', sysdate, 'Ana');
COMMIT;
--TWO
CREATE TABLE ABCD (TENANT VARCHAR2(20) , UPDAT DATE , XNAME VARCHAR2(200) );
INSERT INTO ABCD (TENANT, UPDAT, XNAME) VALUES ('DBTWO', sysdate, 'Jose');
INSERT INTO ABCD (TENANT, UPDAT, XNAME) VALUES ('DBTWO', sysdate, 'Lucas');
COMMIT;
--MAIN
CREATE TABLE ABCD (TENANT VARCHAR2(20) , UPDAT DATE , XNAME VARCHAR2(200) );
INSERT INTO ABCD (TENANT, UPDAT, XNAME) VALUES ('DBMAIN', sysdate, 'System');
INSERT INTO ABCD (TENANT, UPDAT, XNAME) VALUES ('DBMAIN', sysdate, 'Admin');
COMMIT;
```



# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.2/reference/htmlsingle/#web)
* [JDBC API](https://docs.spring.io/spring-boot/docs/2.7.2/reference/htmlsingle/#data.sql)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.2/reference/htmlsingle/#using.devtools)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.7.2/reference/htmlsingle/#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

