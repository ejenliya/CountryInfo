package com.example.test;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

class MyAdapter extends SimpleAdapter {
    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
