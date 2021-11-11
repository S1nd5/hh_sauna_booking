package com.sauna.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", nullable = false, updatable = false)
	private long id;
	
	// Name
	
	private String firstname;
	private String lastname;
	
    // Unique email per user
	@Column(name = "username", nullable = false, unique = true)
	private String email;
    
    @Column(name = "password", nullable = false)
	private String password;
    
    @Column(name = "usergroup", nullable = false)
	private String group;
    
    // Apartment and other details
    
	private String apartment;
	private String phonenumber;
	
	public User() {}
	
	public User(String email, String password, String group) {
		this.email = email;
		this.password = password;
		this.group = group;
	}

	public User(String firstname, String lastname, String email, String password, String group) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.group = group;
	}
	
	public User(String firstname, String lastname, String email, String password, String group, String apartment, String phonenumber) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.group = group;
		this.apartment = apartment;
		this.phonenumber = phonenumber;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}	
}
