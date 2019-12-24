package cn.tail.fantasy.pop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.tail.fantasy.R;
import cn.tail.fantasy.util.DisplayUtil;

public class AngleLayout extends FrameLayout {
    Context mContext;
    private Paint paint;
    private Path anglePath;
    private Path rectPath;
    private float angleCenterX;
    private float angleWidth;

    private float angleHeight;
    private boolean isAboveAnchor;
    private int surplusSpace;
    private int paintColor;
    private float paintWidth;
    private int paintStyle;
    private float radius;
    private int anglePosition;
    private float xOffset;
    private float yOffset;
    private int anchorX;
    private int anchorY;

    private int startColor;
    private int endColor;

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;


    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface anglePosition {
    }

    public AngleLayout(@NonNull Context context) {
        this(context, null);
    }

    public AngleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AngleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AngleLayout);
        angleWidth = ta.getDimension(R.styleable.AngleLayout_angleWidth, 0);
        angleHeight = ta.getDimension(R.styleable.AngleLayout_angleHeight, 0);
        paintColor = ta.getColor(R.styleable.AngleLayout_paintColor, Color.BLACK);
        paintWidth = ta.getDimension(R.styleable.AngleLayout_paintWidth, 1);
        paintStyle = ta.getInt(R.styleable.AngleLayout_paintStyle, 0);
        radius = ta.getDimension(R.styleable.AngleLayout_radius, 0);
        anglePosition = ta.getInt(R.styleable.AngleLayout_angelPosition, -1);
        xOffset = ta.getDimension(R.styleable.AngleLayout_xOffset, 0);
        yOffset = ta.getDimension(R.styleable.AngleLayout_yOffset, 0);
        ta.recycle();
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(paintStyle == 0 ? Paint.Style.FILL : Paint.Style.STROKE);
        paint.setColor(paintColor);
        paint.setStrokeWidth(paintWidth);
    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setPaintColor(@ColorInt int color) {
        if (paintColor != color) {
            paintColor = color;
            paint.setColor(paintColor);

        }
    }

    public void setPaintColor(@ColorInt int startColor,@ColorInt int endColor){
        this.startColor=startColor;
        this.endColor=endColor;
    }

    /**
     * 设置画笔是stroke还是fill
     *
     * @param isStroke
     * @param strokeWidth dp
     */
    public void setIsStroke(boolean isStroke, float strokeWidth) {
        paint.setStyle(isStroke ? Paint.Style.STROKE : Paint.Style.FILL);
        paint.setStrokeWidth(DisplayUtil.dp2px(mContext, strokeWidth));
    }

    /**
     * 设置矩形框圆角
     *
     * @param radius dp
     */
    public void setRadius(float radius) {
        this.radius = DisplayUtil.dp2px(mContext, radius);
    }

    /**
     * 设置三角宽高
     *
     * @param width  dp
     * @param height dp
     */
    public void setAngelDimens(float width, float height) {
        angleHeight = DisplayUtil.dp2px(mContext, height);
        angleWidth = DisplayUtil.dp2px(mContext, width);
    }

    /**
     * 设置三角锚点的view
     *
     * @param view
     */
    public void setAnchorView(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        anchorX = location[0];
        angleCenterX = location[0] + view.getWidth() / 2;
        anchorY = location[1];
        //view下剩余空间
        surplusSpace = DisplayUtil.getScreenHeight(mContext) - anchorY - view.getHeight();
    }

    /**
     * 设置三角位置
     *
     * @param position
     */
    public void setAnchorPosition(@anglePosition int position) {
        if (anglePosition != position) {
            anglePosition = position;
        }
    }

    /**
     * 设置三角位置的偏移量
     *
     * @param xOffset dp
     * @param yOffset dp
     */
    public void setAngleOffset(float xOffset, float yOffset) {
            this.xOffset = DisplayUtil.dp2px(mContext,xOffset);
            this.yOffset = DisplayUtil.dp2px(mContext,yOffset);
    }


    @Override
    public void invalidate() {
        calculatePath();
        super.invalidate();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawAngleBg(canvas);
        super.dispatchDraw(canvas);
    }

    private void calculatePath() {
        int width = getWidth();
        int height = getHeight();
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        float sx = 0, sy = 0;
        float cx = 0, cy = 0;
        float ex = 0, ey = 0;
        rectPath = new Path();
        RectF rectF = new RectF();
        switch (anglePosition) {
            case LEFT:
                rectF.left = angleHeight + paintWidth;
                rectF.top = paintWidth;
                rectF.right = width - paintWidth;
                rectF.bottom = height - paintWidth;
                sx = rectF.left;
                sy = 1 / 2f * height - angleWidth / 2f + yOffset;
                cx = paintWidth * 0.8f;
                cy = 1 / 2f * height + yOffset;
                ex = rectF.left;
                ey = 1 / 2f * height + angleWidth / 2f + yOffset;
                setPadding((int) Math.ceil(angleHeight),0,0,0 );

                break;
            case RIGHT:
                rectF.left = paintWidth;
                rectF.top = paintWidth;
                rectF.right = width - angleHeight - paintWidth;
                rectF.bottom = height - paintWidth;

                sx = rectF.right;
                sy = 1 / 2f * height - 1 / 2f * angleWidth + yOffset;
                cx = width - paintWidth * 0.8f;
                cy = 1 / 2f * height + yOffset;
                ex = rectF.right;
                ey = 1 / 2f * height + 1 / 2f * angleWidth + yOffset;
                setPadding(0,0,(int) Math.ceil(angleHeight),0 );

                break;
            case TOP:
                rectF.left = paintWidth;
                rectF.right = width - paintWidth;
                rectF.top = angleHeight + paintWidth;
                rectF.bottom = height - paintWidth;

                sx = angleCenterX - x - angleWidth / 2f + xOffset;
                sy = rectF.top;
                cx = angleCenterX - x + xOffset;
                cy = paintWidth * 0.8f;
                ex = angleCenterX - x + angleWidth / 2f + xOffset;
                ey = rectF.top;
                setPadding(0,(int) Math.ceil(angleHeight),0,0 );

                break;
            case BOTTOM:
                rectF.left = paintWidth;
                rectF.right = width - paintWidth;
                rectF.top = paintWidth;
                rectF.bottom = height - angleHeight - paintWidth;

                sx = angleCenterX - x - angleWidth / 2f + xOffset;
                sy = rectF.bottom;
                cx = angleCenterX - x + xOffset;
                cy = height - paintWidth * 0.8f;
                ex = angleCenterX - x + angleWidth / 2f + xOffset;
                ey = rectF.bottom;
                setPadding(0,0,0, (int) Math.ceil(angleHeight));
                break;
            default:
                isAboveAnchor = height > surplusSpace;
                rectF.left = paintWidth;
                rectF.top = isAboveAnchor ? paintWidth : angleHeight + paintWidth;
                rectF.right = width - paintWidth;
                rectF.bottom = isAboveAnchor ? height - angleHeight - paintWidth : height - paintWidth;

                sx = angleCenterX - x - angleWidth / 2f + xOffset;
                sy = isAboveAnchor ? rectF.bottom : rectF.top;
                cx = angleCenterX - x + xOffset;
                cy = isAboveAnchor ? height + paintWidth * 0.8f : paintWidth * 0.8f;
                ex = angleCenterX - x + angleWidth / 2f + xOffset;
                ey = isAboveAnchor ? rectF.bottom : rectF.top;
                setPadding(0, isAboveAnchor?0: (int) Math.ceil(angleHeight),0,isAboveAnchor?(int) Math.ceil(angleHeight):0 );

                break;
        }

        rectPath.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        anglePath = new Path();
        anglePath.moveTo(sx, sy);
        anglePath.lineTo(cx, cy);
        anglePath.lineTo(ex, ey);
        LinearGradient linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.bottom, startColor, endColor, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        rectPath.op(anglePath, Path.Op.UNION);
    }

    private void drawAngleBg(Canvas canvas) {
        canvas.save();
        calculatePath();
        canvas.drawPath(rectPath, paint);
        canvas.restore();

    }


}
