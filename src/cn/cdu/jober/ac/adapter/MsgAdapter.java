package cn.cdu.jober.ac.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.cdu.jober.R;
import cn.cdu.jober.ac.GlobeApp;
import cn.cdu.jober.data.entity.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter{
	
	private List<Message> Messages = new ArrayList<Message>();
	private Context context;
	private final LayoutInflater layoutInflater;
	
	public MsgAdapter(List<Message> Messages){
		this.context = GlobeApp.getContext();
		this.Messages = Messages;
		
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	public MsgAdapter(Context context,List<Message> Messages){
		this.Messages = Messages;
		this.context = context;
		
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return this.Messages != null ? Messages.size() : 0;
	}

	@Override
	public Message getItem(int position) {
		return Messages == null? null : Messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 增加更多的数据
	 * @param more
	 */
	public void addMoreData(List<Message> more) {
		this.Messages.addAll(more);
		// 更新list
		this.notifyDataSetChanged();
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.ac_message_item, parent, false);
		}
		
		Message Message = this.getItem(position);
		if(Message != null){
			//设置到item
			TextView author = (TextView) convertView.findViewById(R.id.msg_author);
			TextView state = (TextView) convertView.findViewById(R.id.msg_state_now);
			TextView at = (TextView) convertView.findViewById(R.id.msg_create_at);
			TextView content = (TextView) convertView.findViewById(R.id.msg_content);
			
			author.setText(Message.getAuthor() != null ? Message.getAuthor() : "");
			state.setText(Message.getHasread() ? "已读": "未读");
			content.setText(Message.getContent() != null ? Message.getContent() : "");
			at.setText(Message.getAt() != null ? Message.getAt() : "");
		}
		return convertView;
	}

}
