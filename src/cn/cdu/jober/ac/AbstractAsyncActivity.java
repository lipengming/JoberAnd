package cn.cdu.jober.ac;

import cn.cdu.jober.data.MyService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.KeyEvent;


/**
 * 
 * @author Lee
 *
 */
public abstract class AbstractAsyncActivity extends Activity implements AsyncActivity {

	protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();

	private ProgressDialog progressDialog;

	private boolean destroyed = false;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.destroyed = true;
	}

	// ***************************************
	// Public methods
	// ***************************************
	public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (this.progressDialog != null && !this.destroyed) {
			this.progressDialog.dismiss();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyService.promptExitApp(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void add2Service() {
		if(MyService.isRunning)
			MyService.allActivity.add(this);
		else{
			MyService.startService(this);
			MyService.allActivity.add(this);
		}
	}
}
