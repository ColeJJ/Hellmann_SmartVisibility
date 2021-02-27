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
	private String customerFirstName;
	private String customerLastName;
	private String companyName;
	private String customerEmail;
	private String customerAddress;
	private String customerPhone;
	
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

	public void setOfferpositions(Collection<OfferPosition> offerpositions) {
		this.offerpositions = offerpositions;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getOffNr() {
		return offNr;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
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
		return "Nr: " + getOffNr() + ", Kunde:" + getCustomerFirstName() + " " + getCustomerLastName();
	}
}
