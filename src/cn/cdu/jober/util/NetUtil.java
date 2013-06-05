package cn.cdu.jober.util;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	private static Context context;
	
	public NetUtil(){}
	public NetUtil(Context context){
		this.context = context; 
	}
	
	
	public static boolean checkNet(){
		
		boolean tag = false;
		try {
			
			ConnectivityManager cManager = (ConnectivityManager) context.
					getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cManager != null) {
				NetworkInfo info = cManager.getActiveNetworkInfo();
				if(info!=null&&info.isConnected()){
					if(info.getState()==NetworkInfo.State.CONNECTED){
						tag = true;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	} 
	
public static boolean checkNet(Context context){
		
		boolean tag = false;
		try {
			
			ConnectivityManager cManager = (ConnectivityManager) context.
					getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cManager != null) {
				NetworkInfo info = cManager.getActiveNetworkInfo();
				if(info!=null&&info.isConnected()){
					if(info.getState()==NetworkInfo.State.CONNECTED){
						tag = true;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	} 

	@SuppressWarnings("deprecation")
	public static BitmapDrawable getImageFromUrl(URL url){
		System.out.println("***--->"+url);
		BitmapDrawable bmap = null;
		try {
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			bmap = new BitmapDrawable(hc.getInputStream());
			hc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmap;
	}
}
