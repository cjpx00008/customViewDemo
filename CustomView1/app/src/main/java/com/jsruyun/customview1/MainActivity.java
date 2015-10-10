package com.jsruyun.customview1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity {

    private CustomView mCustomView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 设置自定义View的半径值
            mCustomView.setRadiu(mRadiu);
        }
    };
    private int mRadiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomView = (CustomView) findViewById(R.id.customView);


        /*
         * 开线程
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                 * 确保线程不断执行不断刷新界面
                 */
                while (true) {
                    try {
                        /*
                         * 如果半径小于200则自加否则大于200后重置半径值以实现往复
                         */
                        if (mRadiu <= 200) {
                            mRadiu += 10;

                            // 发消息给Handler处理
                            mHandler.obtainMessage().sendToTarget();
                        } else {
                            mRadiu = 0;
                        }

                        // 每执行一次暂停40毫秒
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 界面销毁后清除Handler的引用
        mHandler.removeCallbacksAndMessages(null);
    }
}
