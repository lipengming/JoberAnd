package cn.cdu.jober;


import cn.cdu.jober.data.MyService;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	private RadioGroup group;
	private TabHost tabHost;
	public static final String TAB_HOME="tab_Home";
	public static final String TAB_MESSAGE="tab_mes";
	public static final String TAB_RESUME="tab_resume";
	public static final String TAB_SEARCH="tab_search";
	public static final String TAB_FOLLOW="tab_follow";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		add2Service();//把当前activity加到service
		
		group = (RadioGroup)findViewById(R.id.main_radio);
		tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec(TAB_HOME)
	                .setIndicator(TAB_HOME)
	                .setContent(new Intent(this,HomeActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_MESSAGE)
	                .setIndicator(TAB_MESSAGE)
	                .setContent(new Intent(this,MessageActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_RESUME)
	    		.setIndicator(TAB_RESUME)
	    		.setContent(new Intent(this,ResumeActivity.class)));
	    
	    tabHost.addTab(tabHost.newTabSpec(TAB_SEARCH)
	    		.setIndicator(TAB_SEARCH)
	    		.setContent(new Intent(this,SearchActivity.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_FOLLOW)
	    		.setIndicator(TAB_FOLLOW)
	    		.setContent(new Intent(this,FollowActivity.class)));
	    
	    group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_HOME);
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag(TAB_MESSAGE);
					break;
				case R.id.radio_button2:
					tabHost.setCurrentTabByTag(TAB_RESUME);
					break;
				case R.id.radio_button3:
					tabHost.setCurrentTabByTag(TAB_SEARCH);
					break;
				case R.id.radio_button4:
					tabHost.setCurrentTabByTag(TAB_FOLLOW);
					break;
				default:
					break;
				}
			}
		});
	}
	
	private void add2Service() {
		if(MyService.isRunning)
			MyService.allActivity.add(this);
		else{
			MyService.startService(this);
			MyService.allActivity.add(this);
		}
	}
}
