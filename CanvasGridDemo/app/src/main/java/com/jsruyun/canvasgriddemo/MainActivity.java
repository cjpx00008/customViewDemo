package com.jsruyun.canvasgriddemo;

import android.graphics.PointF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private PolylineView mPolylineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPolylineView = (PolylineView) findViewById(R.id.main_pv);

        List<PointF> pointFs = new ArrayList<PointF>();
        pointFs.add(new PointF(0.3F, 0.5F));
        pointFs.add(new PointF(1F, 2.7F));
        pointFs.add(new PointF(2F, 3.5F));
        pointFs.add(new PointF(3F, 3.2F));
        pointFs.add(new PointF(4F, 1.8F));
        pointFs.add(new PointF(5F, 1.5F));
        pointFs.add(new PointF(6F, 2.2F));
        pointFs.add(new PointF(7F, 5.5F));
        pointFs.add(new PointF(8F, 7F));
        pointFs.add(new PointF(8.6F, 5.7F));

        mPolylineView.setData(pointFs, "Money", "Time");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
