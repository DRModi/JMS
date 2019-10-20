package com.drmodi.jms.basics;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMSContextDemo {
	
	public static void main(String[] args) throws Exception {
		
		//1. create ActiveMQconnection Factory, 
		//2. create context 
		//3. create producer using JMSContext and send message
		//4. create consumer and receive body message.
		
		//Note: once we start using the JBoss application server, we can directly use JBoss context to look up destination,
		// till that we are using the regular way
		
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		//1. 2. create factory and context using 2.0 API with in Try block so we not need to close 
		//these resources, they will be closed automatically by JVM - Java 7 onwards. 
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();){
			
			//3. create producer and send message
			jmsContext.createProducer().send(queue, "My future is created by what I do today, NOT tomorrow!");
			
			//4. create consumer and receive message
			String receiveBodyMessage = jmsContext.createConsumer(queue).receiveBody(String.class);
			
			System.out.println("Receieved Message : "+ receiveBodyMessage);
		}
		
	}

}
