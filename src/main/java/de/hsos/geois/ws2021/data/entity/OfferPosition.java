package de.hsos.geois.ws2021.data.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class OfferPosition extends AbstractEntity {

	private String offPoNr;
	private String offerPoName;
	private String quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Offer offer;
	
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public String getOffPoNr() {
		return offPoNr;
	}
	public void setOffPoNr(String offPoNr) {
		this.offPoNr = offPoNr;
	}
	public String getOfferPoName() {
		return offerPoName;
	}
	public void setOfferPoName(String offerPoName) {
		this.offerPoName = offerPoName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
