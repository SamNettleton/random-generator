package com.rng.sam.randomnumbergenerator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import static java.lang.System.out;


public class MainActivity extends AppCompatActivity {

    public static int randMin = 0;
    public static int randMax = 10000;
    public static int numberOfRandoms = 1;
    public static String randomType = "NUMBEROPTION";
    public static GetRandomFactory randomFactory = new GetRandomFactory();
    public static RandomFormat format = randomFactory.getRandomMethod(randomType);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();

        Context context = this;
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        randMin = settingSave.getInt("minSave", 0);
        randMax = settingSave.getInt("maxSave", 10000);
        numberOfRandoms = settingSave.getInt("norSave", 1);
        randomType = settingSave.getString("typeSave", "NUMBEROPTION");

        TextView randtb = (TextView)findViewById(R.id.rand_field);
        format.randomMethod();
        String randomString = format.createNewRandoms(numberOfRandoms);
        randtb.setText(randomString);
        randtb.startAnimation(a);

        Button newNumbers = (Button) findViewById(R.id.button);
        newNumbers.setOnClickListener(new View.OnClickListener() {
            //Set up snackbar for min > max error
            @Override
            public void onClick(View view) {
                format.randomMethod();
                String newRandoms = format.createNewRandoms(numberOfRandoms);
                TextView newNumSpace = (TextView) findViewById(R.id.rand_field);
                //String newRandoms = MainActivity.multipleRandoms();

                newNumSpace.setText(newRandoms);
                newNumSpace.startAnimation(a);

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, NumberSettings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**public static String createRandom() {
        Random randNum = new Random();
        int range = randMax - randMin + 1;
        int randomInt = randNum.nextInt(range) + randMin;
        return Integer.toString(randomInt);
    }

    public static String multipleRandoms() {
        String newRandoms = "";
        String newLine = System.getProperty("line.separator");
        for(int i=0; i < numberOfRandoms; i++){
            String newRandomString = MainActivity.createRandom();
            newRandoms += newRandomString;
            newRandoms += newLine;
        }
        return newRandoms;
    } */


    /** called when the user clicks the Update Number button
    public void updateNumber(View view) {
        format.randomMethod();
        String newRandoms = format.createNewRandoms(numberOfRandoms);
        TextView newNumSpace = (TextView) findViewById(R.id.rand_field);
        //String newRandoms = MainActivity.multipleRandoms();
        newNumSpace.setText(newRandoms);
    } */
}
