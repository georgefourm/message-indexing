# Message Indexing
A Message Indexing system created for educational purposes, using [RabbitMQ](http://www.rabbitmq.com/) and [Solr](http://lucene.apache.org/solr/).
Messages are encrypted using AES, and are then sent over RabbitMQ to a "middleware", where they are then indexed and stored using Solr. 
