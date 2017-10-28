# Test Payment Distribution APIs
POST localhost:8003/api/payments
{
	"amount": 58,
	"orderId": "5903e81327b884525eb9a5be",
	"creditCardInfo": {
		"firstName": "first 1",
		"lastName": "last1",
		"expiredMonth": "02",
		"expiredYear": "2019",
		"securityCode": "231"		
	}
}

Check RabbitMQ at: 
http://localhost:15672/#/queues/%2F/binder.payments

See the message is sent to the queue.