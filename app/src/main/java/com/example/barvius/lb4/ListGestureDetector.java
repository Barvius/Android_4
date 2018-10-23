package com.example.barvius.lb4;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class ListGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private final int SWIPE_MIN_DISTANCE = 120;
    private final int SWIPE_MAX_OFF_PATH = 250;
    private final int SWIPE_THRESHOLD_VELOCITY = 200;

    private MainActivity activity = null;

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            activity.closeDir();
        }
        if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            activity.openDir();
        }
        return false;
    }
}

