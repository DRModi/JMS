package com.drmodi.jms.security.clinical;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ClinicalApp {
	
public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
		Queue reqQueue = (Queue) context.lookup("queue/requestQueue");
		//Queue resQueue = (Queue) context.lookup("queue/replyQueue");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext("clinicalUser", "clinical!123")){
			
			JMSProducer producer = jmsContext.createProducer();
			producer.send(reqQueue, "This is message from Producer");
			
			
		}
	}

}
