package cn.cdu.jober;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import cn.cdu.jober.ac.AbstractAsyncActivity;
import cn.cdu.jober.data.entity.SearchType;

public class SearchTypeDialog extends AbstractAsyncActivity{
	
	private Button type_news,type_clickcount,type_ask;
	
	private SearchType type = SearchType.NEWS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		setContentView(R.layout.ac_home_search_type);
		add2Service();
		
		Intent it = getIntent();
		type = (SearchType) it.getExtras().getSerializable("type");
		
		initCompnent();
		setListener();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//点击空处，当前的dialog也会关闭
		
		Intent intent = new Intent();
		intent.putExtra("type",type);
		setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
		finish();//此处一定要调用finish()方法
		return true;
		
	}
	
	private void setListener() {
		type_news.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				type = SearchType.NEWS;
				returnView(type);
			}
		});
		type_clickcount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				type = SearchType.CLICKCOUT;
				returnView(type);
			}
		});			
					
		type_ask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				type = SearchType.ASKCOUNT;
				returnView(type);
			}
		});
	}
	
	private void returnView(SearchType type){
		Intent intent = new Intent();
		intent.putExtra("type", type);
		setResult(RESULT_OK, intent); // intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
		finish();// 此处一定要调用finish()方法
	}
	
	private void initCompnent() {
		
		type_news = (Button) findViewById(R.id.type_news);
		type_clickcount = (Button) findViewById(R.id.type_clickcount);
		type_ask = (Button) findViewById(R.id.type_ask);
		
		switch (type) {
			case NEWS:
				type_news.setBackgroundColor(Color.BLUE);
				break;
			case CLICKCOUT:
				type_clickcount.setBackgroundColor(Color.BLUE);
				break;
			case ASKCOUNT:
				type_ask.setBackgroundColor(Color.BLUE);
				break;
			default:
				type_news.setBackgroundColor(Color.BLUE);
			break;
		}
	}

	class MyDialog extends AlertDialog {
		public MyDialog(Context context)
		{
			super(context);
		}
	}
}
