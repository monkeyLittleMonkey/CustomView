package com.hbh.cl.customview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hbh.cl.customview.View.ClockView;
import com.hbh.cl.customview.View.PanelView;
import com.hbh.cl.customview.View.SpeedView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout clockView_layout;
    private LinearLayout speedView_layout;
    private LinearLayout greadView_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        clockView_layout = (LinearLayout) findViewById(R.id.clockView);
        speedView_layout = (LinearLayout) findViewById(R.id.speedView);
        greadView_layout = (LinearLayout) findViewById(R.id.greadView);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        drawCircle();//绘制表盘
//        drawSpeedView();
        drawPanelView();
    }

    public void drawPanelView(){
        PanelView panelView = new PanelView(this);
        greadView_layout.addView(panelView);
    }

    public void drawSpeedView(){
        SpeedView speedView = new SpeedView(this);
        speedView_layout.addView(speedView);
    }

    /**
     * 绘制表盘
     */
    public void drawCircle(){
        ClockView clockView = new ClockView(this);
        clockView_layout.addView(clockView);
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
