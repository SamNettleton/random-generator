package com.rng.sam.randomnumbergenerator;

import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import android.content.Context;
import android.widget.Toast;

public class NumberSettings extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    Context context = this;
    @Override
    @SuppressWarnings("ConstantConditions")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_settings);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView minValueField = (TextView) findViewById(R.id.min_field);
        final TextView maxValueField = (TextView) findViewById(R.id.max_field);
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        final SharedPreferences.Editor edit = settingSave.edit();
        MainActivity.randMin = settingSave.getInt("minSave", 0);
        MainActivity.randMax = settingSave.getInt("maxSave", 10000);
        MainActivity.numberOfRandoms = settingSave.getInt("norSave", 1);

        //handle ad setup
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //set default numbers in min/max value spaces
        minValueField.setText(String.valueOf(MainActivity.randMin));
        maxValueField.setText(String.valueOf(MainActivity.randMax));

        //set up for amount random spinner
        Spinner amountRandom = (Spinner) findViewById(R.id.amount_random);
        ArrayList<String> spinnerVals = new ArrayList<>();
        for (int i=0; i<11; i++){
            spinnerVals.add(Integer.toString(i));
        }
        // Creating adapter for spinner so it shows array
        ArrayAdapter<String> adaptAmountRandom = new ArrayAdapter<>
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
                MainActivity.numberOfRandoms = position;
                edit.putInt("norSave", MainActivity.numberOfRandoms);
                edit.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Add a listener for save settings button
        Button btnInvalidRange = (Button) findViewById(R.id.save_settings);
        btnInvalidRange.setOnClickListener(new View.OnClickListener() {
            //Set up snackbar for min > max error
            @Override
            public void onClick(View view) {
                Snackbar invalidRange = Snackbar.make(coordinatorLayout,
                        "Error: minimum value must be less than maximum value", Snackbar.LENGTH_LONG);
                Snackbar missingValue = Snackbar.make(coordinatorLayout,
                        "Error: Please make sure all fields have values", Snackbar.LENGTH_LONG);
                Snackbar outOfRangeMin = Snackbar.make(coordinatorLayout,
                        "Error: The minimum number entered is too large", Snackbar.LENGTH_LONG);
                Snackbar outOfRangeMax = Snackbar.make(coordinatorLayout,
                        "Error: The maximum number entered is too large", Snackbar.LENGTH_LONG);
                boolean successMin = false;
                boolean successMax = false;
                try {
                    int newMin = Integer.parseInt(minValueField.getText().toString());
                    successMin = true;
                } catch (NumberFormatException e) {
                    outOfRangeMin.show();
                }
                try {
                    int newMax = Integer.parseInt(maxValueField.getText().toString());
                    successMax = true;
                } catch (NumberFormatException e) {
                    outOfRangeMax.show();
                }
                if (successMin && successMax) {
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
                            Toast.makeText(NumberSettings.this,
                                    "Settings Saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        minValueField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minValueField.setText("");
            }
        });
        maxValueField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxValueField.setText("");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                // app icon in action bar clicked; goto parent activity.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
