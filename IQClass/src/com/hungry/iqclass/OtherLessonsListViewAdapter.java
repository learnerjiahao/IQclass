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

public class OtherLessonsListViewAdapter extends BaseAdapter{
	private Context context = null;
	private List<HashMap<String, String>> otherLessons ;

	public OtherLessonsListViewAdapter(List<HashMap<String, String>> otherLessons,Context context) {
		super();
		this.otherLessons = otherLessons;
		this.context = context;
	}
	
	

	public Context getContext() {
		return context;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return otherLessons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return otherLessons.get(position);
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
					R.layout.listview_cell_other_classes, null);
			convertView.setTag(new ListCell((LinearLayout) convertView
					.findViewById(R.id.llOtherLessonsCellTab)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llClassLabel = lc.getllClassLabel();
		HashMap<String, String> otherLesson = (HashMap<String, String>) getItem(position);
		((TextView) llClassLabel.findViewById(R.id.tv_other_class_teacher_name))
				.setText(otherLesson.get("other_class_teacher_name"));
		((TextView) llClassLabel.findViewById(R.id.tv_other_class_name))
				.setText(otherLesson.get("other_class_name"));
		((TextView) llClassLabel.findViewById(R.id.tv_other_class_time_location))
				.setText(otherLesson.get("other_class_time_location"));
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
