package edu.kse.wordplay;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainTAG";

    ViewGroup rootLayout;
    MyView view1;
    MyView view2;
    MyView view3;

    TextView draggedView;

    final Map<View, Integer> draggedViews = new HashMap<>();

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        String s = "ABC" + draggedViews;

        rootLayout = findViewById(R.id.rootLayout);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        gridLayout = findViewById(R.id.gridLayout);

        gridLayout.setColumnCount(3);

        gridLayout.addView(getCharViewCopy(view1));
        gridLayout.addView(getCharViewCopy(view2));
        gridLayout.addView(getCharViewCopy(view3));

        MyView.OnDrawCompleteListener onDrawCompleteListener = view -> {
            AppCompatImageView imageView = new AppCompatImageView(this);
            imageView.setImageResource(R.drawable.dot_line);

            imageView.setMinimumHeight(100);
            imageView.setMinimumWidth(view1.getWidth());

            gridLayout.addView(imageView);
        };

        view1.setOnDrawCompleteListener(onDrawCompleteListener);
        view2.setOnDrawCompleteListener(onDrawCompleteListener);
        view3.setOnDrawCompleteListener(onDrawCompleteListener);

        draggedViews.put(view1, 0);
        draggedViews.put(view2, 1);
        draggedViews.put(view3, 2);

        view1.setOnTouchListener(this::startDragging);
        view2.setOnTouchListener(this::startDragging);
        view3.setOnTouchListener(this::startDragging);

        rootLayout.setOnDragListener((view, dragEvent) -> {
            switch (dragEvent.getAction()){
                case DragEvent.ACTION_DROP:
                    float x = dragEvent.getX(); // - draggedView.getWidth()/2;
                    float y = dragEvent.getY(); // - draggedView.getHeight()/2;

                    Log.i(TAG, "X : " + x);
                    Log.i(TAG, "Y : " + y);

                    int index = draggedViews.get(draggedView);
                    TextView viewInGrid = (TextView)gridLayout.getChildAt(index);

                    Point point1 = new Point(x, y);
                    Point point2 = getLocation(viewInGrid);
                    double distance = point1.getDistance(point2);

                    float dx = viewInGrid.getWidth()/2;
                    float dy = viewInGrid.getHeight()/2;
                    double v = Math.pow(dx, 2) + Math.pow(dy, 2);
                    double radius = Math.sqrt(v);

                    Log.i(TAG, "index    : " + index);
                    Log.i(TAG, "point1 : " + point1);
                    Log.i(TAG, "point2 : " + point2);
                    Log.i(TAG, "distance : " + distance);
                    Log.i(TAG, "radius   : " + radius);

                    if(distance <= radius){
                        Toast.makeText(this, "Merge", Toast.LENGTH_LONG).show();
                        viewInGrid.setText(draggedView.getText());
                        rootLayout.removeView(draggedView);
                    }else {
                        draggedView.setVisibility(View.VISIBLE);
                    }
            }

            return true;
        });
    }

    private TextView getCharViewCopy(TextView textView){
        TextView view = new TextView(this);
        view.setText(textView.getText());
        view.setTextSize(50);
        return view;
    }

    @Override
    protected void onStart() {
        super.onStart();

//        int colors[] = {Color.YELLOW, Color.CYAN, Color.GRAY};
//        for (int i=0; i<gridLayout.getChildCount(); i++){
//            gridLayout.getChildAt(i).setBackgroundColor(colors[i]);
//        }
    }

    protected boolean startDragging(View view, MotionEvent event){
        draggedView = (TextView)view;
        draggedView.startDrag(
                null,
                new View.DragShadowBuilder(view),
                null,
                0);
        draggedView.setVisibility(View.INVISIBLE);

        return true;
    }

    Point getLocation(View view){
        int outLocation[] = {0, 0};
        view.getLocationOnScreen(outLocation);

        float x = outLocation[0] + view.getWidth()/2;
        float y = outLocation[1] + view.getHeight()/2;

        return new Point(x, y);
    }

    class Point {
        final float x;
        final float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        double getDistance(Point point){
            float dx = x - point.x;
            float dy = y - point.y;
            double v = Math.pow(dx, 2) + Math.pow(dy, 2);
            return Math.sqrt(v);
        }

        @Override
        public String toString(){
            return "(" + x + ", " + y + ")";
        }
    }
}
