package com.drmodi.jms.p2p.eligibilitycheck;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.p2p.eligibilitycheck.listeners.EligibilityCheckListner;

public class EligibilityCheckerApp {
	
	public static void main(String[] args) throws NamingException, InterruptedException {
		
		//create initial context, retrieve queue
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		
		//JMSContext
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
			
			//create consumer
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			consumer.setMessageListener(new EligibilityCheckListner());
		
			Thread.sleep(10000);
		}
		
	}

}
