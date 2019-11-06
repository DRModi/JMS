package com.drmodi.jms.security.eligibility;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class EligibilityCheckerApp {
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Queue reqQueue = (Queue) context.lookup("queue/requestQueue");
		//Queue resQueue = (Queue) context.lookup("queue/replyQueue");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext("eligibilityUser1","eligibility!1231")){
			
			JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
			TextMessage message = (TextMessage) consumer.receive();
			System.out.println("Print Received Message: "+message.getText());
			
			
		}
	}

}
