package de.hsos.geois.ws2021.data.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hsos.geois.ws2021.data.AbstractEntity;

@Entity
public class Offer extends AbstractEntity {

	private String offNr;
	private String customerNr;
	private String customerName;
	private String customerAddress;
	
	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = false)
	private Collection<OfferPosition> offerpositions;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Offer() {
		this.offerpositions = new ArrayList<OfferPosition>();
	}
	
	public Collection<OfferPosition> getOfferpositions() {
		return offerpositions;
	}

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
	
	public boolean addOfferPosition(OfferPosition offerposition) {
		return getOfferpositions().add(offerposition);
	}
	
	public boolean removeOfferPosition(OfferPosition offerposition) {
		return getOfferpositions().remove(offerposition);
	}
	
	public String toString() {
		return "Nr: " + getOffNr() + ", Kunde:" + getCustomerName();
	}
}
