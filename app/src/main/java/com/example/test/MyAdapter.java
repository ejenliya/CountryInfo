package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> info = new ArrayList<>();

    MyAdapter(Context context, ArrayList<HashMap<String, String>> info) {
        this.info = info;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        HashMap<String, String> item = info.get(position);
        holder.country.setText(item.get("country"));
        holder.code.setText(item.get("code"));
        holder.region.setText(item.get("region"));
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView country, code, region;
        ViewHolder(View view){
            super(view);
            country = view.findViewById(R.id.country);
            code = view.findViewById(R.id.code);
            region = view.findViewById(R.id.region);
        }
    }
}
