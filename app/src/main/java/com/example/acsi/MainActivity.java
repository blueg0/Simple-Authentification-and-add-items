package com.example.acsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acsi.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    ArrayList<String> name,symptomes;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DatabaseHelper(this);

        name= new ArrayList<>();
        symptomes= new ArrayList<>();
        recyclerView= binding.rec;
        adapter= new MyAdapter(this,name,symptomes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();



        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog);
                dialog.show();
                EditText maladie = dialog.findViewById(R.id.etName);

                EditText symptomes = dialog.findViewById(R.id.Symptomes);
                TextView skip = dialog.findViewById(R.id.skip);
                Button Ajouter = dialog.findViewById(R.id.ajouter);
                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Ajouter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String name = maladie.getText().toString().trim();
                            String symptoms = symptomes.getText().toString().trim();

                            if (name.isEmpty() || symptoms.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Please enter both name and description", Toast.LENGTH_SHORT).show();
                            }else {
                                Boolean insert = dbHelper.insertMaladies(name,symptoms);
                                if (insert){
                                    Toast.makeText(MainActivity.this, "new entry inserted", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, "entry not inserted", Toast.LENGTH_SHORT).show();
                                }
                            }



                        }catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }

   private void displaydata() {
        Cursor cursor =dbHelper.getdata();
        if (cursor.getCount()==0){
            Toast.makeText(MainActivity.this, "No entry exist", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                name.add(cursor.getString(0));
                symptomes.add(cursor.getString(1));
            }
        }
    }
}