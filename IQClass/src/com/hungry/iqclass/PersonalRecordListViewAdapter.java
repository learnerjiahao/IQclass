package com.hungry.iqclass;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalRecordListViewAdapter extends BaseAdapter{
	private Context context = null;
	private List<HashMap<String, String>> myRecords ;

	public PersonalRecordListViewAdapter(List<HashMap<String, String>> myRecords,Context context) {
		super();
		this.myRecords = myRecords;
		this.context = context;
	}
	
	

	public Context getContext() {
		return context;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myRecords.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myRecords.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			convertView = LayoutInflater.from(this.getContext()).inflate(
					R.layout.listview_cell_personal_record, null);
			convertView.setTag(new ListCell((LinearLayout) convertView
					.findViewById(R.id.llRecordCellTab)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llClassLabel = lc.getllClassLabel();
		HashMap<String, String> myRecord = (HashMap<String, String>) getItem(position);
		((TextView) llClassLabel.findViewById(R.id.tv_record_date))
				.setText(myRecord.get("record_date"));
		((TextView) llClassLabel.findViewById(R.id.tv_record_lesson_name))
				.setText(myRecord.get("class_name"));
		((TextView) llClassLabel.findViewById(R.id.tv_record_describe))
				.setText(myRecord.get("record_describe"));
		return convertView;
	}
	
	
	private static class ListCell {
		private LinearLayout llClassLabel;

		public ListCell(LinearLayout llClassLabel) {
			this.llClassLabel = llClassLabel;
		}

		public LinearLayout getllClassLabel() {
			return llClassLabel;
		}

	}

}
