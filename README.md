## Spring AMQP Demo
[https://spring.io/guides/gs/messaging-rabbitmq/](https://spring.io/guides/gs/messaging-rabbitmq/)

## 啟動 Rabbit MQ Service
```
cd docker
docker-compose up
```

## 執行
```
./mvnw clean package
java -jar target/rabbit-mq-demo-0.0.1-SNAPSHOT.jar
```

## Result
```
發送訊息...
========================================= 
Received: Hello World from RabbitMQ!
========================================= 
```
