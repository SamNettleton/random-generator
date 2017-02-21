package com.rng.sam.randomnumbergenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.ArrayAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;

public class NumberSettings extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private AdView mAdView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_settings);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        Context context = this;
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        final SharedPreferences.Editor edit = settingSave.edit();
        MainActivity.randMin = settingSave.getInt("minSave", 0);
        MainActivity.randMax = settingSave.getInt("maxSave", 10000);
        MainActivity.numberOfRandoms = settingSave.getInt("norSave", 1);

        //handle ad setup
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //set default numbers in min/max value spaces
        TextView minValueField = (TextView) findViewById(R.id.min_field);
        TextView maxValueField = (TextView) findViewById(R.id.max_field);
        minValueField.setText(String.valueOf(MainActivity.randMin));
        maxValueField.setText(String.valueOf(MainActivity.randMax));

        //set up for amount random spinner
        Spinner amountRandom = (Spinner) findViewById(R.id.amount_random);
        ArrayList<String> spinnerVals = new ArrayList<String>();
        for (int i=0; i<11; i++){
            spinnerVals.add(Integer.toString(i));
        }
        // Creating adapter for spinner so it shows array
        ArrayAdapter<String> adaptAmountRandom = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerVals);
        // Drop down layout style - list view with radio button
        adaptAmountRandom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        amountRandom.setAdapter(adaptAmountRandom);
        amountRandom.setSelection(MainActivity.numberOfRandoms);
        // creating listener for the spinner
        amountRandom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                MainActivity.numberOfRandoms = position;
                edit.putInt("norSave", MainActivity.numberOfRandoms);
                edit.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Add a listener for confirm settings button
        Intent intent = getIntent();
        Button btnInvalidRange = (Button) findViewById(R.id.confirm_settings);
        btnInvalidRange.setOnClickListener(new View.OnClickListener() {
            //Set up snackbar for min > max error
            @Override
            public void onClick(View view) {
                Snackbar invalidRange = Snackbar.make(coordinatorLayout,
                                "Error: minimum value must be less than maximum value", Snackbar.LENGTH_LONG);
                Snackbar missingValue = Snackbar.make(coordinatorLayout,
                                "Error: Please make sure all fields have values", Snackbar.LENGTH_LONG);
                Snackbar largeNumber = Snackbar.make(coordinatorLayout,
                                "Error: The maximum value entered is too large", Snackbar.LENGTH_LONG);
                TextView minValueField = (TextView) findViewById(R.id.min_field);
                TextView maxValueField = (TextView) findViewById(R.id.max_field);
                String minValString = minValueField.getText().toString().trim();
                String maxValString = maxValueField.getText().toString().trim();
                if (minValString.isEmpty() || maxValString.isEmpty()) {
                    missingValue.show();
                } else {
                    int newMin = Integer.parseInt(minValueField.getText().toString());
                    int newMax = Integer.parseInt(maxValueField.getText().toString());
                    //If min > max display snackbar error message
                    if (newMin > newMax) {
                        invalidRange.show();
                    }
                    //Else update min and max globally and apply changes
                    else {
                        edit.putInt("minSave", newMin);
                        edit.putInt("maxSave", newMax);
                        edit.apply();
                        MainActivity.randMin = newMin;
                        MainActivity.randMax = newMax;
                        finish();
                    }
                }
            }
        });
        EditText minValField = (EditText) findViewById(R.id.min_field);
        minValField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText minValField = (EditText) findViewById(R.id.min_field);
                minValField.setText("");
            }
        });
        EditText maxValField = (EditText) findViewById(R.id.max_field);
        maxValField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText maxValField = (EditText) findViewById(R.id.max_field);
                maxValField.setText("");
            }
        });
    }

    //When cancel button is pressed on settings screen
    public void backToMain (View view) {
        finish();
    }
}
