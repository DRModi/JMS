package com.drmodi.jms.messageStructure;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RequestResponseDemoUsingJMS1x {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext initialContext = null;
		Connection connection = null;
		
		//---- Point to Point (P2P in action) ---
		
		// =====  Producer / Send Message =======
		
		//1. create initial context
		//2. look up for connection factory
		//3. create connection
		//4. create session
		//5. look up for destination/queue
		//6. create producer session object, provide destination name where it connects to
		//7. create message
		//8. send message
		
		// =====  Consumer / Received Message =======
		
		//9. create consumer
		//10. start connection to consume the message
		//11. receive message
		
		// At Finally block
		// close the initial context and connection.
		 
		
		
		
		try {
			initialContext = new InitialContext(); //1 Initial Context
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("myConnectionFactory");//2 Connection Factory
			connection = cf.createConnection(); //3 Connection - throw JMS exception, handled through catch
			Session session = connection.createSession();//4 Session
			
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");//5 - lookup for destination
			
			MessageProducer producer = session.createProducer(queue);//6 - Producer
			TextMessage message = session.createTextMessage("The more I sweat in peace, the less I bleed in war!");//7. create message
			
			
			long startTime = System.currentTimeMillis();
			System.out.println("CurrentTime : "+ startTime);
			//Send message
			TemporaryQueue resQueue = session.createTemporaryQueue();
			message.setJMSReplyTo(resQueue);
			producer.send(message); //8 send message
			System.out.println("Message being sent: "+message.getText());
			System.out.println("JMS ID : "+ message.getJMSMessageID());
			
			//Create MAP to correlate the ack message to request message
			Map<String,String> correlateMap = new HashMap<>();
			correlateMap.put(message.getJMSMessageID(), message.getText());
			
			
			
			//consumer part
			MessageConsumer consumer = session.createConsumer(queue);//9 create consumer
			connection.start(); //10 start connection
			
			//Received message
			TextMessage receivedMessage = (TextMessage) consumer.receive(1000);//11 - passing 5000 ms for connection
			
			System.out.println("JMS ID at Receiver End : " + receivedMessage.getJMSMessageID());
			System.out.println("CurrentTime 2: "+ (System.currentTimeMillis() - startTime));
			System.out.println("Received Message : "+receivedMessage.getText());
			
			MessageProducer ackProducer = session.createProducer(receivedMessage.getJMSReplyTo());
			TextMessage ackMessage = session.createTextMessage("This is ack message!");
			ackMessage.setJMSCorrelationID(receivedMessage.getJMSMessageID());
			ackProducer.send(ackMessage);
			
			MessageConsumer ackConsumer = session.createConsumer(resQueue);
			Message ackMessageAtProducer = ackConsumer.receive();
			System.out.println("Ack Message - Consume by First Producer : "+ ((TextMessage)ackMessageAtProducer).getText());
			System.out.println("Received Correlation ID : "+ackMessageAtProducer.getJMSCorrelationID());
			
			System.out.println("Find corrosponding request message from map: "
					+correlateMap.get(ackMessageAtProducer.getJMSCorrelationID()));
			
			
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			//close initial context
			if(initialContext!=null) {
				initialContext.close();
			}
			
			//close connection
			if(connection!=null) {
				connection.close();
			}
		}
		
		
	}
}
