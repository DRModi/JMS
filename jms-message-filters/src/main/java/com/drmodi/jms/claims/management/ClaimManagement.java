package com.drmodi.jms.claims.management;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.drmodi.jms.claims.model.Claim;

public class ClaimManagement {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/claimsQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();

			// create object message, so that properties can be assigned, and based on that filtering can be done.
			ObjectMessage objMsg = jmsContext.createObjectMessage();

			Claim claim = new Claim(101, "JayMa", "OB-GYN", "Atena", 1050.50);

			// set claim object to object message
			objMsg.setObject(claim);
			
			//set object property 
				//1. based on hospitalID 
			//objMsg.setIntProperty("hospitalID", claim.getHospitalId());
				//2. based on Amount
			//objMsg.setDoubleProperty("claimAmountMax5000", claim.getClaimAmount());
				//3. based on doctor name
			//objMsg.setStringProperty("docName", claim.getDoctorName());
				//4. based on doctor type
			objMsg.setStringProperty("docType", claim.getDoctorType());

			
			producer.send(queue, objMsg);

			// create consumer with selector (to define the filter)
			// jmsContext.createConsumer(queue, messageSelector)
				//1. Using Equal (=) operator
			//JMSConsumer consumer = jmsContext.createConsumer(queue, "hospitalID=101");

				//2. Using BETWEEN operator
		    //JMSConsumer consumer = jmsContext.createConsumer(queue, "claimAmountMax5000 BETWEEN 0 AND 5000");
				
				//3. Using Like operator
			//JMSConsumer consumer = jmsContext.createConsumer(queue, "docName LIKE 'J%'");
			//JMSConsumer consumer = jmsContext.createConsumer(queue, "docName LIKE 'Ja_'");
			
				//4. Using IN operator
			//JMSConsumer consumer = jmsContext.createConsumer(queue, "docType IN ('NURO','GENSURGEON','OB-GYN')");
			
				//5. Test with header property priority (default is 4 priority, will go through since OB-GYN is not
				// there in option, but due to OR and default 4 it will go through
			JMSConsumer consumer = jmsContext.createConsumer(queue, "docType IN ('NURO','GENSURGEON') OR JMSPriority BETWEEN 4 AND 6");
			
			
			//Print the received Message
			Claim body = consumer.receiveBody(Claim.class);
			System.out.println(body.toString());

		}
	}

}
