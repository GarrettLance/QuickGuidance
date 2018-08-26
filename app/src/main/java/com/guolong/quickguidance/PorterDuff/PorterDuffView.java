package com.guolong.quickguidance.PorterDuff;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.guolong.quickguidance.R;
import com.guolong.quickguidance.utils.ScreenUtils;

/**
 * Created by Garrett on 2018/8/26.
 * contact me krouky@outlook.com
 */
public class PorterDuffView extends FrameLayout {

    private int textHeight = 50;
    private String title;

    private Paint srcPaint;
    private Paint dstPaint;
    private Paint textPaint;

    public PorterDuffView(@NonNull Context context) {
        this(context, null);
    }

    public PorterDuffView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PorterDuffView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        String style = null;
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PorterDuffView, defStyleAttr, 0);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            switch (ta.getIndex(i)) {
                case R.styleable.PorterDuffView_porter_duff_style:
                    style = ta.getString(i);
                    break;
                default:
                    break;
            }
        }
        initPaint(style);
    }

    private void initPaint(String style) {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);

        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        srcPaint.setColor(0xFFFFCC44);

        dstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dstPaint.setColor(0xFF66AAFF);

        switch (style) {
            case "Source":
                title = "Source";
                dstPaint.setAlpha(0);
                break;
            case "Destination":
                title = "Destination";
                srcPaint.setAlpha(0);
                break;
            case "Source over":
                title = "SrcOver";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                break;
            case "Source in":
                title = "SrcIn";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                break;
            case "Source Atop":
                title = "SrcATop";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                break;
            case "Destination Over":
                title = "DstOver";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                break;
            case "Destination In":
                title = "DstIn";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                break;
            case "Destination Atop":
                title = "DstATop";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                break;
            case "Clear":
                title = "Clear";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                break;
            case "Source Out":
                title = "SrcOut";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                break;
            case "Destination Out":
                title = "DstOut";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                break;
            case "Exclusive Or":
                title = "Xor";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
                break;
            case "Darken":
                title = "Darken";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
                break;
            case "Lighten":
                title = "Lighten";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
                break;
            case "Multiply":
                title = "Multiply";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
                break;
            case "Screen":
                title = "Screen";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
                break;
            case "Overlay":
                title = "Overlay";
                dstPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (ScreenUtils.getScreenWidth(getContext()) - 5 * 6) / 3;
        setMeasuredDimension(width, width + textHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = width;
        drawOutLine(canvas, 0, 0, width, height);

        int layer = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        drawSrcCircle(canvas, width * 3.5f / 9f, height * 3.5f / 9f, width / 3f);
        drawDstRect(canvas, width * 3.5f / 9f, width * 3.5f / 9f, width * 19 / 20f, height * 19 / 20f);
        canvas.restoreToCount(layer);

        drawText(canvas);
    }

    private void drawSrcCircle(Canvas canvas, float cx, float cy, float r) {
        canvas.drawCircle(cx, cy, r, srcPaint);
    }

    private void drawDstRect(Canvas canvas, float left, float top, float right, float bottom) {
        canvas.drawRect(left, top, right, bottom, dstPaint);
    }

    private void drawOutLine(Canvas canvas, int left, int top, int right, int bottom) {
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1);
        canvas.drawRect(left, top, right, bottom, textPaint);
    }

    private void drawText(Canvas canvas) {
        textPaint.setStyle(Paint.Style.FILL);
        float length = textPaint.measureText(title);
        float x = getWidth() / 2 - length / 2;
        canvas.drawText(title, x, getHeight()-10, textPaint);
    }
}
