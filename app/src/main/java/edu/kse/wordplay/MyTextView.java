package edu.kse.wordplay;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    private OnDrawCompleteListener onDrawCompleteListener;
    private int drawCount;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCount++;

        if(onDrawCompleteListener != null){
            onDrawCompleteListener.onComplete(this);
        }
    }

    public void setOnDrawCompleteListener(OnDrawCompleteListener onDrawCompleteListener) {
        this.onDrawCompleteListener = onDrawCompleteListener;
    }

    public interface OnDrawCompleteListener{
        void onComplete(MyTextView view);
    }

    public int getDrawCount() {
        return drawCount;
    }
}
