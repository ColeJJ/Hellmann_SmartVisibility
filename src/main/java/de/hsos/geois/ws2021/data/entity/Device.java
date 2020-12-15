package de.hsos.geois.ws2021.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class Device extends AbstractEntity {

	private String name;
	
	private String artNr;
	
	@Column(unique=true)
	private String serialNr;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal purchasePrice, salesPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtNr() {
		return artNr;
	}

	public void setArtNr(String artNr) {
		this.artNr = artNr;
	}

	public String getSerialNr() {
		return serialNr;
	}

	public void setSerialNr(String serialNr) {
		this.serialNr = serialNr;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	
	

}
