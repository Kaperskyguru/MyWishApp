package com.multimega.kaperskyguru.mywishapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {
    private EditText titleText, contentText;
    private Button submit;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(getApplicationContext());
        titleText = (EditText) findViewById(R.id.titleEditText);
        contentText = (EditText) findViewById(R.id.myWishEditText);
        submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_to_DB_clear_form_close_DB_and_switch_intent();
            }
        });

    }

    private void save_to_DB_clear_form_close_DB_and_switch_intent() {
        MyWish wish = new MyWish();
        wish.setTitle(titleText.getText().toString().trim());
        wish.setContent(contentText.getText().toString().trim());
        db.add_wishes_to_database(wish);


        clearForm();

        Intent i = new Intent(MainActivity.this, DisplayWishesActivity.class);
        startActivity(i);
        closeDB();
    }

    private void clearForm() {
        titleText.setText("");
        contentText.setText("");
    }

    private void closeDB() {
        db.close();
    }
}
