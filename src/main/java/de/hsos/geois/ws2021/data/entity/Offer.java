package de.hsos.geois.ws2021.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class Offer extends AbstractEntity {

	private String customerNr;
	private String customerName;
	private String customerAddresse;
	private String ordNr;
	
	@Column(unique=true)
	private String offNr;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal purchasePrice, salesPrice;

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

	public String getCustomerAddress() {
		return customerAddresse;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddresse = customerAddress;
	}

	public String getOrderNr() {
		return ordNr;
	}

	public void setOrderNr(String orderNr) {
		this.ordNr = orderNr;
	}

	public String getOffNr() {
		return offNr;
	}
}
