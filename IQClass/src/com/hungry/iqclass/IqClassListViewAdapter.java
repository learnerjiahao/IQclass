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
import com.hungry.iqclass.ic.Lesson;

public class IqClassListViewAdapter extends BaseAdapter {

	private Context context = null;
	private List<Lesson> lessons = new ArrayList<Lesson>();

	public IqClassListViewAdapter(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lessons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lessons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			convertView = LayoutInflater.from(this.getContext()).inflate(
					R.layout.listview_cell_iqclass, null);
			convertView.setTag(new ListCell((LinearLayout) convertView
					.findViewById(R.id.llClassLabel)));
		}
		ListCell lc = (ListCell) convertView.getTag();
		LinearLayout llClassLabel = lc.getllClassLabel();
		Lesson lesson = (Lesson) getItem(position);
		((TextView) llClassLabel.findViewById(R.id.tvLessonName))
				.setText(lesson.getClass_name());
		((TextView) llClassLabel.findViewById(R.id.tvLessonLocation))
				.setText(lesson.getClass_location());
		((TextView) llClassLabel.findViewById(R.id.tvLessonNumber))
				.setText(lesson.getClass_segmenceth());
		((TextView) llClassLabel.findViewById(R.id.tvLessonTime))
				.setText(lesson.getClass_time());
		return convertView;
	}

	public void addList(List<Lesson> lessons) {
		List<Lesson> listLessons = new ArrayList<Lesson>();
		while(lessons.size() > 0){
			Lesson lesson = getForeLesson(lessons);
			listLessons.add(lesson);
			lessons.remove(lesson);
		}
		this.lessons.addAll(listLessons);
		notifyDataSetChanged(); // when data changed,fresh data
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
	
	private static Lesson getForeLesson(List<Lesson> lessons){
		Lesson foreLesson = lessons.get(0);
		for(int i=0;i<lessons.size();i++){
			if((lessons.get(i).getClass_segmenceth()).compareTo(foreLesson.getClass_segmenceth())<=0){
				foreLesson = lessons.get(i);
			}
		}
		return foreLesson;
	}

}
