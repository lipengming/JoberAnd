package cn.cdu.jober.data.entity;

import java.io.Serializable;

public class User implements Serializable{
	
	private String name;
	private String pwd;
	private String email;
	private String id ;
	
	public User(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User(String name, String pwd, String email, String id) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.id = id;
	}
	
}
