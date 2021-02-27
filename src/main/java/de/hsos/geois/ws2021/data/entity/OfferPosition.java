package de.hsos.geois.ws2021.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class OfferPosition extends AbstractEntity {

	private String deviceTyp;
	private int quantity;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal price, totalPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Offer offer;
	
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public String getDeviceTyp() {
		return deviceTyp;
	}
	public void setDeviceTyp(String deviceTyp) {
		this.deviceTyp = deviceTyp;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
