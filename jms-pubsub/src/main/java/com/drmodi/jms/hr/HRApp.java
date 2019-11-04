package com.drmodi.jms.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.model.Employee;

public class HRApp {
	
	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext()){
			
			
			//create new employee object
			Employee emp = new Employee(050711, "Dixit", "Modi", "Software Engineer", "drmodi.jmcns@gmail.com", "5525599200");
			
			//create producer and send serializable model object.
			jmsContext.createProducer().send(topic, emp);
			
			System.out.println("Message Sent From Publisher - HRApp");
			
		}
		
	}

}
