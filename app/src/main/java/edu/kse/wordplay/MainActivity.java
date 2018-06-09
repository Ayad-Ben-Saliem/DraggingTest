package edu.kse.wordplay;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainTAG";
    private static final String TAG2 = "MainTAG2";
    private float x;
    private float y;

    ViewGroup.LayoutParams layoutParams;
    ViewGroup rootLayout;
    View view1;
    View view2;
    View view3;

    GridLayout gridLayout;

    private static final List<View> views = new ArrayList<>();
//    static View draggedView;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        gridLayout = findViewById(R.id.gridLayout);

        view1.setOnTouchListener(this::startDragging);
        view2.setOnTouchListener(this::startDragging);
        view3.setOnTouchListener(this::startDragging);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        View.OnDragListener viewsDragListener = (v, event) -> {
            if(v.equals(view1))
                index = 0;
            else if(v.equals(view2))
                index = 1;
            else if(v.equals(view3))
                index = 2;

            Log.d(TAG, "INDEX : " + index + " : " + views.get(index));

            return false;
        };

        view1.setOnDragListener(viewsDragListener);
        view2.setOnDragListener(viewsDragListener);
        view3.setOnDragListener(viewsDragListener);

        rootLayout.setOnDragListener(new MyDragEventListener());

        int[] colors = {Color.GREEN, Color.BLUE, Color.YELLOW};
        for(int i=0; i<gridLayout.getChildCount(); i++){
            gridLayout.getChildAt(i).setBackgroundColor(colors[i]);
        }
    }


    protected boolean startDragging(View view, MotionEvent event){
        view.startDrag(null,
                        new View.DragShadowBuilder(view),
                        null,
                        0);
        view.setVisibility(View.INVISIBLE);

        return true;
    }


    protected class MyDragEventListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
//            Log.d(TAG, event.toString());

            switch (event.getAction()){
                case DragEvent.ACTION_DROP:
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "index : " + index);
                    views.get(index).setVisibility(View.VISIBLE);
            }

            x = event.getX();
            y = event.getY();

            return true;
        }
    };

}
