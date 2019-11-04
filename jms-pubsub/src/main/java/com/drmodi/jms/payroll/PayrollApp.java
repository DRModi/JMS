package com.drmodi.jms.payroll;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.model.Employee;

public class PayrollApp {
	
	
public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext contex = new InitialContext();
		Topic topic = (Topic) contex.lookup("topic/empTopic");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext()){
			
			//create consumer
			JMSConsumer consumer = jmsContext.createConsumer(topic);
			Message msg = consumer.receive();
			
			Employee employee = msg.getBody(Employee.class);
			
			System.out.println("Employee Object Info at Payroll App: "+ employee.toString());
			
			
			
		}
		
	}

}
