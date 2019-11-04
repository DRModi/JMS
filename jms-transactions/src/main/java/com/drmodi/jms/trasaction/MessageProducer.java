package com.drmodi.jms.trasaction;

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
		
		
		//Define Session Transacted for transaction
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)){

			
			JMSProducer producer = jmsContext.createProducer();
			
			producer.send(queue, "Message 1 - From Message Producer");
			producer.send(queue, "Message 2 - From Message Producer");
			//jmsContext.commit();
			producer.send(queue, "Message 3 - From Message Producer");
			jmsContext.rollback(); // will removed all before transaction.
			producer.send(queue, "Message 4 - From Message Producer");
			producer.send(queue, "Message 5 - From Message Producer");
			jmsContext.commit(); // will commit only message 4
		}
		
	}

}
