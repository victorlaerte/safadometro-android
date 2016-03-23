package com.victorlaerte.safadometro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Spinner yearSpinner;
	private Spinner monthSpinner;
	private Spinner daySpinner;
	private double year;
	private double month;
	private double day;
	private double safadeza;
	private double anjo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		populateYearSpinner();

		populateMonthSpinner();

		Button calculateButton = (Button) findViewById(R.id.calculateButton);
		calculateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				safadeza = calculateSafadeza();
				Log.d("", "Safadeza " + safadeza);
				anjo = calculateAnjo(safadeza);
				Log.d("", "Anjo " + anjo);

				TextView anjoTxtView = (TextView) findViewById(R.id.anjoString);
				anjoTxtView.setText(anjo + "% Anjo");

				TextView vabagundoTxtView = (TextView) findViewById(R.id.vagabundoString);
				vabagundoTxtView.setText(safadeza + "% Vagabundo");
			}
		});

		// LikeView likeView = (LikeView) findViewById(R.id.likeView);
		// likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
		// likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
		//
		// likeView.setObjectIdAndType("https://www.facebook.com/FacebookDevelopers", LikeView.ObjectType.PAGE);
	}

	private double calculateSafadeza() {

		double safadeza = 0;

		int somatorio = sum((int) month);

		safadeza = somatorio + (year / 100) * (50 - day);

		return safadeza;
	}

	private double calculateAnjo(double safadeza) {

		return 100 - safadeza;
	}

	private void populateYearSpinner() {

		yearSpinner = (Spinner) findViewById(R.id.yearSpinner);

		List<Integer> yearList = new ArrayList<Integer>();

		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);

		for (int i = 1950; i < (currentYear - 1); i++) {

			yearList.add(i);
		}

		ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, yearList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		yearSpinner.setAdapter(arrayAdapter);

		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

				year = ((Integer) yearSpinner.getSelectedItem()) % 100;
				Log.d("", "Year selected " + year);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

	}

	private void populateMonthSpinner() {

		monthSpinner = (Spinner) findViewById(R.id.monthSpinner);

		Months[] values = Months.values();

		SpinnerAdapter arrayAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, values);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		monthSpinner.setAdapter(arrayAdapter);

		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

				Months selectedMonth = (Months) monthSpinner.getSelectedItem();
				populateDaySpinner((selectedMonth.getNumberOfDays()));

				month = selectedMonth.ordinal() + 1;
				Log.d("", "Month selected " + month);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
	}

	private void populateDaySpinner(int numberOfDays) {

		daySpinner = (Spinner) findViewById(R.id.daySpinner);

		List<Integer> daysList = new ArrayList<Integer>();

		for (int i = 1; i < (numberOfDays + 1); i++) {

			daysList.add(i);
		}

		ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, daysList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		daySpinner.setAdapter(arrayAdapter);

		daySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

				day = (Integer) daySpinner.getSelectedItem();
				Log.d("", "Day selected " + day);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

	}

	private int sum(int n) {

		if (n <= 0) {
			return n;
		}

		int sum = 0;
		for (int i = n; i > 0; i--) {

			sum += i;
		}

		return sum;
	}
}
