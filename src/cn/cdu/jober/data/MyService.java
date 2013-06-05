package cn.cdu.jober.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import cn.cdu.jober.ResumeActivity;
import cn.cdu.jober.SearchActivity;
import cn.cdu.jober.ac.GlobeApp;
import cn.cdu.jober.data.entity.Job;
import cn.cdu.jober.data.entity.LoginResult;
import cn.cdu.jober.data.entity.Resume;
import cn.cdu.jober.util.Constant;

public class MyService extends Service implements Runnable {
	protected static final String TAG = MyService.class.getSimpleName();

	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();
	public static ArrayList<Task> allTask = new ArrayList<Task>();
	public static boolean isRunning = false;

	private Handler handler = new Handler() {
		Activity current;
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Task.SEARCH:
				current = MyService.getActivityByName(SearchActivity.class.getName());
				((SearchActivity)current).refresh(msg.obj);
				break;
			case Task.GET_RESUME:
				current = MyService.getActivityByName(ResumeActivity.class.getName());
				((ResumeActivity)current).refresh(msg.obj);
				break;
			default:
				break;
			}
		}

	};


	public void doTask(Task task) {
		Message msg = new Message();
		msg.what = task.getTaskID();
		Map dataMap = task.getTaskParam();
		String req_url = "";
		try {
			switch (task.getTaskID()) {
			case Task.SEARCH:
				
				String type = (String) dataMap.get("type");
				String condition = (String) dataMap.get("condition");
				req_url = Constant.BASE_URL + "?type="+type+"&condition="+condition;
				
				List<Job> jobs = getRemoteJobs(req_url);
				msg.obj = jobs;
				
				break;
			case Task.GET_RESUME:
				
				String uid = GlobeApp.getUser().getId();
				req_url = Constant.GET_RESUME + "/"+uid;
				List<Resume> resume = getResumeData(req_url);
				msg.obj = resume;
				
				break;	
				
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.sendMessage(msg);
		this.allTask.remove(task);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		isRunning = true;
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		while (isRunning) {
			Task lastTask = null;
			synchronized (allTask) {
				if (allTask.size() > 0) {
					lastTask = allTask.get(0);
					doTask(lastTask);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Activity getActivityByName(String name) {
		for (Activity ac : allActivity) {
			if (ac.getClass().getName().equals(name)) {
				return ac;
			}
		}
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}

	// add a new task
	public static void newTask(Task task) {
		allTask.add(task);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// /////////////////////////////////
	// ======退出程序
	// /////////////////////////////////
	public static void exitApp(Activity context) {// 退出所有Activity
		for (int i = 0; i < allActivity.size(); i++) {
			((Activity) allActivity.get(i)).finish();// 销毁所有activity
		}
		allActivity.clear();// 释放list
		// 退出Service
		context.stopService(new Intent(MyService.class.getName()));
	}

	// 提示是否退出应用程序
	public static void promptExitApp(final Activity context) {
		// 创建对话框
		AlertDialog.Builder ab = new AlertDialog.Builder(context);
		LayoutInflater li = LayoutInflater.from(context);
		View msgview = li.inflate(android.R.layout.select_dialog_item, null);
		ab.setView(msgview);

		// 设定对话框显示的内容
		ab.setPositiveButton("确定退出", new OnClickListener() {
			public void onClick(DialogInterface dv, int id) {
				// TODO Auto-generated method stub
				dv.dismiss();
				exitApp(context);// 退出整个应用
			}
		});
		ab.setNegativeButton("返回", null);
		ab.show();
	}

	// 提示网络错误
	public static void AlertNetState(final Activity context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("网络出错啦！");
		builder.setMessage("连接不到网络……");
		builder.setNegativeButton("退出程序", new OnClickListener() {

			public void onClick(DialogInterface b, int arg1) {
				b.cancel();
				exitApp(context);
			}
		});
		builder.setPositiveButton("设置网络", new OnClickListener() {

			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				context.startActivityForResult(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
			}
		});
		builder.show();
	}

	/***
	 * 开启服务
	 * 
	 * @param context
	 */
	public static void startService(Context context) {
		MyService.isRunning = true;
		Intent it = new Intent(context, MyService.class);
		context.startService(it);
	}
	
	/**
	 * 获取远程职位数据
	 * @param url
	 * @return
	 */
	private List<Job> getRemoteJobs(String url){
		
		System.out.println(url);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		
		ResponseEntity<Job[]> responseEntity = restTemplate.exchange(
				url, 
				HttpMethod.GET, 
				requestEntity,
				Job[].class);
		
		return Arrays.asList(responseEntity.getBody());
	}
	
	/**
	 * 获取远程简历数据
	 * @param req_url
	 * @return
	 */
	private List<Resume> getResumeData(String req_url) {
		
		System.out.println(req_url);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		
		ResponseEntity<Resume[]> responseEntity = restTemplate.exchange(
				req_url, 
				HttpMethod.GET, 
				requestEntity,
				Resume[].class);
		
		return Arrays.asList(responseEntity.getBody());
	}
}
