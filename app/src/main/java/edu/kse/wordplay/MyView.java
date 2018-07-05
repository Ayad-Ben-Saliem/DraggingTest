package edu.kse.wordplay;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MyView extends android.support.v7.widget.AppCompatTextView {

    OnDrawCompleteListener onDrawCompleteListener;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(onDrawCompleteListener != null){
            onDrawCompleteListener.onComplete(this);
        }
    }

    public void setOnDrawCompleteListener(OnDrawCompleteListener onDrawCompleteListener) {
        this.onDrawCompleteListener = onDrawCompleteListener;
    }

    public interface OnDrawCompleteListener{
        void onComplete(MyView view);
    }
}
