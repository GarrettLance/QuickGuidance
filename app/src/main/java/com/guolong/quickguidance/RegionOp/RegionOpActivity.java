package com.guolong.quickguidance.RegionOp;

import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.guolong.quickguidance.PorterDuff.PorterDuffActivity;
import com.guolong.quickguidance.R;
import com.guolong.quickguidance.base.BaseActivity;
import com.guolong.quickguidance.utils.ScreenUtils;

/**
 * Created by Garrett on 2018/8/26.
 * contact me krouky@outlook.com
 */
public class RegionOpActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_region_op;
    }

    @Override
    protected void initView() {
        actionBarRightSetting();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void actionBarRightSetting() {
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(RegionOpActivity.this);
                TextView tv = new TextView(RegionOpActivity.this);
                tv.setTextSize(22);
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setBackgroundColor(Color.parseColor("#77000000"));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ScreenUtils.getScreenWidth(RegionOpActivity.this) * 2 / 3, ScreenUtils.getScreenHeight(RegionOpActivity.this));
                lp.gravity = Gravity.END | Gravity.TOP;
                tv.setText("说明：\n    API 26废除了除DIFFERENCE和INTERSECT的其他剪裁方式，使用其他剪裁方式会报错，" +
                        "所以如果你的Android版本时26及以上就只看到DIFFERENCE和INTERSECT两种，其他表示已废除。\n" +
                        "步骤：\n   1.开启图层，在图层上裁剪第一个Rect（黄色区域）；\n   2.在图层上通过Region.Op参数剪裁第二个Rect（蓝色区域）；\n" +
                        "    3.画当前View对应的最大Rect，图中红色区域为最终剪裁区域");
                dialog.addContentView(tv, lp);
                dialog.show();
            }
        });
    }
}
