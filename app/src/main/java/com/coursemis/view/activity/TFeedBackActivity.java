package com.coursemis.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


import com.coursemis.R;
import com.coursemis.model.Course;
import com.coursemis.model.Teacher;
import com.coursemis.view.fragment.BaseFragment;
import com.coursemis.view.fragment.FeedBackHistogramFragment;
import com.coursemis.view.fragment.FeedBackLineChartFragment;
import com.coursemis.view.fragment.FeedBackSectorChartFragment;

import java.io.Serializable;

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
 *显示学生反馈的界面
 * Created by zhxchao on 2018/3/17.
 */

public class TFeedBackActivity extends AppCompatActivity {

    //Intent传参时的标志
    public final static String TAG = "feedback" ;
    //柱状图
    public final static String Histogram = "柱状图" ;
    //折线图
    public final static String LineChart = "折线图" ;
    //扇形图
    public final static String SectorChart = "扇形图" ;
    //学生反馈
    public final static String StudentFeedBack = "学生反馈" ;

    private Toolbar mTitle;
    private FrameLayout mFeedBack;
    private Teacher mTeacher;
    private Course mCourse;
    private String mTag;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent() ;
        initView() ;
        initData() ;
        initFragment() ;
    }

    private void initFragment() {
        BaseFragment fragment ;
        Bundle bundle = new Bundle() ;
        bundle.putSerializable("teacher",mTeacher);
        bundle.putSerializable("course",mCourse);
        switch (mTag){
            case Histogram :
                fragment = new FeedBackHistogramFragment() ;
                fragment.setArguments(bundle);
                mTransaction.replace(R.id.feedBack,fragment) ;
                mTransaction.commit() ;
                break;
            case LineChart :
                fragment = new FeedBackLineChartFragment();
                fragment.setArguments(bundle);
                mTransaction.replace(R.id.feedBack,fragment) ;
                mTransaction.commit() ;
                break;
            case SectorChart :
                Log.e("测试","SectorChart") ;
                fragment = new FeedBackSectorChartFragment();
                fragment.setArguments(bundle);
                mTransaction.replace(R.id.feedBack,fragment) ;
                mTransaction.commit() ;
                break;
            case StudentFeedBack :
                break;
        }
    }

    private void initData() {
        mTitle.setTitle(mTag);
        mTitle.setTitleTextColor(Color.WHITE);
        mFragmentManager = getSupportFragmentManager() ;
        mTransaction = mFragmentManager.beginTransaction();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mTeacher = (Teacher) intent.getSerializableExtra("teacher");
        mCourse = (Course) intent.getSerializableExtra("course");
        mTag = intent.getStringExtra(TAG) ;
        Log.e("测试TAG",mTag) ;
    }

    private void initView() {
        setContentView(R.layout.activity_t_feedback);
        mTitle = (Toolbar) findViewById(R.id.title);
        mFeedBack = (FrameLayout) findViewById(R.id.feedBack);
    }
}
