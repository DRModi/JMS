package com.drmodi.jms.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
	
	@JmsListener(destination = "${springjms.myQueue}")
	public void receiveMsg(String message) {
			System.out.println("***** Received Message is : "+message);
	}

}
