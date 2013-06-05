package cn.cdu.jober;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.cdu.jober.ac.AbstractAsyncActivity;
import cn.cdu.jober.ac.IBaseActivity;
import cn.cdu.jober.ac.adapter.JobAdapter;
import cn.cdu.jober.ac.adapter.MsgAdapter;
import cn.cdu.jober.data.MyService;
import cn.cdu.jober.data.Task;
import cn.cdu.jober.data.entity.Job;

public class SearchActivity extends AbstractAsyncActivity implements IBaseActivity{
	
	AutoCompleteTextView autoComp;
	Button btnSearch;
	RadioButton radCompany,radPosition;
	RadioGroup radGroup;
	
	ListView listView;
	
	String condition = "",type = "company"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_search);
		
		listView = (ListView) findViewById(R.id.search_result_listView);
		registerForContextMenu(listView);//注册上下文菜单
		
		add2Service();
		initComponent();//初始化组件
		addListener();//时间监听
	}

	private void addListener() {
		radGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup gp, int checkid) {
				if(checkid == radCompany.getId())
					type = "campany";
				if(checkid == radPosition.getId())
					type = "position";
			}
		});
		
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				condition = autoComp.getText().toString();
				//搜索
				init();
			}
		});
	}

	private void initComponent() {
		autoComp = (AutoCompleteTextView) findViewById(R.id.seach_AutoCompleteTextView);
		btnSearch = (Button) findViewById(R.id.search_btSearch);
		radGroup = (RadioGroup) findViewById(R.id.search_radio_group);
		radCompany = (RadioButton) findViewById(R.id.search_radiobutton_company);
		radPosition = (RadioButton) findViewById(R.id.search_radiobutton_position);	
		
		autoComp.setText("java");
	}

	@Override
	public void init() {
		showLoadingProgressDialog();
		//任务初始化，以及发布
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("condition", condition);
		Task task = new Task(Task.SEARCH, map);
		MyService.allTask.add(task);
	}

	@Override
	public void refresh(Object... param) {
		dismissProgressDialog();
		
		List<Job> jobs = (List<Job>) param[0];
		
		if(jobs != null && jobs.size() > 0){
			JobAdapter adapter = new JobAdapter(this, jobs);
			listView.setAdapter(adapter);
		}else{
			Toast.makeText(this, "没有找到结果,您可以跟换关键字以及选择查询条件来搜索....", Toast.LENGTH_LONG).show();
		}
	}
	
	
}
