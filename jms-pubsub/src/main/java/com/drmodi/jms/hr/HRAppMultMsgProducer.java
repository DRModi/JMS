package com.drmodi.jms.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.model.Employee;

public class HRAppMultMsgProducer {
	
	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext=cf.createContext()){
			
			
			//create multiple object employee object
			for(int i=0; i<10; i++) {
				Employee emp = new Employee(i, "Jay", "Mataji " + i, "Software Engineer", "jay"+i+"@gmail.com", "552559920"+i);
				//create producer and send serializable model object.
				jmsContext.createProducer().send(topic, emp);
				
			}
			
			
			
			System.out.println("Message Sent From Publisher - HRApp");
			
		}
		
	}

}

