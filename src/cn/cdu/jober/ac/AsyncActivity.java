package cn.cdu.jober.ac;

/**
 * 
 * @author Lee
 *
 */
public interface AsyncActivity {

	public void showLoadingProgressDialog();

	public void showProgressDialog(CharSequence message);

	public void dismissProgressDialog();

}
