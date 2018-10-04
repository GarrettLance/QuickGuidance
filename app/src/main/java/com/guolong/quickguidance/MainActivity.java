package com.guolong.quickguidance;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.guolong.quickguidance.FileOption.FileActivity;
import com.guolong.quickguidance.PorterDuff.PorterDuffActivity;
import com.guolong.quickguidance.RegionOp.RegionOpActivity;
import com.guolong.quickguidance.base.BaseActivity;
import com.guolong.quickguidance.interpolator.InterpolatorActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<Wrapper> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ListView listView = findViewById(R.id.list_view);
        MyAdapter adapter = new MyAdapter(this, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        mList.add(new Wrapper(PorterDuffActivity.class, "PorterDuff.Mode效果"));
        mList.add(new Wrapper(FileActivity.class, "文件操作相关方法"));
        mList.add(new Wrapper(RegionOpActivity.class, "Region.Op剪裁效果"));
        mList.add(new Wrapper(InterpolatorActivity.class, "插值器"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Wrapper wrapper = mList.get(position);
        startActivity(new Intent(this, wrapper.getCls()));
    }

    private class Wrapper {
        Class<?> cls;
        String str;

        Wrapper(Class<?> cls, String str) {
            this.cls = cls;
            this.str = str;
        }

        Class<?> getCls() {
            return cls;
        }

        String getStr() {
            return str;
        }
    }

    class MyAdapter extends ArrayAdapter {

        MyAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view;
            if (convertView == null) {
                view = (TextView) getLayoutInflater().inflate(R.layout.list_item_main, parent, false);
            } else {
                view = (TextView) convertView;
            }
            Wrapper wrapper = mList.get(position);
            view.setText((1 + position) + "." + wrapper.getStr());
            return view;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
