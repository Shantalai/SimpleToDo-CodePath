package com.example.simpletodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText edItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edItem = findViewById(R.id.edItem);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit item");

        edItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        //when the user is done editing, they click the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an intent which which will contain the result
                Intent intent = new Intent();
                //pass the data
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, edItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                //set the result of intent
                setResult(RESULT_OK, intent);
                //finish activity and close the screen
                finish();
            }
        });
    }
}