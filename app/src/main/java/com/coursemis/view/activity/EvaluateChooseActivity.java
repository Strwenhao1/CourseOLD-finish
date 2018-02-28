package com.coursemis.view.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.coursemis.R;
import com.coursemis.adapter.CourseAdapter;
import com.coursemis.model.Course;
import com.coursemis.util.HttpUtil;
import com.coursemis.view.myView.TitleView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author Sivan
 * @2017-5-4下午7:56:22
 * @描述 教师选择要查看教学反馈的课程
 */
public class EvaluateChooseActivity extends Activity {

    //private Button			bt_back;
    private ListView lv_course_teacher;
    private AsyncHttpClient client;
    private int teacherid;
    private CourseAdapter courseAdapter;
    private List<Course> courseList = new ArrayList<Course>();
    private TitleView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_evaluate_choose);
        mTitleView = (TitleView) findViewById(R.id.evaluate_choose_title);
        lv_course_teacher = (ListView) findViewById(R.id.lv_course_teacher);
    }

    private void initData() {

        client = new AsyncHttpClient();
        Intent intent = getIntent();
        teacherid = intent.getExtras().getInt("teacherid");

        if (courseAdapter == null) {
            courseAdapter = new CourseAdapter(this, courseList, teacherid);
        }
        lv_course_teacher.setAdapter(courseAdapter);
        RequestParams params = new RequestParams();
        params.put("tid", teacherid + "");
        client.post(HttpUtil.server_teacher_course, params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        for (int i = 0; i < arg1.optJSONArray("result").length(); i++) {
                            Course course = new Course();
                            JSONObject object = arg1.optJSONArray("result").optJSONObject(i);
                            course.setCId(object.optInt("CId"));
                            course.setCName(object.optString("CName"));
                            courseList.add(course);
                        }
                        courseAdapter.notifyDataSetChanged();
                    }
                });

        initTitle();
    }

    private void initTitle() {
        mTitleView.setTitle("查看课程反馈");
        mTitleView.setLeftButton("返回", new TitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {
                EvaluateChooseActivity.this.finish();
            }
        });
    }
}
