package com.example.mobiletaskplanner.listeners;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.mobiletaskplanner.models.Task;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    public interface OnSwipeListener {
        void onSwipeLeft();
        void onSwipeRight();
    }

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private OnSwipeListener listener;

    public SwipeGestureListener(OnSwipeListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        if (Math.abs(deltaX) > Math.abs(velocityY)
                && Math.abs(deltaX) > SWIPE_THRESHOLD
                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (deltaX > 0) {
                listener.onSwipeRight();
            } else {
                listener.onSwipeLeft();
            }
            Log.e("SWIPE", "SWIPE");
            return true;
        }
        Log.e("SWIPE", "N O SWIPE");
        return false;
    }
}
