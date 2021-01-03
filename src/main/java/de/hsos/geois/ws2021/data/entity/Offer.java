package de.hsos.geois.ws2021.data.entity;

import javax.persistence.Entity;
import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class Offer extends AbstractEntity {

	private String offNr;
	private String customerNr;
	private String customerName;
	private String customerAddress;

	public String getCustomerNr() {
		return customerNr;
	}

	public void setCustomerNr(String customerNr) {
		this.customerNr = customerNr;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOffNr() {
		return offNr;
	}

	public void setOffNr(String offNr) {
		this.offNr = offNr;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
}
