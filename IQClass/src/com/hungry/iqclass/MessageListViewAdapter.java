package com.hungry.iqclass;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hungry.iqclass.R;
import com.hungry.iqclass.ic.Message;

public class MessageListViewAdapter extends BaseAdapter{

	private Context context = null;
	private List<Message> data = new ArrayList<Message>();
	public MessageListViewAdapter(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			
			convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.listview_cell_message, null);
			convertView.setTag(new ListCell((LinearLayout)convertView.findViewById(R.id.llCellTab)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llCellTab = lc.getllCellTab();
		Message msg = (Message) getItem(position);
		((TextView)llCellTab.findViewById(R.id.tvCelllabel)).setText(msg.getlessonName());
		((TextView)llCellTab.findViewById(R.id.tvMsg)).setText(msg.getMsg());
		final int itemPosition = position;
		((TextView)llCellTab.findViewById(R.id.btToDel)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				delete(itemPosition);
			}
		});
		
		
		return convertView;
	}
	
	public void addList(List<Message> data){
		this.data.addAll(data);
		notifyDataSetChanged();   //when data changed,fresh data   
	}	
	public void clear(){
		data.clear();
		notifyDataSetChanged(); 
	}
	public void delete(int position){
		data.remove(position);
		notifyDataSetChanged();
	}
	private static class ListCell{
		private LinearLayout llCellTab;
		
		public ListCell(LinearLayout llCellTab){
			this.llCellTab = llCellTab;
		}
		public LinearLayout getllCellTab(){
			return llCellTab;
		}

	}
}
