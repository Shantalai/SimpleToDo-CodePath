package com.example.simpletodoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
//import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;

    Button btnAdd;
    EditText addInput;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        addInput = findViewById(R.id.addInput);
        rvItem = findViewById(R.id.rvItem);

        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                // delete item at the position
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click "+position);
                //create activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                //pass the data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                //display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
              String todoItem = addInput.getText().toString();
              items.add(todoItem);
              itemsAdapter.notifyItemInserted(items.size() - 1);
              addInput.setText("");
              Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
              saveItems();
            }
        });

    }

    //handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){
           //Retrieve  the updated text value
           String itemText = data.getStringExtra(KEY_ITEM_TEXT);
           int position = data.getExtras().getInt(KEY_ITEM_POSITION);
           items.set(position, itemText);
           itemsAdapter.notifyItemChanged(position);
           saveItems();
           Toast.makeText(getApplicationContext(), "Item updated successfully!", Toast.LENGTH_SHORT).show();
       } else {

       }
    }

    private File getDataFile() {
         return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
            //e.printStackTrace();
            Log.e("MainActivity", "Error reading items", e);
            items =  new ArrayList<>();

        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e){

            Log.e("MainActivity", "Error reading items", e);
        }
    }
}

























