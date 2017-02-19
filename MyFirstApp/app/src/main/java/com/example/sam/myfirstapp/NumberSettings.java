package com.example.sam.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NumberSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_settings);

        Intent intent = getIntent();
    }

    //When Confirm button is pressed on settings screen
    public void changeRange (View view) {
        TextView minValueField = (TextView) findViewById(R.id.min_field);
        TextView maxValueField = (TextView) findViewById(R.id.max_field);
        MainActivity.randMin = Integer.parseInt(minValueField.getText().toString());
        MainActivity.randMax = Integer.parseInt(maxValueField.getText().toString());
        finish();
        /**String message = editText.getText().toString();
         intent.putExtra(EXTRA_MESSAGE, message);
         startActivity(intent); */

    }

    //When cancel button is pressed on settings screen
    public void backToMain (View view) {
        finish();
    }
}
