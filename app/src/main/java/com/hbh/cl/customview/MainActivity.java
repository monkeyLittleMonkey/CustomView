package com.hbh.cl.customview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.hbh.cl.customview.View.FalseView;
import com.hbh.cl.customview.View.PieView;
import com.hbh.cl.customview.View.ClockView;
import com.hbh.cl.customview.model.PieData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout pieview_layout;
    private LinearLayout falseView_layout;
    private LinearLayout speedView_layout;
    private List<PieData> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pieview_layout = (LinearLayout) findViewById(R.id.pieview_layout);
        falseView_layout = (LinearLayout) findViewById(R.id.falseView);
        speedView_layout = (LinearLayout) findViewById(R.id.speedView);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        drawPieView();//饼图
//        drawFalseView();//错视图
        drawCircle();//圆
    }

    public void drawCircle(){
        ClockView clockView = new ClockView(this);
        speedView_layout.addView(clockView);
    }

    /**
     * 绘制错视图
     */
    public void drawFalseView(){
        FalseView falseView = new FalseView(this);
        falseView_layout.addView(falseView);
    }

    /**
     * 绘制饼图
     */
    public void drawPieView(){
        for (int i = 0; i < 5; i++) {
            PieData p = new PieData();
            p.setName("name"+i);
            p.setValue((i+1) * 3);
            lists.add(p);
        }

        PieView pv = new PieView(this);
        pv.setStartAngle(0);
        pv.setData(lists);

        pieview_layout.addView(pv);
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
