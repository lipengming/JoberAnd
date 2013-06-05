package cn.cdu.jober.data.entity;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
public class Resume implements Serializable{
	@Element
	private String author;//求职人id
	@Element
	private String name;//姓名
	@Element
	private String email;//邮箱
	@Element
	private String phoneNo;//电话号码
	@Element
	private String workyears;//工作年限
	@Element
	private String workhistory;//工作经历
	@Element
	private String edubackend;//学历
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getWorkyears() {
		return workyears;
	}
	public void setWorkyears(String workyears) {
		this.workyears = workyears;
	}
	public String getWorkhistory() {
		return workhistory;
	}
	public void setWorkhistory(String workhistory) {
		this.workhistory = workhistory;
	}
	public String getEdubackend() {
		return edubackend;
	}
	public void setEdubackend(String edubackend) {
		this.edubackend = edubackend;
	}
	public Resume(String author, String name, String email, String phoneNo,
			String workyears, String workhistory, String edubackend) {
		super();
		this.author = author;
		this.name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.workyears = workyears;
		this.workhistory = workhistory;
		this.edubackend = edubackend;
	}
	
	public Resume() {	}
}
