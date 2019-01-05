package com.hungry.iqclass;

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

public class ClassRecordListViewAdapter extends BaseAdapter{
	private Context context = null;
	private List<HashMap<String, String>> classRecords ;

	public ClassRecordListViewAdapter(List<HashMap<String, String>> classRecords,Context context) {
		super();
		this.classRecords = classRecords;
		this.context = context;
	}
	
	

	public Context getContext() {
		return context;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return classRecords.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return classRecords.get(position);
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
					R.layout.listview_cell_class_record, null);
			convertView.setTag(new ListCell((LinearLayout) convertView
					.findViewById(R.id.ll_cell_lable_class_record)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llClassLabel = lc.getllClassLabel();
		HashMap<String, String> classRecord = (HashMap<String, String>) getItem(position);
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_classmate_id))
				.setText(classRecord.get("member_id"));
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_classmate_name))
				.setText(classRecord.get("member_name"));
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_absent_times))
				.setText(classRecord.get("absent_times")+"次");
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_late_times))
		.setText(classRecord.get("late_times")+"次");
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_vocation_times))
		.setText(classRecord.get("note_times")+"次");
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_early_leave_times))
		.setText(classRecord.get("leave_early_times")+"次");
		((TextView) llClassLabel.findViewById(R.id.tv_class_record_amend)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
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
