# Develop a Food Ordering System using Microservices architecture and Spring Cloud

This is a system designed in **Spring Boot/Data/Cloud** to provide **REST** APIs for food ordering/delivery system.

## Development environment
- **Mongo DB** is running on **docker**, image specified in **docker-compose.yml** file, and started by **docker-compose**.
- **Spring Data Rest** is used for quick DB access and verification.
- **Spring Boot** is used for fast REST API development and independant deployment.
- **Spring Boot Actuator** is used to provide monitoring information. (/health, /metrics... etc.)
- **HAL Browser** is used for quick repository exploration.
- **Lombok** is used to eliminate constructors and getter/setter implementation for cleaner coding style.
- **Spring Cloud** is used to provide infrastructure services.
- **Eureka** is used for microservices registration and discovery.
- **Hystrix** is used as circuit breaker when failure to prevent avalanche in system.
- **RabbitMQ** is used to decouple microservices.
- **WebSocket** is used to send message to UI.
- **RestTemplate** is used to communicate between microservices.

## Features
- User can search a restaurant based on restaurant name.
- User can order food by choosing different menu item, quantity and add a note about his/her diet restrictions and etc.
- User can fill in the delivery address.
- After user places an order, the order should contain the food items user ordered, quantity, price and order time.
- User needs to pay for his/her order by providing credit card number, expiration date and security code.
- After payment is made successfully, it should return payment ID, timesatmp and then the order is considered as completed so the user can see the estimated delivery time.
- Estimated delivery time is a random time between 5 minutes ~ 1 hour.

![Food Ordering System Design Diagram](/FoodOrderingSystemDesign.png)

## REST APIs
### Restaurant Service
Search restaurant by name
```
GET localhost:8001/api/restaurants?name=<restaurant_name>
Return
{
    "id": <restaurant_id>,
    "name": <restaurant_name>
}
```
Create a restaurant
```
POST localhost:8001/api/restaurants
Request Body
{
    "name": <restaurant_name>
}
Return HttpStatus.CREATED
```
Get all menu items by restaurant id
```
GET localhost:8001/api/restaurants/{restaurantId}/menuItems
Return
[
    {
        "id": <menu_item_id>,
        "restaurantId": <restaurantId>,
        "name": <menu_item_name>,
        "description": <menu_item_description>,
        "price": <menu_item_price>
    },
    ...
]
```
Create menu item
```
POST localhost:8001/api/restaurants/{restaurantId>/menuItems
Request Body
{
        "restaurantId": <restaurantId>,
        "name": <menu_item_name>,
        "description": <menu_item_description>,
        "price": <menu_item_price>
}
Return HttpStatus.CREATED
```
Bulk upload menu items
```
POST localhost:8001/api/restaurants/bulk/menuItems
Request Body
[
    {
        "restaurantId": <restaurantId>,
        "name": <menu_item_name>,
        "description": <menu_item_description>,
        "price": <menu_item_price>
    }
]
Return HttpStatus.CREATED
```
### Order Service
Create an order
```
POST localhost:8002/api/restaurants/{rid}/orders
{
    "restaurantId": <restaurant_id>,
    "items":
    [
        {
            "name": <menu_item_name>,
            "price": <menu_item_price>,
            "quantity": <# of items>
        },
        ...
    ],
    "userInfo":
    {
        "firstName": <customer_first_name>,
        "lastName": <customer_last_name>,
        "phone": <customer_phone>,
        "address": <customer_address>
    },
    "specialNote": <special_note>
}
Return:
HttpStatus.CREATED
{
    "id": <order_id>,
    "restaurantId": <restaurant_id>,
    "items":
    [
        {
            "name": <menu_item_name>,
            "price": <menu_item_price>,
            "quantity": <# of items>
        },
        ...
    ],
    "userInfo":
    {
        "firstName": <customer_first_name>,
        "lastName": <customer_last_name>,
        "phone": <customer_phone>,
        "address": <customer_address>
    },
    "specialNote": <special_note>,
    "totalPrice": <total_price>,
    "orderTime": <order_time_in_milliseconds>
}
```
### Payment Distribution Service
Payment distribution
```
POST localhost:8003/api/payments
{
    "orderId": <order_id>,
    "amount": <payment_amount>,
    "creditCardInfo": 
    {
        "firstName": <first_name>,
        "lastName": <lastName>,
        "expiredMonth": <month>,
        "expiredYear": <year>,
        "securityCode": <security_code>
    }
}

Note: Since payment process can be slow and become a bottleneck in a busy environment, this API will send the payment information to message queue for Payment Service to process.
```
### Payment Service
Process payment
```
POST localhost:8004/api/payments
{
    "orderId": <order_id>,
    "amount": <payment_amount>,
    "creditCardInfo": 
    {
        "firstName": <first_name>,
        "lastName": <lastName>,
        "expiredMonth": <month>,
        "expiredYear": <year>,
        "securityCode": <security_code>
    }
}
Return
HttpStatus.CREATED

Note: **Hystrix** fallbackMethod is added in case processPayment() fails.
Note: this API will store the payment into DB, find the matching order and update the order paymentId, deliveryTime, and notify Order Complete Service with RestTemplate.
```
### Order Complete Updater Service
Order complete
```
POST localhost:8005/api/orders
{
    "id": <order_id>,
    "items":
    [
        {
            "name": <menu_item_name>,
            "price": <menu_item_price>,
            "quantity": <# of items>
        },
        ...
    ],
    "userInfo":
    {
        "firstName": <customer_first_name>,
        "lastName": <customer_last_name>,
        "phone": <customer_phone>,
        "address": <customer_address>
    },
    "specialNote": <special_note>,
    "totalPrice": <total_order_price>,
    "orderTime": <order_time>,
    "deliveryTime": <food_delivery_time>,
    "paymentId": <payment_id>
}

Note: This API will serialize the order to WebSocket channel: "topic/orders", UI can subscribe to this channel to receive message and display to user.
```
## Getting started
### Start MongoDB, RabbitMQ on Docker
```bash
docker-compose up -d
```
### Check data in MongoDB
Find mongodb container id
```
docker ps
```
Enter mongodb container by typing the first 3 charactters of the container id (ex: '9cd'), then type mongo inside the container to use mongodb shell command.
```
docker exec -it 9cd bash
# mongo                             // open mongo shell
> use test                          // Spring boot use test db as default
> show collections                  // show all collections inside test db
> db.restaurant.find().pretty()     // show all data inside restaurant table
> exit                              // quit mongo shell
> exit                              // exit container shell
```

### Installation
```bash
mvn clean install
```
### Start Eureka
```bash
sh ./start-eureka.sh
```
### Start Hystrix
```bash
sh ./start-hystrix.sh
```
### Start Restaurant Service
```bash
sh ./start-restaurant-service.sh
```
### Start Order Service
```bash
sh ./start-order-service.sh
```
### Start Payment Distribution
```bash
sh ./start-payment-distribution.sh
```
### Start Payment Service
```bash
sh ./start-payment-service.sh
```
### Start Order Complete Updater
```bash
sh ./start-order-complete-updater.sh
```
### Upload Test Menu Items
```bash
cd restaurant-service
sh ./upload-menu-items.sh

Note: default restaurant id for testing: "11111111-1111-1111-11111111111111111".
```
### Explore by HAL Browser
```bash
http://localhost:8001/browser/index.html

port: 8001 can be changed for different services.
```
#### Investigate registerd services in Eureka
```
http://localhost:8761
```
#### Investigate message queue in RabbitMQ
```
http://localhost:15762
Go to Queues -> binder.payments
```
### Checkout application metrics
```
http://localhost:8005/health
http://localhost:8005/env
http://localhost:8005/metrics
http://localhost:8005/mappings

Note: 8005 can be changed for different services.
```
### Test work flow with PostMan
Create an order
```
POST localhost:8002/api/restaurants/11111111-1111-1111-11111111111111111/orders
{
    "restaurantId": "11111111-1111-1111-11111111111111111",
    "items": [
        {
            "name": "menuItem 1",
            "price": 11,
            "quantity": 2
        },
        {
            "name": "menuItem 2",
            "price": 12,
            "quantity": 3
        }
    ],
    "userInfo": {
        "firstName": "first1",
        "lastName": "last1",
        "phone": "14081234567",
        "address": "123 stree1 ave, San Jose, CA 95123"
    }
}
Returns:
Returns:
{
    "id": "5903e81327b884525eb9a5be",
    ...
    "totalPrice": 58,
    ...
}

```
Post a payment for the order
```
POST localhost:8003/api/payments
{
    "amount": 58,
    "orderId": "5903e81327b884525eb9a5be",
    "creditCardInfo": {
        "firstName": "first 1",
        "lastName": "last 1",
        "expiredMonth": "02",
        "expiredYear": "2019",
        "securityCode": "231"
    }
}
```
Check RabbitMQ, you will see a message is queued by payment-distribution and consumed by payment-service.

At the log of order-complet-updater, you will see the following message:
"Receive order = Order(id=5903e81327b884525eb9a5be, ..."
"WebSocketSession[1 current WS(1)-HttpStream(0)-HttpPoll(0), 3 total..."

So the message has been sent out through WebSocket by order-complete-updater.

To see the message with test UI:
```
localhost:8005
Click on "Subscribe to Order Complete Updates" to subscribe the channel.
Click on "Send Test Message" to send out the test JSON object.
```
See the message received at the buttom text box.
```
Subscribed to /topic/orders
sendMessage triggered
{
  "id": "5903e81327b884525eb9a5be",
  "restaurantId": "11111111-1111-1111-11111111111111111",
  "items": [
     {
       "name": "menuItem 1",
       "price": 11,
       "quantity": 2
     },
     {
       "name": "menuItem 2",
       "price": 12,
       "quantity": 3
     }
  "totalPrice": 58,
  "orderTime": 1493428243933,
  "specialNote": "",
  "deliveryTime": 1493486808730,
  "paymentId": "",
  "userInfo": {
     "id": "",
     "firstName": "first1",
     "lastName": "last1",
     "phone": "14081234567",
     "address": "123 stree1 ave, San Jose, CA 95123"
  }
}
```
### Test fallback
Open Hystrix dashboard
```
localhost:7979
```
Monitor order-complete-updater
```
http://localhost:8004/hystrix.stream
```
Stop order-complete-updater.
Post a payment to payment-service.
Watch processPayment error rate jump from 0.0 to 100%.
Watch error message from fallback method in log.
Keep posting the same payment again and again, eventually, see the Circuit status changed to "Opened" from "Closed."
## LICENSE

[MIT](./License.txt)

