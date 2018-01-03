package com.example.a77354.android_final_project.Widget;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.a77354.android_final_project.R;

//import com.github.mzule.fantasyslide.app.R;

/**
 * Created by shujunhuai on 2017/12/26.
 */

public class CircleImageView extends de.hdodenhof.circleimageview.CircleImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setBorderColor(getResources().getColor(R.color.green));
        } else {
            setBorderColor(Color.WHITE);
        }
    }
}
