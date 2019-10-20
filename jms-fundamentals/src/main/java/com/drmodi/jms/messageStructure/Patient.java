package com.drmodi.jms.messageStructure;

import java.io.Serializable;

public class Patient implements Serializable {

	/**
	 * 
	 * it require to be Serializable, since it is being used in Object Type Message.
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	public Patient(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name of the Patient is : " + this.name + "\n" + "Id of the Patient is : " + this.id ;
	}
	
	
}
