package com.dmitrij.doberstein.spritfuchs.adapters;

import java.util.ArrayList;

import com.dmitrij.doberstein.spritfuchs.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	private ArrayList<String> parentItems, child;

	public MyExpandableAdapter(ArrayList<String> parents, ArrayList<Object> childern) {
		this.parentItems = parents;
		this.childtems = childern;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		child = (ArrayList<String>) childtems.get(groupPosition);

		TextView textView = null;
		TextView textView2 = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.groupzeiten, null);
		}
		
		String item = child.get(childPosition);
		String[] items = item.split("\t");
		
		textView = (TextView) convertView.findViewById(R.id.tvGroupZeiten);
		textView.setText(items[0].trim());

		textView2 = (TextView) convertView.findViewById(R.id.tvGroupZeiten2);
		textView2.setText(items[1].trim());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(activity, child.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});
		

//		LayoutParams lp = (LayoutParams) parent.getLayoutParams();
//		View vv = inflater.inflate(R.layout.activity_vergleich_activity_list_detail, null);
//
//		ScrollView sv = (ScrollView)vv.findViewById(R.id.layout);
//		int svh = sv.getHeight();
//		int svw = sv.getWidth();
//		int tvh = textView.getHeight();
//		sv.setLayoutParams(new LinearLayout.LayoutParams(svw, svh + tvh));
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.rowzeiten, null);
		}

		((TextView) convertView).setText(parentItems.get(groupPosition));
//		((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
//		((CheckedTextView) convertView).setChecked(isExpanded);

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) childtems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
