package com.hoya.app.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User implements Serializable {

	public User() {
		
	}

	public User(String name, String password, String ignore) {
		this.name = name;
		this.password = password;
		this.ignore = ignore;
	}
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY) 非自增字段
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	public String getIgnore() {
		return ignore;
	}

	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	
	@Override
	public String toString() {
		return "username: " + name + " password: " + password + " ignore: " + ignore;
	}
	
	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String name;
	
	@Column(name="password")
	@JsonProperty("password")//别名
	private String password;
	
	@JsonIgnore
	private String ignore;

	private static final long serialVersionUID = 1L;
}
