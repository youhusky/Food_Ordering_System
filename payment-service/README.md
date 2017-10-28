# Test Payment Service APIs
After posting a payment message at payment-distribution and see the message count increased at RabbitMQ.

Start payment-service, and see the message count decreased at RabbitMQ at:
http://localhost:15672/#/queues/%2F/binder.payments
