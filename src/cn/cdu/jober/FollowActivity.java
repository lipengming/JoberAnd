package cn.cdu.jober;

import android.os.Bundle;
import cn.cdu.jober.ac.AbstractAsyncActivity;

public class FollowActivity extends AbstractAsyncActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_follow);
		add2Service();
		
	}
}