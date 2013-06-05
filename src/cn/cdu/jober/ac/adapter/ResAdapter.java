package cn.cdu.jober.ac.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.cdu.jober.R;
import cn.cdu.jober.ac.GlobeApp;
import cn.cdu.jober.data.entity.Resume;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResAdapter extends BaseAdapter{
	private List<Resume> Resumes = new ArrayList<Resume>();
	private Context context;
	private final LayoutInflater layoutInflater;
	
	public ResAdapter(List<Resume> Resumes){
		this.context = GlobeApp.getContext();
		this.Resumes = Resumes;
		
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	public ResAdapter(Context context,List<Resume> Resumes){
		this.Resumes = Resumes;
		this.context = context;
		
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return this.Resumes != null ? Resumes.size() : 0;
	}

	@Override
	public Resume getItem(int position) {
		return Resumes == null? null : Resumes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 增加更多的数据
	 * @param more
	 */
	public void addMoreData(List<Resume> more) {
		this.Resumes.addAll(more);
		// 更新list
		this.notifyDataSetChanged();
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.ac_home_item, parent, false);
		}
		
		Resume Resume = this.getItem(position);
		if(Resume != null){
			//设置到item
			TextView name = (TextView) convertView.findViewById(R.id.home_job_title);
			TextView phoneNo = (TextView) convertView.findViewById(R.id.home_click_count);
			TextView edubackend = (TextView) convertView.findViewById(R.id.home_job_content);
			
			name.setText(Resume.getName() != null ? Resume.getName() : "");
			phoneNo.setText(Resume.getPhoneNo() != null ? Resume.getPhoneNo()+"" : "");
			edubackend.setText(Resume.getEdubackend() != null ? Resume.getEdubackend() : "");
		}
		return convertView;
	}
}
