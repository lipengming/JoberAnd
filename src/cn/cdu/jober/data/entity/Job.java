package cn.cdu.jober.data.entity;

import java.io.Serializable;
import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("serial")
@Root
public class Job implements Serializable{
	private static final long serialVersionUID = -3439490642759384805L;
	
	@Element
	private String title;
	@Element
	private Integer clickCount;
	@Element
	private String requirements;
//	private String author_id;
//
//	private String position;
//	private String company;
	
	
//	private Integer number;
	
//	private Integer ask_count;
//	private Integer coment_count;

//	private Date create_at;
//	private Date update_at;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public Job(){}
	public Job(String title, Integer clickCount, String requirements) {
		super();
		this.title = title;
		this.clickCount = clickCount;
		this.requirements = requirements;
	}	
	
}
