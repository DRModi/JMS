package com.drmodi.jms.messagegrouping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageGroupingDemo {
	
	public static void main(String[] agms) throws NamingException, JMSException, InterruptedException {
	
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();
				JMSContext jmsCtxCons = cf.createContext()){
			
		
			
			//Create producer from JMSContext
			JMSProducer producer = jmsContext.createProducer();
			
			//Create multiple text messages
			int count = 10;
			TextMessage[] messages = new TextMessage[count];
			
			//Generate multiple message's with some name
			for(int i=0; i<count; i++) {
				messages[i] = jmsContext.createTextMessage("Group-0, with message "+i);
				//set the group id
				messages[i].setStringProperty("MyJMSXGroupID", "Group-0");
				producer.send(queue, messages[i]);
			}
			
			
			//wait till all messages are being sent
			Thread.sleep(2000);
			
			//Create Empty Map for sending to listener
			Map<String, String> receivedMessages = new ConcurrentHashMap<>();
			
			//create multiple consumer, to check messages are delivered to consumer 1
			//in this case we have multiple consumer that having the multiple listener, 
			//so sending and receiving from the same jmsContext will run into exception.
			
			
			JMSConsumer consumer = jmsCtxCons.createConsumer(queue);
			consumer.setMessageListener(new MessageGroupingListener("ConsListener_1", receivedMessages));
			
			JMSConsumer consumer2 = jmsCtxCons.createConsumer(queue);
			consumer2.setMessageListener(new MessageGroupingListener("ConsListener_2", receivedMessages));
			
			//throw exception in case of it goes to different consumer
			for(TextMessage msg : messages) {
				if (!receivedMessages.get(msg.getText()).equals("ConsListener_1")){
					throw new IllegalStateException(
							"Group Message "+msg.getText()+" has gone to different consumer!!" );
				}
			}
			
		}
	}

}



class MessageGroupingListener implements MessageListener {

	private final String name;
	private final Map<String, String> receivedMessages;
	
	public MessageGroupingListener(String name, Map<String,String> receivedMessages) {
		this.name = name;
		this.receivedMessages = receivedMessages;
	}
	
	@Override
	public void onMessage(Message message) {
		TextMessage txtMessage = (TextMessage) message;
		
		try {
			System.out.println("** Received TextMessage at Listener : "+txtMessage.getText());
			System.out.println("## Name of the Listener : "+name);
			//Store all the messages to validate it consume by only one consumer.
			receivedMessages.put(txtMessage.getText(), name);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
