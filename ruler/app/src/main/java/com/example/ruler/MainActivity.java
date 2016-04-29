package com.example.ruler;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class MainActivity extends Activity {

    private HorizontalScrollView ruler;
    private LinearLayout rulerlayout,all_layout;
    private int beginYear;
    private String birthyear = "1970";
    private long time = 0;
    private boolean isFirst = true;
    private TextView user_birth_value;
    private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_birth_value = (TextView) findViewById(R.id.user_birth_value);
        user_birth_value.setText("1970");
        ruler = (HorizontalScrollView) findViewById(R.id.birthruler);
        rulerlayout = (LinearLayout) findViewById(R.id.ruler_layout);
        ruler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                String value = String.valueOf(beginYear+(int)Math.ceil((ruler.getScrollX())/20));
                user_birth_value.setText(value);
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        new Handler().postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                user_birth_value.setText(String.valueOf(beginYear+(int)Math.ceil((ruler.getScrollX())/20)));
                            }
                        },1000);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(isFirst){
            screenWidth = ruler.getWidth();
            Toast.makeText(this,"screenWidth"+screenWidth,Toast.LENGTH_SHORT).show();
            constructRuler();
            isFirst = false;
        }
    }

    private void constructRuler() {
        int year = new Date().getYear();
        if(year<2015){
            year = 2010;
        }
        beginYear = year/10*10-150;
        View leftView = LayoutInflater.from(this).inflate(R.layout.blankhrulerunit,null);
        leftView.setLayoutParams(new ActionBar.LayoutParams(screenWidth / 2, ViewGroup.LayoutParams.MATCH_PARENT));
        rulerlayout.addView(leftView);
        for(int i=0;i<16;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.hrulerunit,null);
            view.setLayoutParams(new ActionBar.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
            TextView tv = (TextView) view.findViewById(R.id.hrulerunit);
            tv.setText(String.valueOf(beginYear+i*10));
            rulerlayout.addView(view);
        }
        View rightView = LayoutInflater.from(this).inflate(R.layout.blankhrulerunit,null);
        rightView.setLayoutParams(new ActionBar.LayoutParams(screenWidth/2, ViewGroup.LayoutParams.MATCH_PARENT));
        rulerlayout.addView(rightView);
    }
}
