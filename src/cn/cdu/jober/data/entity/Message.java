package cn.cdu.jober.data.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Message {
	@Element
	private String type;
	@Element
	private String author;
	@Element
	private String authorid;
	@Element
	private String content;
	@Element
	private String at;
	@Element
	private Boolean hasread;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAt() {
		return at;
	}
	public void setAt(String at) {
		this.at = at;
	}
	public Boolean getHasread() {
		return hasread;
	}
	public void setHasread(Boolean hasread) {
		this.hasread = hasread;
	}
	public Message(String type, String author, String authorid, String content,
			String at, Boolean hasread) {
		super();
		this.type = type;
		this.author = author;
		this.authorid = authorid;
		this.content = content;
		this.at = at;
		this.hasread = hasread;
	}
	public Message() {}
	
}
