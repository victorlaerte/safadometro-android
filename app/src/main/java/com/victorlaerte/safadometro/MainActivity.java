package com.victorlaerte.safadometro;

import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private double year;
    private double month;
    private double day;
    private double vagabundo;
    private double anjo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        populateYearSpinner();

        populateMonthSpinner();

        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);
                TransitionDrawable transition = (TransitionDrawable) layout.getBackground();
                transition.startTransition(1000);

                vagabundo = calculateVagabundo();
                Log.d("", "Vagabundo " + vagabundo);
                anjo = calculateAnjo(vagabundo);
                Log.d("", "Anjo " + anjo);

                TextView anjoTxtView = (TextView) findViewById(R.id.anjoString);
                anjoTxtView.setText(anjo + "% Anjo");

                TextView vabagundoTxtView = (TextView) findViewById(R.id.vagabundoString);
                vabagundoTxtView.setText(vagabundo + "% Vagabundo");

                ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);

                LinearLayout shareLayout = (LinearLayout) findViewById(R.id.shareLayout);
                shareLayout.setVisibility(View.VISIBLE);

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentTitle("Sou " + anjo + " anjo, perfeito mas aquele " + vagabundo + " é vagabunbdo")
                        .setContentDescription("Calcule sua porcentagem Anjo com o safadômetro para Android e em breve para IOs!!")
                        .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.victorlaerte.safadometro"))
                        .build();

                shareButton.setShareContent(content);
            }
        });

        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        likeView.setObjectIdAndType("https://www.facebook.com/appsafadometro", LikeView.ObjectType.PAGE);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private double calculateVagabundo() {

        double vagabundo = 0;

        int somatorio = sum((int) month);

        vagabundo = somatorio + (year / 100) * (50 - day);

        return vagabundo;
    }

    private double calculateAnjo(double vagabundo) {

        return 100 - vagabundo;
    }

    private void populateYearSpinner() {

        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);

        List<Integer> yearList = new ArrayList<Integer>();

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);

        for (int i = 1960; i < (currentYear - 1); i++) {

            yearList.add(i);
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, yearList);
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

        SpinnerAdapter arrayAdapter = new SpinnerAdapter(this, android.R.layout.simple_list_item_1, values);
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

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, daysList);
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

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }
}
