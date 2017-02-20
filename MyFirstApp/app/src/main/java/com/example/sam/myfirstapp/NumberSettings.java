package com.example.sam.myfirstapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NumberSettings extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_settings);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

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

        // creating listener for the spinner
        amountRandom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
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
                TextView minValueField = (TextView) findViewById(R.id.min_field);
                TextView maxValueField = (TextView) findViewById(R.id.max_field);
                int newMin = Integer.parseInt(minValueField.getText().toString());
                int newMax = Integer.parseInt(maxValueField.getText().toString());
                //If min > max display snackbar error message
                if (newMin > newMax) {
                    invalidRange.show();
                }
                //Else update min and max globally and apply changes
                else {
                    MainActivity.randMin = newMin;
                    MainActivity.randMax = newMax;
                    finish();
                }
            }
        });
    }

    //When cancel button is pressed on settings screen
    public void backToMain (View view) {
        finish();
    }
}
