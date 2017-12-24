package com.rng.sam.randomnumbergenerator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int randMin = 0;
    public static int randMax = 10000;
    public static int numberOfRandoms = 1;
    public static int numberOfWords = 1;
    public static String randomType = "NUMBEROPTION";
    public static String customWordString = "";
    public static Boolean shakeEnabled = Boolean.TRUE;
    public static Boolean darkEnabled = Boolean.FALSE;
    public final GetRandomFactory randomFactory = new GetRandomFactory();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Force invalidatation of the menu to cause onPrepareOptionMenu to be called
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        randMin = settingSave.getInt("minSave", 0);
        randMax = settingSave.getInt("maxSave", 10000);
        numberOfRandoms = settingSave.getInt("norSave", 1);
        numberOfWords = settingSave.getInt("nowSave", 1);
        customWordString = settingSave.getString("listSave", "");
        randomType = settingSave.getString("typeSave", "NUMBEROPTION");
        shakeEnabled = settingSave.getBoolean("shakeSave", Boolean.TRUE);
        darkEnabled = settingSave.getBoolean("themeSave", Boolean.FALSE);
        if (MainActivity.darkEnabled) {
            setTheme(R.style.DarkTheme);
        } else if (!MainActivity.darkEnabled) {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
        invalidateOptionsMenu();

        refreshNumbers();

        Button newNumbers = (Button) findViewById(R.id.button);
        newNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshNumbers();
            }
        });
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                if (shakeEnabled) {
                    refreshNumbers();
                }
            }
        });
    }

    private void numberButtonSelected() {
        // Find the menu item you want to style
        View view = findViewById(R.id.action_number);
        View view2 = findViewById(R.id.action_word);

        // Cast to a TextView instance if the menu item was found
        if (view != null && view instanceof TextView) {
            ((TextView) view).setTypeface(Typeface.DEFAULT_BOLD);// Make text bold
            ((TextView) view).setTextColor(Color.WHITE); // Make text colour white
            ((TextView) view2).setTypeface(Typeface.DEFAULT);// Make text regular
            ((TextView) view2).setTextColor(Color.GRAY); // Make text colour gray

        }
    }

    private void wordButtonSelected() {
        // Find the menu item you want to style
        View view2 = findViewById(R.id.action_number);
        View view = findViewById(R.id.action_word);

        // Cast to a TextView instance if the menu item was found
        if (view != null && view instanceof TextView) {
            ((TextView) view).setTypeface(Typeface.DEFAULT_BOLD);// Make text bold
            ((TextView) view).setTextColor(Color.WHITE); // Make text colour white
            ((TextView) view2).setTypeface(Typeface.DEFAULT);// Make text regular
            ((TextView) view2).setTextColor(Color.GRAY); // Make text colour gray

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);

        ActionMenuView bottomBar = (ActionMenuView)findViewById(R.id.secondary_toolbar);
        Menu secondaryMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.action_bar_secondary, secondaryMenu);
        for (int i = 0; i < secondaryMenu.size(); i++) {
            secondaryMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = this;
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        SharedPreferences.Editor edit = settingSave.edit();
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, NumberSettings.class));
                return true;
            case R.id.action_number:
                MainActivity.randomType = "NUMBEROPTION";
                edit.putString("typeSave", randomType);
                edit.apply();
                recreate();
                return true;
            case R.id.action_word:
                MainActivity.randomType = "WORDOPTION";
                edit.putString("typeSave", randomType);
                edit.apply();
                recreate();
                return true;
            /*case R.id.action_dice:
                startActivity(new Intent(this, NumberSettings.class));
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);
        if (randomType.equals("NUMBEROPTION")) {
            numberButtonSelected();
        } else if (randomType.equals("WORDOPTION")) {
            wordButtonSelected();
        }
        return result;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    public void refreshNumbers() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();
        RandomFormat format = randomFactory.getRandomMethod(randomType);
        format.randomMethod();
        String randomString = format.createNewRandoms();
        TextView newNumSpace = (TextView) findViewById(R.id.rand_field);
        newNumSpace.setText(randomString);
        newNumSpace.startAnimation(a);
    }
}
