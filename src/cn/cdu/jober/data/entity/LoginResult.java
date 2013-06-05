package cn.cdu.jober.data.entity;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class LoginResult implements Serializable{
	
	@Element
	private String type;
	@Element
	private String content;
	@Element
	private String name;
	@Element
	private String pwd;
	@Element
	private String email;
	@Element
	private String id;
	
	/***
	 * bean
	 * @param result
	 * @return
	 */
	public static User tran2User(LoginResult result){
		if(result.getId() == null || result.getId().equals(""))
			return null;
		User user = new User();		
		user.setId(result.getId());
		user.setEmail(result.getEmail());
		user.setName(result.getName());
		user.setPwd(result.getPwd());
		return user;
	}
	
	public LoginResult(){}

	public LoginResult(String type, String content, String name, String pwd,
			String email, String id) {
		super();
		this.type = type;
		this.content = content;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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
	
}