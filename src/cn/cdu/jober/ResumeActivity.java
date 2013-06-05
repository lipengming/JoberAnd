package cn.cdu.jober;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import cn.cdu.jober.ac.AbstractAsyncActivity;
import cn.cdu.jober.ac.IBaseActivity;
import cn.cdu.jober.ac.adapter.ResAdapter;
import cn.cdu.jober.data.MyService;
import cn.cdu.jober.data.Task;
import cn.cdu.jober.data.entity.Resume;

public class ResumeActivity extends AbstractAsyncActivity implements IBaseActivity{
		
	EditText txtName,txtEamil,txtPhoneNo,txtWorkYears,txtEdu,txtHistory;
	Button btnSubmit,btnBack;
	ListView listView;
	List<Resume> currentData = null;
	LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_resume);
		
		listView = (ListView) findViewById(R.id.resume_listview);
		registerForContextMenu(listView);//注册上下文菜单
		
		add2Service();
		initComponent();//初始化组件
		addListener();//时间监听
	}

	@Override
	protected void onStart() {
		super.onStart();
		init();
	}
	
	private void addListener() {
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switchView(1);
			}
		});
	}

	private void initComponent() {
		layout = (LinearLayout) findViewById(R.id.resume_detail_view);
		
		txtName = (EditText) findViewById(R.id.resume_name);
		txtEamil= (EditText) findViewById(R.id.resume_emailbox);
		txtPhoneNo = (EditText) findViewById(R.id.resume_phoneno);
		txtWorkYears = (EditText) findViewById(R.id.resume_workyears);
		txtEdu = (EditText) findViewById(R.id.resume_edubackend);
		txtHistory = (EditText) findViewById(R.id.resume_workhistory);
		
		btnSubmit = (Button) findViewById(R.id.resume_submit);
		btnBack = (Button) findViewById(R.id.resume_back_list);
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;

		if (info.id != -1) {
			menu.setHeaderTitle("简历管理...");
			menu.add(1, 1, 1, "查看详细");
			menu.add(1, 2, 2, "删除");
			menu.add(1, 3, 3,  "更新");
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		// 得到菜单项目的信息
		AdapterContextMenuInfo lm = (AdapterContextMenuInfo) item.getMenuInfo();
		Resume resu = currentData.get((int)lm.id);
		
		switch (item.getItemId()) {
		case 1:
			//看详情
			Toast.makeText(this, lm.id + "看详情。。！", Toast.LENGTH_LONG).show();
			refreshUI(resu);
			switchView(0);
			
			break;
		case 2:
			// 评论
			Toast.makeText(this, lm.id + "删除！", Toast.LENGTH_LONG).show();
			break;
		case 3:
			// 申请该职位
			Toast.makeText(this, lm.id + "更新！", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void init() {
		showLoadingProgressDialog();
		//任务初始化，以及发布
		Task task = new Task(Task.GET_RESUME);
		MyService.allTask.add(task);
	}

	@Override
	public void refresh(Object... param) {
		dismissProgressDialog();
		
		List<Resume> resumes = (List<Resume>) param[0];
		if(resumes == null || resumes.size() < 1){
			Toast.makeText(this, "您还没有存档的简历...", Toast.LENGTH_LONG).show();
		}else{
			//显示list
			ResAdapter resAdapter = new ResAdapter(this, resumes);
			listView.setAdapter(resAdapter);
			
			//显示细节
			currentData = resumes;
		}
		
	}

	private void refreshUI(Resume resume) {
		txtName.setText(resume.getName());
		txtEamil.setText(resume.getEmail());
		txtPhoneNo.setText(resume.getPhoneNo());
		txtWorkYears.setText(resume.getWorkyears());
		txtEdu.setText(resume.getEdubackend());
		txtHistory.setText(resume.getWorkhistory());
	}
	
	/**
	 * !1、list--->layout
	 * 1、layout--->list
	 * @param current
	 */
	private void switchView(int current){
		if(current == 1){
			listView.setVisibility(View.VISIBLE);
			layout.setVisibility(View.GONE);
		}else{
			layout.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}
	}
	
}
