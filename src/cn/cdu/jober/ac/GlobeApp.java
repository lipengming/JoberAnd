package cn.cdu.jober.ac;


import cn.cdu.jober.data.MyService;
import cn.cdu.jober.data.entity.User;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class GlobeApp extends Application{
	
	private final static String TAG = GlobeApp.class.getName();
	private static Context mContext;
	public static User user;

	@Override
	public void onCreate() {
		mContext = getApplicationContext();
		MyService.startService(this);
	}
	
	public static Context getContext(){
		return mContext ;
		
	}
	
	public static User getUser() {
		return user;
	}

	public void setUser(User user) {
		GlobeApp.user = user;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.i(TAG, "LOW MEMORY!!!!!");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.i(TAG, "TERMONATED!!!!!");
	}
	
}
