package com.rng.sam.randomnumbergenerator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
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
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        final SharedPreferences.Editor edit = settingSave.edit();
        MainActivity.randMin = settingSave.getInt("minSave", 0);
        MainActivity.randMax = settingSave.getInt("maxSave", 10000);
        MainActivity.numberOfRandoms = settingSave.getInt("norSave", 1);
        MainActivity.numberOfWords = settingSave.getInt("nowSave", 1);
        MainActivity.shakeEnabled = settingSave.getBoolean("shakeSave", Boolean.TRUE);
        MainActivity.darkEnabled = settingSave.getBoolean("themeSave", Boolean.FALSE);
        if (MainActivity.darkEnabled) {
            setTheme(R.style.DarkTheme);
        } else if (!MainActivity.darkEnabled) {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.number_settings);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        final TextView minValueField = (TextView) findViewById(R.id.min_field);
        final TextView maxValueField = (TextView) findViewById(R.id.max_field);
        final Switch shakeSwitch = (Switch) findViewById(R.id.shake_switch);
        final Switch themeSwitch = (Switch) findViewById(R.id.theme_change);

        //handle ad setup
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //set default numbers in min/max value spaces
        minValueField.setText(String.valueOf(MainActivity.randMin));
        maxValueField.setText(String.valueOf(MainActivity.randMax));
        shakeSwitch.setChecked(MainActivity.shakeEnabled);
        themeSwitch.setChecked(MainActivity.darkEnabled);

        //set up for amount random spinner
        Spinner amountRandom = (Spinner) findViewById(R.id.amount_random);
        ArrayList<String> spinnerVals = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            spinnerVals.add(Integer.toString(i));
        }
        Spinner amountWords = (Spinner) findViewById(R.id.word_amount_random);
        ArrayList<String> spinnerWordVals = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            spinnerWordVals.add(Integer.toString(i));
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
        ArrayAdapter<String> adaptAmountWords = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, spinnerWordVals);
        adaptAmountWords.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountWords.setAdapter(adaptAmountWords);
        amountWords.setSelection(MainActivity.numberOfWords);
        amountWords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.numberOfWords = position;
                edit.putInt("nowSave", MainActivity.numberOfWords);
                edit.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean darkEnabled = themeSwitch.isChecked();
                edit.putBoolean("themeSave", darkEnabled);
                MainActivity.darkEnabled = darkEnabled;
                recreate();
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
        final TextView contactLabel = (TextView) findViewById(R.id.contact_label);
        contactLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDeveloper();
            }
        });
        final TextView contactDescription = (TextView) findViewById(R.id.contact_description);
        contactDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDeveloper();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final TextView minValueField = (TextView) findViewById(R.id.min_field);
        final TextView maxValueField = (TextView) findViewById(R.id.max_field);
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        final SharedPreferences.Editor edit = settingSave.edit();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                // app icon in action bar clicked; goto parent activity.
                return true;
            case R.id.action_save:
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
                String minValString = minValueField.getText().toString().trim();
                String maxValString = maxValueField.getText().toString().trim();
                if (minValString.isEmpty() || maxValString.isEmpty()) {
                    missingValue.show();
                } else {
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
                }
                if (successMin && successMax) {
                    int newMin = Integer.parseInt(minValueField.getText().toString());
                    int newMax = Integer.parseInt(maxValueField.getText().toString());
                    //If min > max display snackbar error message
                    if (newMin > newMax) {
                        invalidRange.show();
                    }
                    //Else update min and max globally and apply changes
                    else {
                        Switch shakeSwitch = (Switch) findViewById(R.id.shake_switch);
                        Boolean shakeEnabled = shakeSwitch.isChecked();
                        Switch themeSwitch = (Switch) findViewById(R.id.theme_change);
                        Boolean darkEnabled = themeSwitch.isChecked();
                        edit.putInt("minSave", newMin);
                        edit.putInt("maxSave", newMax);
                        edit.putBoolean("shakeSave", shakeEnabled);
                        edit.putBoolean("themeSave", darkEnabled);
                        edit.apply();
                        MainActivity.randMin = newMin;
                        MainActivity.randMax = newMax;
                        MainActivity.shakeEnabled = shakeEnabled;
                        MainActivity.darkEnabled = darkEnabled;
                        Toast.makeText(NumberSettings.this,
                                "Settings Saved!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    }
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gotoWordList(View v) {
        Intent intent = new Intent(getApplicationContext(), WordListActivity.class);
        startActivity(intent);
    }
    public void emailDeveloper() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"samnRNG@gmail.com"});
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(NumberSettings.this, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
