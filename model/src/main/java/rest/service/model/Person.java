package rest.service.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;
	private String firstName;
	private String secondName;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "person_address", 
			joinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "person_id", referencedColumnName = "id")})
	private List<Address> addresses;

	public Person(String firstName, String secondName) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.addresses = new ArrayList<>();
	}
	
	public Person(String firstName, String secondName, List<Address> addresses) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.addresses = addresses;
	}
	
	public Person() {}

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public String getSecondName() {
		return this.secondName;
	}

	@Override
	public String toString() {
		return String.format("[%d, %s, %s]", this.id, this.firstName, this.secondName);
	}

	public void setFirstName(String data1) {
		this.firstName = data1;
	}

	public void setSecondName(String data2) {
		this.secondName = data2;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void addAddress(Address address) {
		this.addresses.add(address);
	}
	
	public void clearAddress() {
		this.addresses.clear();
	}
}
