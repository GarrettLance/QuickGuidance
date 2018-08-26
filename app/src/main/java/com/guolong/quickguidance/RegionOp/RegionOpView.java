package com.guolong.quickguidance.RegionOp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.guolong.quickguidance.R;
import com.guolong.quickguidance.utils.ScreenUtils;

/**
 * Created by Garrett on 2018/8/26.
 * contact me krouky@outlook.com
 * 卧槽 API26之后只支持DIFFERENCE和INTERSECT两种，其他不支持，无法演示，否则异常
 */
public class RegionOpView extends FrameLayout {

    private int textHeight = 50;

    private Paint mPaint;
    private int colorFirstClip = 0xFFFFCC44;
    private int colorSecondClip = 0xFF66AAFF;
    private int colorOutLine = 0x66000000;

    Region.Op op;
    String style;

    RectF r1 = new RectF();
    RectF r2 = new RectF();

    public RegionOpView(Context context) {
        this(context, null);
    }

    public RegionOpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegionOpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PorterDuffView, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            switch (ta.getIndex(i)) {
                case R.styleable.PorterDuffView_porter_duff_style:
                    style = ta.getString(i);
                    break;
                default:
                    break;
            }
        }
        initOp();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(30);
    }

    private void initOp() {
        switch (style) {
            case "DIFFERENCE":
                op = Region.Op.DIFFERENCE;
                break;
            case "INTERSECT":
                op = Region.Op.INTERSECT;
                break;
            case "REPLACE":
                op = Region.Op.REPLACE;
                break;
            case "REVERSE_DIFFERENCE":
                op = Region.Op.REVERSE_DIFFERENCE;
                break;
            case "UNION":
                op = Region.Op.UNION;
                break;
            case "XOR":
                op = Region.Op.XOR;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (ScreenUtils.getScreenWidth(getContext()) - 5 * 6) / 3;
        setMeasuredDimension(width, width + textHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        drawOutLine(canvas, 0, 0, width, width);

        int count = canvas.saveLayer(0, 0, width, width, null, Canvas.ALL_SAVE_FLAG);
        Log.d("guolong", "viewWidth:" + getWidth() + ",viewHeight:" + getHeight() + ",canvas width:" + width + ",canvas height:" + canvas.getHeight());

        r1.left = width / 6f;
        r1.top = width / 6f;
        r1.right = r1.left + width * 2 / 6f;
        r1.bottom = r1.top + width * 2 / 6f;

        r2.left = width * 2 / 6f;
        r2.top = width * 2 / 6f;
        r2.right = width * 4 / 6f;
        r2.bottom = width * 4 / 6f;

        if ("第一次clip区域".equals(style)) {
            drawFirstRegion(canvas, r1);
            Log.d("guolong", "r1:" + r1);
        } else if ("第二次clip区域".equals(style)) {
            drawSecondRegion(canvas, r2);
            Log.d("guolong", "r2:" + r2);
        } else {
            drawFirstRegion(canvas, r1);
            drawSecondRegion(canvas, r2);

            if (android.os.Build.VERSION.SDK_INT >= 26) {
                if (style.equals("DIFFERENCE") || style.equals("INTERSECT")) {
                    canvas.clipRect(r1);
                    canvas.clipRect(r2, op);
                    drawResultRegion(canvas);
                } else {
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(strokeWidth);
                    mPaint.setColor(Color.BLACK);
                    String str = "API 26该模式已废除";
                    float left = width / 2f - mPaint.measureText(str) / 2f;
                    canvas.drawText(str, left, width / 2f, mPaint);
                }
            } else {
                canvas.clipRect(r1);
                canvas.clipRect(r2, op);
                drawResultRegion(canvas);
            }
        }

        canvas.restoreToCount(count);
        drawText(canvas);
    }

    private int strokeWidth = 2;

    private void drawOutLine(Canvas canvas, float left, float top, float right, float bottom) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 画第一次区域
     */
    private void drawFirstRegion(Canvas canvas, RectF r) {
        if ("第一次clip区域".equals(style)) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(colorFirstClip);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(colorOutLine);
            mPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        }
        canvas.drawRect(r, mPaint);
        mPaint.setPathEffect(null);
    }

    /**
     * 画第二次区域
     */
    private void drawSecondRegion(Canvas canvas, RectF r) {
        if ("第二次clip区域".equals(style)) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(colorSecondClip);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(colorOutLine);
            mPaint.setPathEffect(new DashPathEffect(new float[]{15, 5}, 0));
        }
        canvas.drawRect(r, mPaint);
        mPaint.setPathEffect(null);
    }

    /**
     * 画两次Clip后的区域
     */
    private void drawResultRegion(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

        canvas.drawRect(0, 0, getWidth(), getWidth(), mPaint);
    }

    /**
     * 画文字
     */
    private void drawText(Canvas canvas) {
        Log.d("guolong", "drawText:" + style);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(Color.BLACK);
        float textWidth = mPaint.measureText(style);
        float left = getWidth() / 2f - textWidth / 2f;
        canvas.drawText(style, left, getHeight() - 10, mPaint);
    }
}
