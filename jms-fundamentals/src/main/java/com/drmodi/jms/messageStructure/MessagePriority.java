package com.drmodi.jms.messageStructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
			
			JMSProducer producer = jmsContext.createProducer();
			
			String[] messages = new String[3];
			messages[0]="Message 0";
			messages[1]="Message 1";
			messages[2]="Message 2";
			
			//Message priority can be define using decimal number system of 0-9
			//Higher the number, higher the priority
			
			//producer.setPriority(5); //to see default priority #4 -  
										//enable above statement, it will reflect mentioned #5
			producer.send(queue, messages[0]);
			
			producer.setPriority(3);
			producer.send(queue, messages[1]);
			
			producer.setPriority(7);
			producer.send(queue, messages[2]);
			
			//create consumer
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			for(int i=0; i<3; ++i) {
				//retrieve the priority from message
				Message receivedMessage = consumer.receive();
				System.out.println("Message Priority : "+receivedMessage.getJMSPriority());
				System.out.println("# Message Text : "+ receivedMessage.getBody(String.class));
				
				//System.out.println(consumer.receiveBody(String.class)); // to receive direct body message
			}
			
			
		}
		
		
	}

}

