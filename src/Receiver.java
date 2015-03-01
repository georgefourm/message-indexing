package com.georgesdoe;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Receiver {
	
	private final static String QUEUE_NAME = "MAIN",key="secretkey1123456";
	private static Channel channel;
	
	private static void startConnection(String host) throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}

	public static void main(String[] argv) throws Exception {
		
		startConnection("localhost");
		AESEncrypter enc=new AESEncrypter(key);
		SolrjWrapper ind=new SolrjWrapper();
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			byte[] message=delivery.getBody();
			byte[] iv=new byte[16];
			byte[] ciphertext=new byte[message.length-16];
			
			//Extract message and IV
			System.arraycopy(message, 0, ciphertext, 0, message.length-16);
			System.arraycopy(message, message.length-16, iv, 0, 16);
			
			String plaintext=enc.decryptString(ciphertext, iv);
			System.out.println(">>Received: "+plaintext);
			try{
				ind.indexDocument(plaintext);
			}catch(Exception e){
				System.err.println("Error while publishing message: "+e.getMessage());
			}
			
		}
	}
}