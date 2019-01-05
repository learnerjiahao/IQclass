package com.hungry.iqclass.frament;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.R;
import com.hungry.iqclass.activity.GetGradeActivity;
import com.hungry.iqclass.activity.GetOtherLessonsActivity;
import com.hungry.iqclass.activity.GetPersonalRecordActivity;
import com.hungry.iqclass.activity.LoginActivity;
import com.hungry.iqclass.activity.PersonalInfoActivity;
import com.hungry.iqclass.activity.PostFaceDataActivity;
import com.hungry.iqclass.net.UpdateFilesTool;
import com.hungry.iqclass.net.UpdateMyNotePicture;

public class MoreFragment extends Fragment {

	private LinearLayout ll_getFaceDate;
	private LinearLayout ll_see_Personal_info;
	private LinearLayout ll_get_grade;
	private LinearLayout ll_get_personal_record;
	private LinearLayout ll_get_other_lessons;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final View view = inflater.inflate(R.layout.frament_more, container,
				false);

		ll_getFaceDate = (LinearLayout) view.findViewById(R.id.ll_getFaceDate);
		ll_getFaceDate.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(view.getContext(),
								PostFaceDataActivity.class));

					}
				});

		ll_see_Personal_info = (LinearLayout) view.findViewById(R.id.ll_see_Personal_info);
		ll_see_Personal_info.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(view.getContext(),
								PersonalInfoActivity.class));

					}
				});
		
		ll_get_grade = (LinearLayout) view.findViewById(R.id.ll_get_grade);
		ll_get_grade.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(view.getContext(),
								GetGradeActivity.class));

					}
				});
		
		ll_get_personal_record = (LinearLayout) view.findViewById(R.id.ll_get_personal_record);
		ll_get_personal_record.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(view.getContext(),
								GetPersonalRecordActivity.class));

					}
				});
		ll_get_other_lessons = (LinearLayout) view.findViewById(R.id.ll_get_other_lessons);
		ll_get_other_lessons.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(view.getContext(),
								GetOtherLessonsActivity.class));

					}
				});
		
		
		if(Config.getCachedUserType(view.getContext()).equals("teacher")){
			ll_getFaceDate.setVisibility(View.GONE);
			ll_get_grade.setVisibility(View.GONE);
			ll_get_personal_record.setVisibility(View.GONE);
			ll_get_other_lessons.setVisibility(View.GONE);
			ll_see_Personal_info.setVisibility(View.GONE);
		}

		return view;
	}
}
