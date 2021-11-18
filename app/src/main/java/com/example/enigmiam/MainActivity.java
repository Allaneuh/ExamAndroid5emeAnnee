package com.example.enigmiam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnAddRestaurant;
    Context ctx;
    RecyclerView rv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv= findViewById(R.id.rvCritiques);
        Intent intentAddRestaurant = new Intent(this,AddRestaurants.class);
        MyDataBaseHelper db = new MyDataBaseHelper(MainActivity.this);
        List critiques= db.getAllCritiques();
        ctx=this;
        btnAddRestaurant =findViewById(R.id.btnAddRestaurant);
        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(intentAddRestaurant);
            }
        });

        RVAdapter adapter = new RVAdapter(this, critiques);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(rv);
    }
}