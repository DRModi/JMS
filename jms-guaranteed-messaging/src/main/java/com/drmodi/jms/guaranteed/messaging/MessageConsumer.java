package com.drmodi.jms.guaranteed.messaging;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageConsumer {
	
	
	public static void main(String[] arguments) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		
		
		//Define Client Acknowledge during jms context creation
		// These will let JMS provider know via message.acknowledge() method that Message is consumed
		// So if acknowledge method not being called when CLIENT_ACK being used then provider will not remove the message
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext(JMSContext.CLIENT_ACKNOWLEDGE)){
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage txtMessage = (TextMessage) consumer.receive();
			System.out.println("Print Received Message : "+txtMessage.getText());
			
			//if below method not called then provider will not remove the message from queue and consumer will keep
			// receiving the same message. can be tested by commenting below code.
			txtMessage.acknowledge();
		}
		
	}

}
