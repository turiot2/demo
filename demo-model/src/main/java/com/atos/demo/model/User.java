package com.atos.demo.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * User entity with validation annotations.
 * No surrogate key, the login field is used as primary key.
 */
@Entity
public class User implements Serializable {
	   
	private static final long serialVersionUID = -901296932597952927L;
	
	@Id
	@NotEmpty(message = "User's login is mandatory") // TODO put in properties
    @Pattern(regexp = "[0-9;a-z;A-Z]*", message = "User's login is not correct")
	private String login;
	private String firstName;
	private String lastName;
	@Email(message = "User's email invalid")
	private String email;
	@NotNull(message = "User's birthDate is mandatory")
	@BirthDateConstraint(message = "User is not 18 yo")
	private Date birthDate;
	@NotEmpty(message = "User's country is mandatory")
    @Pattern(regexp = "FR", message = "User's country is not France")
	private String countryCode; // think Locale.IsoCountryCode.PART1_ALPHA2

	public User() {
	}   
	public User(String login) {
		setLogin(login);
	}
	public User(String login, Date birthDate, String countryCode) {
		setLogin(login);
		setBirthDate(birthDate);
		setCountryCode(countryCode);
	}
	
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}   
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}   
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String LastName) {
		this.lastName = LastName;
	}   
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}   
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthDate=" + birthDate + ", countryCode=" + countryCode + "]";
	}
   
}
