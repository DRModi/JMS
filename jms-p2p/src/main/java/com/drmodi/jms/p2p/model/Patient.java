package com.drmodi.jms.p2p.model;

import java.io.Serializable;

public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String name;
	private String insuranceProvider;
	private double copay;
	private double amountToBePaid;
	
	public Patient(int id, String name, String insuranceProvider, double copay, double amountToBePaid) {
		super();
		this.id = id;
		this.name = name;
		this.insuranceProvider = insuranceProvider;
		this.copay = copay;
		this.amountToBePaid = amountToBePaid;
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
	public String getInsuranceProvider() {
		return insuranceProvider;
	}
	public void setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}
	public double getCopay() {
		return copay;
	}
	public void setCopay(double copay) {
		this.copay = copay;
	}
	public double getAmountToBePaid() {
		return amountToBePaid;
	}
	public void setAmountToBePaid(double amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", insuranceProvider=" + insuranceProvider + ", copay=" + copay
				+ ", amountToBePaid=" + amountToBePaid + "]";
	}
	
	
}
