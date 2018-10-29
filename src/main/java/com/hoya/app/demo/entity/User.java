package com.hoya.app.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {
	
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

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String name;
	
	@Column(name="password")
	@JsonProperty("password")//别名
	private String password;
	
	@JsonIgnore
	private String ignore;
}
