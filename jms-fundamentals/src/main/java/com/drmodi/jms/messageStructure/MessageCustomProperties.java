package com.drmodi.jms.messageStructure;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageCustomProperties {
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext(); 
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
			TextMessage msg = jmsContext.createTextMessage("Message 1");
			
			//setting up custom property using default boolean and string property
			msg.setBooleanProperty("loggedIn", true);
			msg.setStringProperty("user", "User123");
			
			producer.send(queue, msg);
			
			
			Message receivedMsg = jmsContext.createConsumer(queue).receive();
			System.out.println("Received Message : "+receivedMsg.getBody(String.class));
			System.out.println("Logged In Property value : "+receivedMsg.getBooleanProperty("loggedIn"));
			System.out.println("User Poperty value : "+receivedMsg.getStringProperty("user"));
			
		
		}
	}

}
