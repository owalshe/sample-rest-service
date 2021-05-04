package rest.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@Column(name = "id")
    @GeneratedValue()
	private Long id;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	
	@ManyToOne()
	@JoinTable(name = "person_address", 
			joinColumns = { @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false,
	                  updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id")})
	private Person person;
	
	public Address(String street, String city, String state, String postalCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}
	
	public Address() {}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPostalCode() {
		return this.postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@Override
	public String toString() {
		return String.format("[%d, %s, %s, %s, %s]", this.id, this.street, this.city, this.state, this.postalCode);
	}
}
