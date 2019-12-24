package cn.tail.fantasy.util;

import android.content.Context;

public class DisplayUtil {
    public static int dp2px(Context context,float dp){
        float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
