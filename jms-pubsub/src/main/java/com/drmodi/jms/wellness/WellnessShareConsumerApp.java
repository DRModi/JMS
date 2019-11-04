package com.drmodi.jms.wellness;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.model.Employee;

public class WellnessShareConsumerApp {
	
	
public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext contex = new InitialContext();
		Topic topic = (Topic) contex.lookup("topic/empTopic");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext()){
			
			
			
			//create shared consumer, give unique shared subscription name
			JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "sharedSubscription" );
			JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedSubscription" );
			
			//Iterate through messages 
			for(int i=0; i<10; i=+2) {
				Message msg = consumer1.receive();
				Employee employee = msg.getBody(Employee.class);
				System.out.println("Consumer 1 : "+ employee.toString());
				
				Message msg2 = consumer2.receive();
				Employee employee2 = msg2.getBody(Employee.class);
				System.out.println("Consumer 2 : "+ employee2.toString());
			}
			
			
			
			
		}
		
	}

}
