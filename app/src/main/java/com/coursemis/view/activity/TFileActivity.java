package com.coursemis.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.coursemis.R;
import com.coursemis.model.Course;
import com.coursemis.model.Teacher;
import com.coursemis.view.fragment.FileHomeworkFragment;
import com.coursemis.view.fragment.FileResourceFragment;

/**
 * _oo0oo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * 0\  =  /0
 * ___/`---'\___
 * .' \\|     |// '.
 * / \\|||  :  |||// \
 * / _||||| -:- |||||- \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |_/ |
 * \  .-\__  '-'  ___/-. /
 * ___'. .'  /--.--\  `. .'___
 * ."" '<  `.___\_<|>_/___.' >' "".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `_.   \_ __\ /__ _/   .-` /  /
 * =====`-.____`.___ \_____/___.-`___.-'=====
 * `=---='
 * <p>
 * <p>
 * 在文件中点击“作业”或“资源共享”跳转到此activity
 * Created by zhxchao on 2018/3/18.
 */

public class TFileActivity extends AppCompatActivity {


    public static final String HOMEWORK = "作业";
    public static final String RESOURCE = "资源管理";
    public static final String TYPE = "type";

    private FrameLayout mFragment;
    private Toolbar mToolBar;
    private Teacher mTeacher;
    private Course mCourse;
    private String mType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mTeacher = (Teacher) intent.getSerializableExtra("teacher");
        mCourse = (Course) intent.getSerializableExtra("course");
        mType = intent.getStringExtra(TYPE);
    }

    private void initData() {
        Log.e("测试","初始化数据"+mType) ;
        mToolBar.setTitle(mType);
        mToolBar.setTitleTextColor(Color.WHITE);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable("teacher", mTeacher);
        bundle.putSerializable("course", mCourse);
        switch (mType) {
            case HOMEWORK:
                fragment = new FileHomeworkFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                break;
            case RESOURCE:
                fragment = new FileResourceFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                break;
        }
    }

    private void initView() {
        setContentView(R.layout.activity_t_file);
        mFragment = (FrameLayout) findViewById(R.id.content);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
    }
}
