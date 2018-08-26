package com.guolong.quickguidance.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.guolong.quickguidance.R;

/**
 * Created by Garrett on 2018/8/26.
 * contact me krouky@outlook.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected View contentView;
    protected TextView leftTextView;
    protected TextView rightTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBarSetting();

        contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(contentView);

        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected String title() {
        return getClass().getSimpleName();
    }

    protected abstract void initView();

    protected abstract void initData();

    public void actionBarLeftSetting() {
    }

    public void actionBarRightSetting() {
    }

    private void actionBarSetting() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_view);
        TextView titleMain = actionBar.getCustomView().findViewById(R.id.title_main);
        leftTextView = actionBar.getCustomView().findViewById(R.id.left_text);
        rightTextView = actionBar.getCustomView().findViewById(R.id.right_text);
        titleMain.setText(title());
    }

}
