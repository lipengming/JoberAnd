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

import android.content.Intent;
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
import cn.cdu.jober.ac.AbstractAsyncActivity;
import cn.cdu.jober.ac.adapter.JobAdapter;
import cn.cdu.jober.data.MyService;
import cn.cdu.jober.data.entity.Job;
import cn.cdu.jober.data.entity.SearchType;
import cn.cdu.jober.util.Constant;


public class HomeActivity extends AbstractAsyncActivity{
	
	private int page = 1;
	private boolean hasLoad = false; 
	private SearchType type = SearchType.NEWS;
	
	ListView listView;
	View title_bar;
	Button btnfresh,btntype;
	TextView tView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);
			
		listView = (ListView) findViewById(R.id.home_listview);
		registerForContextMenu(listView);//注册上下文菜单
		
		add2Service();//把当前activity加到service
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
				page++;
				new DownloadStatesTask().execute();
			}
		});
		
		btntype.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//显示搜索类型弹出框
				Intent intent = new Intent();
				intent.putExtra("type", type);
				intent.setClass(HomeActivity.this,SearchTypeDialog.class);
				
				startActivityForResult(intent, 0);
			}
		});
		
	}
	
	private void refreshStates(List<Job> result) {
		if(result != null && result.size() > 0){
			if(hasLoad){
				((JobAdapter) listView.getAdapter()).addMoreData(result);
			}else{
				JobAdapter adapter = new JobAdapter(this, result);
				listView.setAdapter(adapter);
				hasLoad = true;
			}
		}else{
			Toast.makeText(this, "数据加载失败！", Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	private void initComponent() {
		title_bar = findViewById(R.id.home_title_bar);
		tView = (TextView) title_bar.findViewById(R.id.textView);
		tView.setText("首页");
		
		btntype = (Button) title_bar.findViewById(R.id.title_bt_left); 
		btnfresh = (Button) title_bar.findViewById(R.id.title_bt_right);
		
		btnfresh.setBackgroundResource(R.drawable.title_reload);
		btntype.setBackgroundResource(R.drawable.title_search_type);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		if (info.id != 0 && info.id != -1) {
			menu.setHeaderTitle("我要去...");
			menu.add(1, 1, 1, "查看详细");
			menu.add(1, 2, 2, "评论");
			menu.add(1, 3, 3,  "申请该职位");
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
			Toast.makeText(this, "看详情。。！", Toast.LENGTH_LONG).show();
			break;
		case 2:
			// 评论
			Toast.makeText(this, "评论！", Toast.LENGTH_LONG).show();
			break;
		case 3:
			// 申请该职位
			Toast.makeText(this, "申请该职位！", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	private class DownloadStatesTask  extends AsyncTask<Void, Void, List<Job>> {
		
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}
		
		@Override
		protected List<Job> doInBackground(Void... param) {
			
			final String url = Constant.JOBS_PAGING + "?type="+ translate(type)+"&page="+page;
			//System.out.println(url);
			
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
		
		@Override
		protected void onPostExecute(List<Job> result) {
			dismissProgressDialog();
			refreshStates(result);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Bundle b=data.getExtras();  //data为B中回传的Intent
			type=(SearchType) b.getSerializable("type");
			
			//清空数据,重新加载
			page = 1;
			hasLoad = false;
			new DownloadStatesTask().execute();
			
            break;
        default: break;
        }
	}
	
	private String translate(SearchType type){
		String result = "news";
		switch (type) {
		case NEWS:
			result = "news";
			break;
		case ASKCOUNT:
			result = "ask";
			break;
		case CLICKCOUT:
			result = "click";
			break;
		default:
			break;
		}
		return result;
	}
}
