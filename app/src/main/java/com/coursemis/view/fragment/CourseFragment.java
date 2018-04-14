package com.coursemis.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.listener.SignInClickListener;
import com.coursemis.model.AskStudent;
import com.coursemis.model.Course;
import com.coursemis.model.Coursetime;
import com.coursemis.model.Student;
import com.coursemis.util.HttpUtil;
import com.coursemis.util.P;
import com.coursemis.view.activity.TAskQuestionActivity;
import com.coursemis.view.activity.TSecondActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * Created by zhxchao on 2018/3/13.
 */

public class CourseFragment extends BaseFragment
        implements View.OnClickListener {

    private AsyncHttpClient client;
    private TextView name_course;
    private TextView weeknum_course;
    private TextView address_course;
    private TextView daytime_course;
    private Coursetime mCoursetime;
    private String[] mDay;
    private String[] mWeek;
    private SwipeRefreshLayout mRefresh;
    private Button mQuiz;
    private Button mCallName;
    private Button mSignIn;
    private Button mFeedBack;
    private AlertDialog mQuizAlert;
    private Button mTest;
    private Button mScore;
    private AlertDialog mAlertDialog;

    @Override
    public void refresh(Course course) {
        mCourse = course;
        mSignIn.setOnClickListener(new SignInClickListener(getActivity(), mTeacher, mCourse));
        mCallName.setOnClickListener(this);
        mQuiz.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);
        mTest.setOnClickListener(this);
        mScore.setOnClickListener(this);
        refresh();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_course, null);
        initView();
        initData();
        return mView;
    }

    private void initData() {
        client = new AsyncHttpClient();
        mWeek = new String[]{"单周", "双周", "每周"};
        mDay = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCourse != null) {
                    refresh();
                }
            }
        });

    }

    private void initView() {
        name_course = (TextView) mView.findViewById(R.id.name_course);
        weeknum_course = (TextView) mView.findViewById(R.id.weeknum_course);
        address_course = (TextView) mView.findViewById(R.id.address_course);
        daytime_course = (TextView) mView.findViewById(R.id.daytime_course);
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.refresh);
        mQuiz = (Button) mView.findViewById(R.id.quiz);
        mCallName = (Button) mView.findViewById(R.id.callName);
        mSignIn = (Button) mView.findViewById(R.id.signIn);
        mFeedBack = (Button) mView.findViewById(R.id.feedBack);
        mTest = (Button) mView.findViewById(R.id.test);
        mScore = (Button) mView.findViewById(R.id.score);
    }

    @Override
    public void onStart() {
        if (mCoursetime != null) {
            name_course.setText(mCoursetime.getCourse().getCName());
            String week_day = mWeek[mCoursetime.getCtWeekChoose() - 1] + mDay[mCoursetime.getCtWeekDay() - 1];
            weeknum_course.setText(week_day);
            address_course.setText(mCoursetime.getCtAddress());
            String daytime = "第" + mCoursetime.getCtStartClass() + "节至第" + mCoursetime.getCtEndClass() + "节";
            daytime_course.setText(daytime);
        }
        super.onStart();
    }

    private void refresh() {
        RequestParams params = new RequestParams();
        params.put("courseid", mCourse.getCId() + "");
        params.put("action", "course_teacher");///
        client.post(HttpUtil.server_course_info, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        JSONObject object = arg1.optJSONArray("result").optJSONObject(0);
                        //TODO 需要删除的临时性代码
                        if (object == null) {
                            //空数据
                            Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Gson gson = new Gson();
                        mCoursetime = gson.fromJson(object.toString(), Coursetime.class);
                        mCoursetime.setCtWeekDay(object.optInt("CtWeekDay"));
                        mCoursetime.setCtWeekChoose(object.optInt("CtWeekChoose"));
                        mCoursetime.setCtEndClass(object.optInt("CtEndClass"));
                        mCoursetime.setCtStartClass(object.optInt("CtStartClass"));
                        mCoursetime.setCtAddress(object.optString("CtAddress"));
                        name_course.setText(mCoursetime.getCourse().getCName());
                        String week_day = mWeek[mCoursetime.getCtWeekChoose() - 1] + mDay[mCoursetime.getCtWeekDay() - 1];
                        weeknum_course.setText(week_day);
                        address_course.setText(mCoursetime.getCtAddress());
                        String daytime = "第" + mCoursetime.getCtStartClass() + "节至第" + mCoursetime.getCtEndClass() + "节";
                        daytime_course.setText(daytime);
                        mRefresh.setRefreshing(false);
                        super.onSuccess(arg0, arg1);
                    }

                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callName:
                //点名
                RequestParams params = new RequestParams();
                params.put("cid", mCourse.getCId() + "");
                client.post(HttpUtil.server_teacher_course_studentNames, params,
                        new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, JSONObject arg1) {
                                JSONArray object = arg1.optJSONArray("result");
                                P.p(object.toString() + 1111);

                                if (object.length() == 0) {
                                    Toast.makeText(getActivity(), "您这门课没有学生选修!", Toast.LENGTH_SHORT).show();
                                } else {
                                    final Map<String, Integer> map = new HashMap<>();
                                    ArrayList<String> list = new ArrayList<String>();
                                    Gson gson = new Gson();
                                    for (int i = 0; i < arg1.optJSONArray("result").length(); i++) {
                                        JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
                                        P.p(object_temp.toString() + 2222);
                                        list.add(i, (object_temp.optInt("SNumber") + (object_temp.optString("SName"))));
                                        map.put(object_temp.optString("SName"), object_temp.optInt("SNumber"));
                                    }
                                    final ArrayList<String> select = new ArrayList<String>();
                                    final String[] strings = map.keySet().toArray(new String[map.keySet().size()]);
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                            .setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                    select.add(strings[which]);
                                                }
                                            })
                                            .setPositiveButton("点名", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    RequestParams params = new RequestParams();
                                                    params.put("type", "call");
                                                    params.put("size", select.size() + "");
                                                    //Log.e("测试。。",select.get(0)+"...."+map.get(select.get(0))) ;
                                                    for (int i = 0; i < select.size(); i++) {
                                                        params.put(i + "", map.get(select.get(i)) + "");
                                                    }
                                                    client.post(HttpUtil.server_teacher_courseStudentCount, params,
                                                            new JsonHttpResponseHandler() {
                                                                @Override
                                                                public void onSuccess(int arg0, JSONObject arg1) {
                                                                    Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                                                                    super.onSuccess(arg0, arg1);
                                                                }
                                                            });
                                                }
                                            })
                                            .create();
                                    alertDialog.show();
                                }

                                super.onSuccess(arg0, arg1);
                            }
                        });

                break;
            case R.id.quiz:
                //提问
                RequestParams p = new RequestParams();
                p.put("cid", mCourse.getCId() + "");
                p.put("tid", mTeacher.getTId() + "");
                client.post(HttpUtil.server_send_quiz_message, p, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        //弹出不可取消的对话框
                        //Toast.makeText(getActivity(),"弹出对话框",Toast.LENGTH_SHORT).show();
                        //JSONArray object = arg1.optJSONArray("result");
                        //String s = object.optString(0);
                        try {
                            final String result = arg1.getJSONObject("result").getString("sid");
                            final EditText etScore = new EditText(getActivity());
                            etScore.setInputType(InputType.TYPE_CLASS_NUMBER);
                            //获取到成绩
//存在数据
//发送数据
                            mQuizAlert = new AlertDialog.Builder(getActivity())
                                    .setTitle("学号为" + result + "的分数")
                                    .setView(etScore)
                                    .setCancelable(false)
                                    .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //获取到成绩
                                            String trim = etScore.getText().toString().trim();
                                            if (trim != null) {
                                                //存在数据
                                                //发送数据
                                                RequestParams p = new RequestParams();
                                                p.put("tid", mTeacher.getTId() + "");
                                                p.put("cid", mCourse.getCId() + "");
                                                p.put("sid", result + "");
                                                p.put("score", trim);
                                                client.post(HttpUtil.server_send_quiz_score, p, new JsonHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int statusCode, JSONObject response) {
                                                        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                                                        mQuizAlert.dismiss();
                                                    }
                                                });
                                            }
                                        }
                                    }).create();
                            mQuizAlert.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            case R.id.feedBack:
                //反馈
                String[] items = new String[]{"反馈情况", "发送消息"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, items);
                AlertDialog d = new AlertDialog.Builder(getActivity())
                        .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("teacher", mTeacher);
                                intent.putExtra("course", mCourse);
                                intent.setClass(getActivity(), TSecondActivity.class);
                                switch (which) {
                                    case 0:
                                        intent.putExtra(TSecondActivity.TYPE, TSecondActivity.LineChart);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        //向学生端发送一条消息
                                        RequestParams p = new RequestParams();
                                        p.put("cid", mCourse.getCId() + "");
                                        p.put("tid", mTeacher.getTId() + "");
                                        client.post(HttpUtil.server_send_callback_message, p, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, JSONObject response) {
                                                Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        break;
                                }
                            }
                        }).create();
                d.show();
                break;
            case R.id.test:
                //发送消息
                mAlertDialog = new AlertDialog.Builder(getActivity())
                        .setMessage("是否发送本次课的随堂测验信息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //发送消息
                                sendTestMess() ;
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAlertDialog.dismiss();
                            }
                        }).create();
                mAlertDialog.show();
                break;
        }
    }
    private void sendTestMess() {
        AsyncHttpClient client = new AsyncHttpClient() ;
        RequestParams params = new RequestParams() ;
        params.put("tid",mTeacher.getTId()+"");
        params.put("cid",mCourse.getCId()+"");
        client.post(HttpUtil.server_send_test_message,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                Toast.makeText(getActivity(),"发送成功",Toast.LENGTH_SHORT).show();
            }
        }) ;
    }
}
