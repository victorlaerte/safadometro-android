package com.victorlaerte.safadometro;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<Months> {

	private Context context;
	private Months[] values;

	public SpinnerAdapter(Context context, int textViewResourceId, Months[] values) {

		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public int getCount() {

		return values.length;
	}

	@Override
	public Months getItem(int position) {

		return values[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(values[position].getLabel());

		return label;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(values[position].getLabel());

		return label;
	}
}
