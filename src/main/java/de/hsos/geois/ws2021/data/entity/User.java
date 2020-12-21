package de.hsos.geois.ws2021.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import de.hsos.geois.ws2021.data.AbstractEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User extends AbstractEntity {

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDate dateOfBirth;
	private String occupation;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<Device> devices;
	
	public User() {
		this.devices = new ArrayList<Device>();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Collection<Device> getDevices() {
		return devices;
	}
	
	public boolean addDevice(Device device) {
		return getDevices().add(device);
	}
	
	public boolean removeDevice(Device device) {
		return getDevices().remove(device);
	}
	
	public String toString()
	{
		return getLastName() + ", " + getFirstName();
	}
	

}
