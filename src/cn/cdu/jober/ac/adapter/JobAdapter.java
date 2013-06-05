package cn.cdu.jober.ac.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.cdu.jober.R;
import cn.cdu.jober.ac.GlobeApp;
import cn.cdu.jober.data.entity.Job;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JobAdapter extends BaseAdapter{
	
	private List<Job> jobs = new ArrayList<Job>();
	private Context context;
	private final LayoutInflater layoutInflater;
	
	public JobAdapter(List<Job> jobs){
		this.context = GlobeApp.getContext();
		this.jobs = jobs;
		
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	public JobAdapter(Context context,List<Job> jobs){
		this.jobs = jobs;
		this.context = context;
		
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return this.jobs != null ? jobs.size() : 0;
	}

	@Override
	public Job getItem(int position) {
		return jobs == null? null : jobs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 增加更多的数据
	 * @param more
	 */
	public void addMoreData(List<Job> more) {
		this.jobs.addAll(more);
		// 更新list
		this.notifyDataSetChanged();
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.ac_home_item, parent, false);
		}
		
		Job job = this.getItem(position);
		if(job != null){
			//设置到item
			TextView title = (TextView) convertView.findViewById(R.id.home_job_title);
			TextView click_count = (TextView) convertView.findViewById(R.id.home_click_count);
			TextView content = (TextView) convertView.findViewById(R.id.home_job_content);
			
			title.setText(job.getTitle() != null ? job.getTitle() : "");
			click_count.setText(job.getClickCount() != null ? job.getClickCount()+"" : "");
			content.setText(job.getRequirements() != null ? job.getRequirements() : "");
		}
		return convertView;
	}

}
