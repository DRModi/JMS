package com.drmodi.jms.basics;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;


public class QueueBrowserDemo {
	
	public static void main(String[] args) throws Exception {
	
		//1. connect to queue, 
		//2. create queue browser
		//3. Get the enumeration/no of messages and browse through it
		
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory) context.lookup("myConnectionFactory");
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		Connection connection = cf.createConnection();
		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(queue);
		
		TextMessage m1 = session.createTextMessage("Message 1");
		TextMessage m2 = session.createTextMessage("Message 2");
		
		producer.send(m1);
		producer.send(m2);
		
		//Create browser, get the number of elements in queue
		QueueBrowser browser = session.createBrowser(queue);
		Enumeration messageList = browser.getEnumeration();
		
		while(messageList.hasMoreElements()) {
			TextMessage message = (TextMessage) messageList.nextElement();
			System.out.println("Retrieved Messages : "+message.getText());
		}
		
		connection.start();
		
		
		MessageConsumer consumer = session.createConsumer(queue);
		TextMessage receive1 = (TextMessage) consumer.receive();
		System.out.println("Consume message 1 :"+receive1.getText());
		TextMessage receive2 = (TextMessage) consumer.receive();
		System.out.println("Consume message 2 :"+receive2.getText());		
	
		
		//close connections
		context.close();
		connection.close();
	}

}
