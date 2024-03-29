package com.drmodi.jms.p2p.clinicals;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.p2p.model.Patient;

public class ClinicalsApp {
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		// First step, unless working with Application server which will inject InitialContext automatically.
		InitialContext initialContext = new InitialContext();
		Queue queue = (Queue) initialContext.lookup("queue/requestQueue");
		Queue resQueue = (Queue) initialContext.lookup("queue/replyQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
			
			JMSProducer producer = jmsContext.createProducer();
			
			//create patient info
			Patient patient = new Patient(123, "John Smith", "Atena",50d, 750d );
			
			//create object msg and set the patient
			ObjectMessage msg = jmsContext.createObjectMessage();
			msg.setObject(patient);
			
			//send the message, using producer
			producer.send(queue, msg);
			
			//create the consumer and consume the message from ReplyQueue
			JMSConsumer consumer = jmsContext.createConsumer(resQueue);
			MapMessage message = (MapMessage) consumer.receive(30000);
			System.out.println("Is Patient Eligible : "+message.getBoolean("eligible"));
			
		}
		
	}

}
