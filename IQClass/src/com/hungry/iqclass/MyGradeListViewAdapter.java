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

public class MyGradeListViewAdapter extends BaseAdapter{
	private Context context = null;
	private List<HashMap<String, String>> myGrade ;

	public MyGradeListViewAdapter(List<HashMap<String, String>> grade,Context context) {
		super();
		this.myGrade = grade;
		this.context = context;
	}
	
	

	public Context getContext() {
		return context;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myGrade.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myGrade.get(position);
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
					R.layout.listview_cell_my_grade, null);
			convertView.setTag(new ListCell((LinearLayout) convertView
					.findViewById(R.id.ll_cell_lable_grade)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llClassLabel = lc.getllClassLabel();
		HashMap<String, String> mGrade = (HashMap<String, String>) getItem(position);
		((TextView) llClassLabel.findViewById(R.id.tv_grade_class_grade))
				.setText(mGrade.get("class_grade"));
		((TextView) llClassLabel.findViewById(R.id.tv_grade_class_type))
				.setText(mGrade.get("class_type"));
		((TextView) llClassLabel.findViewById(R.id.tv_grade_class_name))
				.setText(mGrade.get("class_name"));
		((TextView) llClassLabel.findViewById(R.id.tv_grade_grade))
		.setText(mGrade.get("my_grade"));
		((TextView) llClassLabel.findViewById(R.id.tv_grade_class_semester))
		.setText(mGrade.get("class_semester"));
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
