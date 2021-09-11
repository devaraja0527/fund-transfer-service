Feature: Get new Account, Credit Money , Transfer Money and get Balance

 @CreatenewAccountHappyCase
 Scenario Outline: Create new account verify new account is created with status code
    Given Transfer api Service is up and running on random port
    When I send "<request-method>" request to transfer api service with "<request-uri>"
    Then I received the response with <statuscode>
    And the response body is not empty and contantains "<balance>"
    
 Examples:
 | request-uri 	| statuscode	| request-method | balance |
 | /account     | 200					| GET  					 | 0			 |
 
     
  
      
      
      
      