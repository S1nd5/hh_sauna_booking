package com.sauna.booking.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterForm {
    @NotEmpty
    @Size(min=5, max=30)
    private String username = "";

    @NotEmpty
    @Size(min=7, max=30)
    private String password = "";

    @NotEmpty
    @Size(min=7, max=30)
    private String passwordCheck = "";

    @NotEmpty
    private String role = "USER";
    
    @NotEmpty
    @Size(min=5, max=30)
    private String firstname = "";
    
    @NotEmpty
    @Size(min=5, max=30)
    private String lastname = "";
    
    @Size(min=0, max=10)
    private String phonenumber = "";

	@Size(min=0, max=10)
    private String apartment = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
}