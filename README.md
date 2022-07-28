# Read Me First

How to implement **multiple database/datasource** connections.  
Project using: Spring Starter JDBC and Oracle database.  

### Tenant databases:
Customer databases (1..N) determined based on the URL PathVariable (set to ThreadLocal).

### Main database:
One default (main) database, used (for example) to store env configurations.

# Install

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

# Run

Set database connections in the application.proprerties file.  
Start the application: `./mvnw spring-boot:run`

# Test

https://httpie.io/
```
http localhost:8080/data/tenant/dbone
http localhost:8080/data/tenant/dbtwo

http localhost:8080/both/tenant/dbone
http localhost:8080/both/tenant/dbtwo

http localhost:8080/main
```

