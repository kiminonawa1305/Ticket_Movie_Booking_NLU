package com.lamnguyen.ticket_movie_nlu.utils;

import android.content.Context;
import android.util.TypedValue;

public class Convert {
    public static float convertToPx(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static float convertToDp(Context context, float pxValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics());
    }
}
