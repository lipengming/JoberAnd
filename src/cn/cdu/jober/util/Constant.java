package cn.cdu.jober.util;

public final class Constant {
	
	public final static String BASE_URL = "http://jober.cloudfoundry.com/rest";//http://10.0.2.2:3000/rest
	
	public final static String JOBS_PAGING = BASE_URL + "/jobs";//参数：1、type(枚举) 2、page
	public final static String JOBS_SEARCH = BASE_URL + "/jobsearch";//参数：1、type(枚举) 2、page
	
	public final static String USERS_PAGING = "";
	public final static String USER_LOGIN = BASE_URL + "/user/login";
	
	public final static String MSG_ALL = BASE_URL + "/msgs";
	
	public final static String GET_RESUME = BASE_URL + "/resume";
}
