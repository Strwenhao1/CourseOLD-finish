package com.coursemis.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.coursemis.R;
import com.coursemis.model.Course;
import com.coursemis.model.Teacher;
import com.coursemis.view.fragment.BaseFragment;
import com.coursemis.view.fragment.SettingAddCourseFragment;
import com.coursemis.view.fragment.SettingCourseSettingFragment;
import com.coursemis.view.fragment.SettingStudentManagerFragment;

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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Created by zhxchao on 2018/3/22.
 */

public class TSettingActivity extends AppCompatActivity {

    public static final String TYPE = "type" ;
    public static final String COURSESETTING = "课程管理" ;
    public static final String STUDENTMANAGER = "学生管理" ;
    public static final String ADDCOURSE = "添加课程" ;



    private Toolbar mTitle;
    private FrameLayout mContent;
    private Teacher mTeacher;
    private Course mCourse;
    private String mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent() ;
        initView() ;
        initData() ;
    }

    private void initData() {
        mTitle.setTitle(mType);
        mTitle.setTitleTextColor(Color.WHITE);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        BaseFragment fragment ;
        Bundle bundle = new Bundle() ;
        bundle.putSerializable("teacher",mTeacher);
        bundle.putSerializable("course",mCourse);
        switch (mType){
            case COURSESETTING:
                fragment = new SettingCourseSettingFragment() ;
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content,fragment) ;
                fragmentTransaction.commit() ;
                break;
            case STUDENTMANAGER:
                fragment = new SettingStudentManagerFragment() ;
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content,fragment) ;
                fragmentTransaction.commit() ;
                break;
            case ADDCOURSE:
                fragment = new SettingAddCourseFragment() ;
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content,fragment) ;
                fragmentTransaction.commit() ;
                break;
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        mTeacher = (Teacher) intent.getSerializableExtra("teacher");
        mCourse = (Course) intent.getSerializableExtra("course");
        mType = intent.getStringExtra(TYPE);
    }

    private void initView() {
        setContentView(R.layout.activity_t_setting);
        mTitle = (Toolbar) findViewById(R.id.title);
        mContent = (FrameLayout) findViewById(R.id.content);
    }
}
