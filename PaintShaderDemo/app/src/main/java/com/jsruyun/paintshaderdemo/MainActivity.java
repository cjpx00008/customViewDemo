package com.jsruyun.paintshaderdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimListView animListView = (AnimListView) findViewById(R.id.main_alv);
        animListView.setAdapter(new BaseAdapter() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, null);
                return convertView;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 100;
            }
        });
    }
}
