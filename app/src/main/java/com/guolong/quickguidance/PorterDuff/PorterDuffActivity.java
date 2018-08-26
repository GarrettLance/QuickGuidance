package com.guolong.quickguidance.PorterDuff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.guolong.quickguidance.R;
import com.guolong.quickguidance.base.BaseActivity;
import com.guolong.quickguidance.utils.ScreenUtils;

public class PorterDuffActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_porter_duff;
    }

    @Override
    protected void initView() {
        actionBarRightSetting();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void actionBarRightSetting() {
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(PorterDuffActivity.this);
                TextView tv = new TextView(PorterDuffActivity.this);
                tv.setTextSize(22);
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setBackgroundColor(Color.parseColor("#77000000"));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ScreenUtils.getScreenWidth(PorterDuffActivity.this) * 2 / 3, ScreenUtils.getScreenHeight(PorterDuffActivity.this));
                lp.gravity = Gravity.END | Gravity.TOP;
                tv.setText("步骤：\n1.在Canvas上先开启一个图层（Layer）；\n2.在这个图层上画黄色的圆（Source）；\n3.在这个图层上画蓝色的方形（Destination）；\n4.把这个图层加到onDraw()返回的canvas上。");
                dialog.addContentView(tv, lp);
                dialog.show();
            }
        });
    }
}
