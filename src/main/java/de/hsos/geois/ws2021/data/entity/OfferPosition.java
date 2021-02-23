package de.hsos.geois.ws2021.data.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class OfferPosition extends AbstractEntity {

	private String deviceTyp;
	private String quantity;
	private String price;
	
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
