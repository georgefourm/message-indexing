package com.georgesdoe;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Client {
	
	private final static String QUEUE_NAME = "MAIN", key="secretkey1123456";
	private Channel channel;
	Connection connection;
	AESEncrypter enc;

	public Client() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		connection = factory.newConnection();
		enc=new AESEncrypter(key);
		
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}
	
	public void SendMessage(String plaintext) throws Exception{
		//Concatenate ciphertext and IV
		byte[] ciphertext=enc.encryptString(plaintext);
		byte[] iv=enc.IV;
		byte[] message=new byte[ciphertext.length+iv.length];
		
		System.arraycopy(ciphertext, 0, message, 0, ciphertext.length);
		System.arraycopy(iv, 0, message, ciphertext.length, iv.length);
		
		channel.basicPublish("", QUEUE_NAME, null, message);
		
	}

}
