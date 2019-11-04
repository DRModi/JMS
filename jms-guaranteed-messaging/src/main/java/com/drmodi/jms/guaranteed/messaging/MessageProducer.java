package com.drmodi.jms.guaranteed.messaging;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageProducer {
	
	public static void main(String[] arguments) throws NamingException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		
		
		//Define AUTO Acknowledge or DUPS_OK can be declared during jms context creation
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
				//JMSContext jmsContext = cf.createContext(JMSContext.DUPS_OK_ACKNOWLEDGE)){
			
			JMSProducer producer = jmsContext.createProducer();
			producer.send(queue, "Message 1 - From Message Producer");
		}
		
	}

}
