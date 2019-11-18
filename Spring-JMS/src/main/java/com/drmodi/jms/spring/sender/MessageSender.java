package com.drmodi.jms.spring.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${springjms.myQueue}")
	String queueName;
	
	public void sendMsg(String message) {
		jmsTemplate.convertAndSend(queueName, message);
	}

}
