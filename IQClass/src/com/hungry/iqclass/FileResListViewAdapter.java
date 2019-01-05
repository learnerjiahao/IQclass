package com.hungry.iqclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileResListViewAdapter extends BaseAdapter{

	private Context context = null;
	private String class_id = null;
	private List<HashMap<String, String>> fileInfo = new ArrayList<HashMap<String,String>>();
	public FileResListViewAdapter(Context context,String class_id) {
		this.context = context;
		this.class_id = class_id;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fileInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return fileInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			
			convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.listview_cell_lesson_res, null);
			convertView.setTag(new ListCell((LinearLayout)convertView.findViewById(R.id.llCellTab)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llCellTab = lc.getllCellTab();
		HashMap<String, String> mapFileInfo = (HashMap<String,String>) getItem(position);
		
/*		((TextView)(llCellTab.findViewById(R.id.tv_file_name))).setText(mapFileInfo.get("file_name"));
		((TextView)(llCellTab.findViewById(R.id.tv_file_size))).setText(mapFileInfo.get("file_size"));
		((TextView)(llCellTab.findViewById(R.id.tv_publish_file_date))).setText(mapFileInfo.get("publish_date"));
		((TextView)(llCellTab.findViewById(R.id.tv_publish_person))).setText(mapFileInfo.get("publish_person"));
		
		//根据文件类型设置文件图标
		
		
		//下载的点击事件，需要传文件id和class_id到服务器，请求下载。
		llCellTab.findViewById(R.id.tv_publish_file_date).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		return convertView;
	}
	
	public void addList(List<HashMap<String, String>> fileInfo){
		this.fileInfo.addAll(fileInfo);
		notifyDataSetChanged();   //when data changed,fresh data   
	}	
	public void clear(){
		fileInfo.clear();
		notifyDataSetChanged(); 
	}
	public void delete(int position){
		fileInfo.remove(position);
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
