package com.drmodi.jms.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drmodi.jms.spring.sender.MessageSender;

@SpringBootTest
class SpringJmsApplicationTests {

	@Autowired
	MessageSender msgSender;
	
	
	@Test
	void sendTheMessage() {
		msgSender.sendMsg("Hello from Spring JMS !!!");
	}

}
