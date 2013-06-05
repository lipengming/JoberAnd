package cn.cdu.jober;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cn.cdu.jober.ac.AbstractAsyncActivity;
import cn.cdu.jober.ac.GlobeApp;
import cn.cdu.jober.data.MyService;
import cn.cdu.jober.data.entity.Job;
import cn.cdu.jober.data.entity.LoginResult;
import cn.cdu.jober.data.entity.User;
import cn.cdu.jober.util.Constant;
import cn.cdu.jober.util.NetUtil;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AbstractAsyncActivity{
	EditText loginName,loginPwd;
	Button loginSub;
	String name,pwd;
	
	String validateFailed = "登陆信息验证失败，请重新输入！";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_login);
		
		add2Service();//把当前activity加到service
		addComponents();
		addListeners();
		
	}

	private void addListeners() {
		loginSub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validate()){
					if(NetUtil.checkNet(LoginActivity.this)){
						login();
					}else{
						MyService.AlertNetState(LoginActivity.this);
					}
				}else{
					validateFailed();
				}
			}
		});
	}

	
	private void addComponents() {
		loginSub = (Button)findViewById(R.id.login_submit);
		
		loginName = (EditText) findViewById(R.id.tx_login_username);
		loginPwd = (EditText) findViewById(R.id.tx_login_password);
		
		loginName.setText("aa@a.com");
		loginPwd.setText("aaa");
	}
	
	private void login() {
		new PostMessageTask().execute();
	}
	
	private void validateFailed(){
		Toast.makeText(this, validateFailed, Toast.LENGTH_LONG).show();
	}

	private void setValue(){
		name = loginName.getText().toString();
		pwd = loginPwd.getText().toString();
	}
	
	private boolean validate(){
		//设置值
		setValue();
		//非空|非''
		if(name == null || pwd == null || name.trim().equals("") || pwd.trim().equals("")){
			return false;
		}
		return true;
	}
	
	private class PostMessageTask extends AsyncTask<Void, Void, ResponseEntity<LoginResult>> {
		
		private String req_url = Constant.USER_LOGIN;
		
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
			//TODO 请求之前，构造查询参数
			req_url += "?email="+name+"&password="+pwd;
		}
		
		@Override
		protected ResponseEntity<LoginResult> doInBackground(Void... params) {
			
			try{
				// Add the gzip Accept-Encoding header to the request. This is not needed for Gingerbread and newer versions of Android
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());


				// Perform the HTTP GET request
				ResponseEntity<LoginResult> response = restTemplate.exchange(
						req_url, 
						HttpMethod.GET,
						new HttpEntity<Object>(requestHeaders), 
						LoginResult.class);

				return response;
			}catch (Exception e) {
				Log.e(TAG, e.getMessage(),e);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ResponseEntity<LoginResult> result) {
			dismissProgressDialog();
			System.out.println(result);
			resultHandler(result.getBody());
		}	
	}
	
	private void resultHandler(LoginResult result){
		if(result == null ){
			Toast.makeText(this, "程序异常...", Toast.LENGTH_LONG).show();
		}else{
			//当前用户登录成功
			User user = LoginResult.tran2User(result);
			if(result.getType().trim() != "" && result.getType().equals("success") && user != null){
				GlobeApp.user = user;
				startActivity(new Intent(LoginActivity.this, MainActivity.class));//ListMainActivity
				MyService.allActivity.remove(this);
				finish();
			}else{
				Toast.makeText(this, result.getContent() != null && !result.getContent().equals("") ? result.getContent():"", Toast.LENGTH_LONG).show();
			}
			
		}
	}
}
