package com.rng.sam.randomnumbergenerator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WordListActivity extends AppCompatActivity {


    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        MainActivity.darkEnabled = settingSave.getBoolean("themeSave", Boolean.FALSE);
        MainActivity.customWordString = settingSave.getString("listSave", "");
        if (MainActivity.darkEnabled) {
            setTheme(R.style.DarkTheme);
        } else if (!MainActivity.darkEnabled) {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_word_list);

        final TextView wordField = (TextView) findViewById(R.id.word_field);
        wordField.setText(MainActivity.customWordString);

        Button newNumbers = (Button) findViewById(R.id.clear_words);
        newNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure you want to clear the word list?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                TextView wordField = (TextView) findViewById(R.id.word_field);
                                wordField.setText("");
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_wordlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final TextView wordField = (TextView) findViewById(R.id.word_field);
        SharedPreferences settingSave = context.getSharedPreferences("settingSave", MODE_PRIVATE);
        final SharedPreferences.Editor edit = settingSave.edit();
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save:
                MainActivity.customWordString = wordField.getText().toString();
                edit.putString("listSave", wordField.getText().toString());
                edit.apply();
                Toast.makeText(WordListActivity.this,
                        "Word List Saved!", Toast.LENGTH_SHORT).show();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
