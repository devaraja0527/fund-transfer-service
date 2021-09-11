#### Transfer API Service

This project is a simple Microservice code for Creating new account, Credit money to existing account, Transfer Money between two existing accounts also retrieve balance for the give account number. 

Data will be stroed in H2 memory database!!

This project uses Spring Boot, Spring boot log4j2 and  h2dabase dependencies.


### REST API

** Key Rules **
  1. Account Number Length is 8 digits (In case required to change, please update constants in Constants Class).
  2. Initial Account Balance is Zero (Please use Credit API to credit Money)
  3. Account Number is considered as String to Show preceding digits.
  
 Please do refer to swagger document for parameters!! 

** Pending Activites **
  1. White Box testing with Cucumber

**Get a New Account ** 

This API retrieves new account with 8 digit account number and zero balance.

```bash
  Method : GET
  End Point: http://localhost:8080/TransferAPI/v1/account 
  Content-type : application/json
  SAMPLE JSON RESPONSE:
   200:{
	  "accountNumber": "00000001",
	  "balance": 0
	} 
```
**Get Account Balance with Account Number** 

This API helps in retrieving existing Account balance, where accountNumber is being passed as a path parameter.

```bash
  Method : GET
  End Point: http://localhost:8080/TransferAPI/v1/account/{accountNumber}
  Content-type : application/json
  SAMPLE JSON RESPONSE:
    200:{
	  "accountNumber": "00000001",
	  "balance": 0
	} 
```

**Credit Money to exiting Account** 

This API helps credit Balance to an existing Bank Account with following request body.

```bash
  Method : GET
  End Point: http://localhost:8080/TransferAPI/v1/credit
  Content-type : application/json
  SAMPLE REQUEST BODY
	{
	  "accountNumber": "00000001",
	  "balance": 300.0
	}
  SAMPLE JSON RESPONSE:
    201:{
	  "transactionId": 1,
	  "sourceAccountBalance": 300,
	  "transactionDateTime": "2021-09-11T17:26:14.343"
	}
```

**Transfer Money between exitsting accounts** 

This API help to Transfer between two existing accounts with following request body.

```bash
  Method : GET
  End Point: http://localhost:8080/TransferAPI/v1/transfer
  Content-type : application/json
  SAMPLE REQUEST BODY
	{
	  "amount": 0,
	  "destinationAccountNumber": "string",
	  "sourceAccountNumber": "string"
	}
  SAMPLE JSON RESPONSE:
    201:{
	  "transactionId": 1,
	  "sourceAccountBalance": 300,
	  "transactionDateTime": "2021-09-11T17:26:14.343"
	}
```



**Sample Error Response**

```bash
	{
	  "code": "500",
	  "message": "Invalid Input provided!!",
	  "statusCode": 500
	}

```

**Supported Http Codes**

```bash
	
	Supports Http Codes like 
	
	200, 201, 400, 401, 404, 500 , 503.. etc

```


### Machine Requirements
* Java 1.8
* Maven 3.3.1 or higher


### Config Requirement

The following yml properties has to be updated in **'src/main/resources/application.yml'** file.




### Build Local

```bash
$ mvn clean package       
```

### Run Local

```bash
$ mvn spring-boot:run

```
View the application running here: [http://localhost:8080](http://localhost:8080)

Application Context Path :: **/TransferAPI/v1**



### Swagger 

After Successful application start you can find the swagger below 

Swagger UI :: [http://localhost:8080/TransferAPI/v1/swagger-ui.html#/](http://localhost:8080/TransferAPI/v1/swagger-ui.html#/)

Swagger Docs :: [http://localhost:8080/TransferAPI/v1/v2/api-docs](http://localhost:8080/TransferAPI/v1/v2/api-docs)





