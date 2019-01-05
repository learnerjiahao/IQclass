package com.hungry.iqclass.frament;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hungry.iqclass.MessageListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.ic.Message;

public class MessageFragment extends Fragment {

	private MessageListViewAdapter adapter;
	private ListView messageListView;
	ArrayList<Message> messages =new ArrayList<Message>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.frament_message, container, false);
		adapter = new MessageListViewAdapter(view.getContext());
		messageListView = (ListView) view.findViewById(R.id.listViewMessage);
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		messages.add(new Message("hehe","haha"));
		adapter.addList(messages);
		messageListView.setAdapter(adapter);
		

		//adapter.addAll(messages);
		
		return view;
	}

	

}
