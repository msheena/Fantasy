package cn.tail.fantasy.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cn.tail.fantasy.R;

public class AnglePopUpWindow extends PopupWindow {
    Context context;
    private AngleLayout angleLayout;

    /**
     * 当使用showAsDropDown()，此方法不支持根据剩余区域的大小自动显示在anchorView的上方或下方
     * @param context
     */
    public AnglePopUpWindow(Context context) {
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    /**
     * 当使用showAsDropDown()，指定pop的具体大小，可以根据剩余区域的大小自动显示在anchorView的上方或下方
     * @param context
     * @param popWidth
     * @param popHeight
     */
    public AnglePopUpWindow(Context context, int popWidth, int popHeight) {
        super(context);
        this.context=context;
        initView(context,popWidth,popHeight);

    }

    private void initView(Context context, int popWidth,int popHeight) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_angel_pop_view, null, false);
        angleLayout = view.findViewById(R.id.angle_pop_container);

        setContentView(view);
        setHeight(popHeight);
        setWidth(popWidth);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void inflateView(View view){
        if (view!=null){
            angleLayout.addView(view);
        }
    }

    public void setAngleDimensions(float width,float height){
        angleLayout.setAngelDimens(width,height);

    }

    public void setAngleColor(int color){
        angleLayout.setPaintColor(color);
    }
    public void setStrokeWidth(float width){
        angleLayout.setIsStroke(true,width);
    }
    public void setRadius(float radius){
        angleLayout.setRadius(radius);
    }
    public void setAnglePosition(@AngleLayout.anglePosition int position){
        angleLayout.setAnchorPosition(position);
    }
    public void setAngleOffset(float x,float y){
        angleLayout.setAngleOffset(x,y);
    }

    public void setGradient(int startColor,int endColor){
        angleLayout.setPaintColor(true,startColor,endColor);
    }
    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        angleLayout.setAnchorView(anchor);
        angleLayout.invalidate();
    }
}
