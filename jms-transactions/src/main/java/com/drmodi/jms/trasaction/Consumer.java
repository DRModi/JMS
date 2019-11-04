package com.drmodi.jms.trasaction;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class Consumer {
	
	public static void main(String[] args) throws NamingException, JMSException {
	
	InitialContext context = new InitialContext();
	Queue queue = (Queue) context.lookup("queue/requestQueue");
	
	
	
	
	try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)){
		
		JMSConsumer consumer = jmsContext.createConsumer(queue);
	
		TextMessage txtMessage = (TextMessage) consumer.receive();
		System.out.println("Print Received Message : "+txtMessage.getText());
		
		//jmsContext.commit(); // now message 5 from producer will not removed from the queue, when program run ones only,
							// Message 5 will gone, once it will run second time. due to commit() called here.
	
		TextMessage txtMessage1 = (TextMessage) consumer.receive();
		System.out.println("Print Received Message : "+txtMessage1.getText());
		
		jmsContext.commit(); // both message will be consumed and no messages will be saved on queue,
		
		//jmsContext.rollback();//messages will saved on queue and never consumed till commit being called.
	}
	
}

}
