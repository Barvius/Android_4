package com.example.barvius.lb4;

import android.app.FragmentBreadCrumbs;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String location = null;
    private ArrayList<File> dir;
    private ListAdapter listAdapter;
    private GestureDetectorCompat gestureDetector;
    private ScaleGestureDetector gestureScaleDetector;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("location", location);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        location = savedInstanceState.getString("location");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDir();
    }

    @Override
    public void onBackPressed() {
        if (closeDir()){
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListGestureDetector lgd = new ListGestureDetector();
        lgd.setActivity(this);
        gestureDetector = new GestureDetectorCompat(this, lgd);

        ListScaleGestureDetector lsgd = new ListScaleGestureDetector();
        lsgd.setActivity(this);
        gestureScaleDetector = new ScaleGestureDetector(this, lsgd);

        location = Environment.getExternalStorageDirectory().toString();
    }

    public ArrayList<File> LS(String fn) {
        ArrayList<File> files = new ArrayList<File>();
        File f = new File(fn);
        if (f.canRead()) {
            for (File file : f.listFiles()) {
                files.add(file);
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Ошибка открытия!", Toast.LENGTH_SHORT);
            toast.show();
        }
        return files;
    }

    public boolean closeDir() {
        if (listAdapter.getSelectedItem() != -1) {
            listAdapter.setSelectedItem(-1);
            location = new File(location).getParent();
            return false;
        } else {
            if (!location.equals(Environment.getExternalStorageDirectory().toString())) {
                location = new File(location).getParent();
                showDir();
                return false;
            }
            return true;
        }
    }

    public void openDir(){
        if(listAdapter.getSelectedItem() != -1){
            if (new File(location).isDirectory()) {
                showDir();
            } else {
                Toast toast = Toast.makeText(this,
                        "Это не папка!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void showDir() {
        TextView tw = (TextView) findViewById(R.id.nav_text);
        tw.setText(location);
        dir = LS(location);

        listAdapter = new ListAdapter(this, dir);
        final ListView listView = (ListView) findViewById(R.id.list_area);
        listView.setAdapter(listAdapter);

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                gestureScaleDetector.onTouchEvent(event);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                listAdapter.setSelectedItem(position);
                location = dir.get(position).toString();
            }

        });
    }

    public String getLocation() {
        return location;
    }
}
