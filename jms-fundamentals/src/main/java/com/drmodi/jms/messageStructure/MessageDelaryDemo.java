package com.drmodi.jms.messageStructure;


import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageDelaryDemo {
	
	public static void main(String[] args) throws NamingException, JMSException {
	
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
			JMSProducer producer = jmsContext.createProducer();
			
			//add the delay here in milliseconds
			producer.setDeliveryDelay(1500);
			
			producer.send(queue, "Message will be delivered with 1500 millisecond Delay!");
			
			Message message = jmsContext.createConsumer(queue).receive(2000);
			System.out.println("Recieved Message: "+ message.getBody(String.class));
		}
		
	}

}
