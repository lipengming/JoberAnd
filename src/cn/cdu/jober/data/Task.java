package cn.cdu.jober.data;

import java.util.Map;

public class Task {
	private int taskID;// 任务编号
	private Map<String,Object> taskParam;//任务参数
	
	public Task(int id) {
		this.taskID = id;
	}
	
	public Task(int id, Map<String,Object> param) {
		this.taskID = id;
		this.taskParam = param;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Map<String,Object> getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map<String,Object> taskParam) {
		this.taskParam = taskParam;
	}
	
	
	
	
	
	
	
	public final static int SEARCH = 1;
	public final static int GET_RESUME = 2;
}
