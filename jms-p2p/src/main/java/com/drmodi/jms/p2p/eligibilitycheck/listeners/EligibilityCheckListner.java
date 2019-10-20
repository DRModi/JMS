package com.drmodi.jms.p2p.eligibilitycheck.listeners;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.p2p.model.Patient;

public class EligibilityCheckListner implements MessageListener {
	
	//1. create listner
	//2. write logic
	//3. reply response back to responseQueue.

	@Override
	public void onMessage(Message message) {
		
		//receive the incoming message
		ObjectMessage objectMessage = (ObjectMessage) message;
		
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
		
			//create initial context and retrieve the response queue, Map message for preparing response.
			InitialContext context = new InitialContext();
			Queue responseQueue = (Queue) context.lookup("queue/replyQueue");
			MapMessage responseMsg = jmsContext.createMapMessage();
			
			//convert objectMessage to patient object
			Patient patient = (Patient) objectMessage.getObject();
			
			//Logic - Check the insurance is Atena or United Health, and based on Copay and Amount, Approve it
			String insuranceProvider = patient.getInsuranceProvider();
			
			System.out.println("***** Printing Patient Info "+"\n");
			System.out.println(patient.toString());
			
			if(insuranceProvider.equalsIgnoreCase("Atena") || insuranceProvider.equalsIgnoreCase("UnitedHealth")) {
				if(patient.getCopay()<=100 && patient.getAmountToBePaid()<=2500) {
					responseMsg.setBoolean("eligible", true);
					
				}else {
					responseMsg.setBoolean("eligible", false);
				}
			}
			
			
			//Create producer and send response back to reply queue
			JMSProducer producer = jmsContext.createProducer();
			producer.send(responseQueue, responseMsg);
			
			
			
		} catch (JMSException | NamingException e) {
			e.printStackTrace();
		} 
		
	}

}