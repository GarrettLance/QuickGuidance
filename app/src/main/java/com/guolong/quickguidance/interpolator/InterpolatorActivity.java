package com.guolong.quickguidance.interpolator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.guolong.quickguidance.R;

import java.util.ArrayList;
import java.util.List;

public class InterpolatorActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();
    private List<MyInterpolator> interpolators = new ArrayList<>();

    private Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolotor);
        Spinner spinner = findViewById(R.id.spinner);
        final View view = findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InterpolatorActivity.this, "view! view! view!", Toast.LENGTH_SHORT).show();
            }
        });

        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                getWindow().getDecorView().removeOnLayoutChangeListener(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setOutlineProvider(new ViewOutlineProvider() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setOval(0, 0, 100, 100);
                        }
                    });
                    view.setClipToOutline(true);
                }
                animator = ObjectAnimator.ofFloat(view, "translationX", 0, right - 100);
                animator.setDuration(1000);
            }
        });

        initList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.mList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View tempView, int position, long id) {
                Interpolator interpolator = interpolators.get(position).getInterpolator();
                animator.setInterpolator(interpolator);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animator.start();
                    }
                }, 300);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(InterpolatorActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initList() {
        interpolators.add(new MyInterpolator(new FastOutLinearInInterpolator()));
        interpolators.add(new MyInterpolator(new FastOutSlowInInterpolator()));
        interpolators.add(new MyInterpolator(new LinearOutSlowInInterpolator()));
        interpolators.add(new MyInterpolator(new LinearInterpolator()));

        for (int i = 0; i < interpolators.size(); i++) {
            mList.add(interpolators.get(i).getInterpolatorName());
        }
    }

    class MyInterpolator {
        private Interpolator mInterpolator;

        MyInterpolator(Interpolator interpolator) {
            this.mInterpolator = interpolator;
        }

        String getInterpolatorName() {
            return mInterpolator.getClass().getSimpleName();
        }

        public Interpolator getInterpolator() {
            return mInterpolator;
        }
    }
}
