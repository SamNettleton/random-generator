package com.example.sam.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.sam.myfirstapp.MESSAGE";

    public static int randMin = 0;
    public static int randMax = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView randtb = (TextView)findViewById(R.id.rand_field);
        String randomString = MainActivity.createRandom();
        randtb.setText(randomString);
    }

    public static String createRandom() {
        Random randNum = new Random();
        int range = randMax - randMin + 1;
        int randomInt = randNum.nextInt(range) + randMin;
        return Integer.toString(randomInt);
    }

    /** called when the user clicks the Update Number button */
    public void updateNumber(View view) {
        /**Intent intent = new Intent(this, NumberSettings.class); */
        TextView newNumSpace = (TextView) findViewById(R.id.rand_field);
        String newRandomString = MainActivity.createRandom();
        newNumSpace.setText(newRandomString);
        /**String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent); */
    }

    /** called when the user clicks the Settings button */
    public void settingsButton(View view) {
        Intent intent = new Intent(this, NumberSettings.class);
        startActivity(intent);
    }
}
