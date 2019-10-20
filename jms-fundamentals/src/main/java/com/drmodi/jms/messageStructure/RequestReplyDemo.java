package com.drmodi.jms.messageStructure;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		//1. Lookup the request and reply queue
		//2. producer create message and assign/mention reply queue / create temporary response queue / usage of correlation id
		//3. send the message to requestQueue
		//4. consumer consume the message from request queue
		//5. create replyPoducer and get the destination from assigned reply queue
		//6. create replyConsumer to retrieve reply message
		
		//1
		InitialContext context = new InitialContext();
		Queue reqQueue = (Queue) context.lookup("queue/requestQueue");
		//Queue resQueue = (Queue) context.lookup("queue/replyQueue"); // can be created using createTemporaryQueue
		
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
		
			//2.
			JMSProducer producer = jmsContext.createProducer();
			TemporaryQueue resQueue = jmsContext.createTemporaryQueue();
			TextMessage txtMessage = jmsContext.createTextMessage("This is ** REQUEST ** message Check 2");
			txtMessage.setJMSReplyTo(resQueue);
			
			
			//3.
			producer.send(reqQueue, txtMessage);
			txtMessage.setJMSCorrelationID(txtMessage.getJMSMessageID());
			//System.out.println("JMS Message ID from Producer : "+txtMessage.getJMSMessageID());
			//System.out.println(txtMessage);
			
			//checking through map
			Map<String, TextMessage> requestMessage = new HashMap<>();
			requestMessage.put(txtMessage.getJMSMessageID(),txtMessage);
			
			
			//4
			JMSConsumer consumer = jmsContext.createConsumer(reqQueue);
			TextMessage txtMessageReceived = (TextMessage) consumer.receive();
			//Destination replyToDestination = txtMessageReceived.getJMSReplyTo();
			System.out.println("Received Request JMS Message ID : "+ txtMessageReceived.getJMSMessageID());
			System.out.println("Received Request : "+txtMessageReceived.getText());
			System.out.println(txtMessageReceived);
			
			
			
			//5
			JMSProducer ackProducer = jmsContext.createProducer();
			TextMessage ackTextMesssage = jmsContext.createTextMessage("This is *** ACK *** message!!");
			ackTextMesssage.setJMSCorrelationID(txtMessageReceived.getJMSMessageID());
			ackProducer.send(txtMessage.getJMSReplyTo(), ackTextMesssage);
			
			//6
			JMSConsumer ackConsumer = jmsContext.createConsumer(resQueue);
			TextMessage recievedAckMessage = (TextMessage) ackConsumer.receive();
			System.out.println("Ack Message :"+recievedAckMessage.getText());
			System.out.println("Ack Correlation Id : "+ recievedAckMessage.getJMSCorrelationID());
			
			System.out.println(requestMessage.get(recievedAckMessage.getJMSCorrelationID()).getText());
			
			
		}
		
	}

}
;