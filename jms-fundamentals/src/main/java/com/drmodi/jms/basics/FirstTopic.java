package com.drmodi.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;


public class FirstTopic {
	
	public static void main(String[] args) throws Exception {
		
		//------ Publisher / Subscriber (pub/sub in action) -----
		
		//Similar step in this scenario destination would be the topic
		
		//1. Create initial context, find connection factory and topic
		InitialContext initialContext = new InitialContext();
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("myConnectionFactory");
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		
		//2. create connection, session, producer, message
		Connection connection = cf.createConnection();
		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(topic);
		TextMessage message = session.createTextMessage("Fear doesn't prevent death, it prevent life!");
				
		//3. create consumer and consume the message
		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);
		
		//4. send message
		producer.send(message);
		
		//5. start the connection
		connection.start();
		
		//6. consume the message
		TextMessage receiveMessage1 = (TextMessage) consumer1.receive();
		TextMessage receiveMessage2 = (TextMessage) consumer2.receive();
		
		System.out.println("recieved Message 1 : "+receiveMessage1.getText());
		System.out.println("recieved Message 2 : "+receiveMessage2.getText());
		
		
		//close the connection factory and connections.
		initialContext.close();
		connection.close();
	}

}
