package com.example.dell.appcuxa.CustomeView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class RobBoldText extends TextView {
    public RobBoldText(Context context) {
        super(context);
        setTypeFace();
    }

    public RobBoldText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTypeFace();
    }

    public RobBoldText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeFace();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RobBoldText(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeFace();
    }

    public void setTypeFace(){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Bold.ttf");
        RobBoldText.this.setTypeface(typeface);
    }
}
