package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SecondActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.info);
        Bundle arguments = getIntent().getExtras();

        String country = arguments.getString("country");
        String url = "https://date.nager.at/api/v3/CountryInfo/" + country;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this::parseJSON, error -> {});

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseJSON(String jsonResponse){
        Response res = null;
        ArrayList<HashMap<String, String>> info = null;
        try {
            res = new Response(jsonResponse);
            setTitle("borders of " + res.getCountry() + ", " + res.getRegion() + ":");
            info = res.getInfo();

            for(int i = 0; i < info.size(); i++) {
                HashMap<String, String> item = new HashMap<>();
                item = info.get(i);

                CountryItemDB database = Room.databaseBuilder(getApplicationContext(), CountryItemDB.class, "borders")
                        .fallbackToDestructiveMigration()
                        .build();

                HashMap<String, String> finalItem = item;
                Executors.newSingleThreadExecutor().execute(() -> {
                    CountryItem itemEntity = new CountryItem();
                    itemEntity.country = finalItem.get("country");
                    itemEntity.region = finalItem.get("region");
                    itemEntity.code = finalItem.get("code");
                    database.itemDAO().insertItem(itemEntity);
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new MyAdapter(this, info);

        recyclerView.setAdapter(adapter);
    }
}