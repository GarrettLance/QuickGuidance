package com.guolong.quickguidance.FileOption;

import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guolong.quickguidance.R;
import com.guolong.quickguidance.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class FileActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private MyAdapter mAdapter;

    private void requestDir() {
        mList.add("1.getFilesDir() " + "\n" + getFilesDir().getAbsolutePath() + "\n" + "存储内部文件的文件系统目录的绝对路径");
        mList.add("2.getCacheDir()" + "\n" + getCacheDir().getAbsolutePath() + "\n" + "临时缓存文件保存到的内部目录");

        mList.add("3.getDir(\"test\",MODE_PRIVATE)" + "\n" + getDir("test", MODE_PRIVATE).getAbsolutePath() + "\n" + "在您的内部存储空间内创建（或打开现有的）目录");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mList.add("4.getDataDir()" + "\n" + getDataDir().getAbsolutePath() + "\n" + "内部存储器应用的根目录");
        }
        mList.add("5.getExternalCacheDir()" + "\n" + getExternalCacheDir().getAbsolutePath() + "\n" + "外部存储器上该应用缓存临时文件的私有目录");
        mList.add("6.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)" + "\n" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "\n" + "获取设备外部存储器公共的音乐目录");
        mList.add("7.getExternalFilesDir(null)" + "\n" + getExternalFilesDir(null).getAbsolutePath() + "\n" + "外部存储器上该应用的私有目录的根目录");

        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        requestDir();
    }
}
