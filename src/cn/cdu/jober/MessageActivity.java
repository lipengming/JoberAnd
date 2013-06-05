package cn.cdu.jober;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cn.cdu.jober.ac.AbstractAsyncActivity;
import cn.cdu.jober.ac.GlobeApp;
import cn.cdu.jober.ac.adapter.MsgAdapter;
import cn.cdu.jober.data.entity.Message;
import cn.cdu.jober.util.Constant;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MessageActivity  extends AbstractAsyncActivity{
	
	ListView listView;
	View title_bar;
	Button btnfresh,btnAll;
	TextView tView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_message);
		
		listView = (ListView) findViewById(R.id.message_listview);
		registerForContextMenu(listView);//注册上下文菜单
		
		add2Service();
		initComponent();//初始化组件
		addListener();//时间监听
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//1、有网？
		
		//2、没网？从本地缓存读取，如果没有
		
		//提醒先联网
		
		//request the data
		new DownloadStatesTask().execute();
	}
	
	private void addListener() {
		btnfresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DownloadStatesTask().execute();
			}
		});
		
		btnAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	private void initComponent() {
		title_bar = findViewById(R.id.msg_title);
		tView = (TextView) title_bar.findViewById(R.id.textView);
		tView.setText("MSG");
		
		btnAll = (Button) title_bar.findViewById(R.id.title_bt_left); 
		btnfresh = (Button) title_bar.findViewById(R.id.title_bt_right);
		
		btnfresh.setBackgroundResource(R.drawable.title_reload);
		btnAll.setBackgroundResource(R.drawable.title_search_type);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		if (info.id != 0 && info.id != -1) {
			menu.setHeaderTitle("菜单");
			menu.add(1, 1, 1, "标为已读");
			menu.add(1, 2, 2, "回访");
			menu.add(1, 3, 3,  "回信");
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// 得到菜单项目的信息
		AdapterContextMenuInfo lm = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case 1:
			//看详情
			Toast.makeText(this, "标为已读", Toast.LENGTH_LONG).show();
			break;
		case 2:
			// 评论
			Toast.makeText(this, "回访", Toast.LENGTH_LONG).show();
			break;
		case 3:
			// 申请该职位
			Toast.makeText(this, "回信", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	private class DownloadStatesTask  extends AsyncTask<Void, Void, List<Message>> {
		
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}
		
		@Override
		protected List<Message> doInBackground(Void... param) {
			
			final String url = Constant.MSG_ALL + "/"+GlobeApp.getUser().getId();
			//System.out.println(url);
			
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			
			ResponseEntity<Message[]> responseEntity = restTemplate.exchange(
					url, 
					HttpMethod.GET, 
					requestEntity,
					Message[].class);
			
			return Arrays.asList(responseEntity.getBody());
		}
		
		@Override
		protected void onPostExecute(List<Message> result) {
			dismissProgressDialog();
			refreshStates(result);
		}
	}
	

	private void refreshStates(List<Message> result) {
		if(result != null && result.size() > 0){
			MsgAdapter adapter = new MsgAdapter(this, result);
			listView.setAdapter(adapter);
		}else{
			Toast.makeText(this, "数据加载失败！", Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}