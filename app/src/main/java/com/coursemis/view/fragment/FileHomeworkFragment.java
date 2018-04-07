package com.coursemis.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.model.Classroomtest;
import com.coursemis.model.Course;
import com.coursemis.service.WebService;
import com.coursemis.util.HttpUtil;
import com.coursemis.util.P;
import com.coursemis.view.activity.HomeworkManageCourseSelectInfoActivity;
import com.coursemis.view.activity.HomeworkManageJobInfoActivity;
import com.coursemis.view.activity.ImageActivity;
import com.coursemis.view.activity.ImageViewActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * 作业管理的界面
 * Created by zhxchao on 2018/3/18.
 */

public class FileHomeworkFragment extends BaseFragment {



    String sm = null;
    private ListView hms = null;
    byte[] data = null;
    private AsyncHttpClient mClient;
    private ArrayList<Classroomtest> mClassroomTestList;


    @Override
    public void refresh(Course course) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_file_homework, null);
        initView();
        initData();
        return mView;
    }

    private void initData() {
        Log.e("测试", "初始化数据" + (mCourse == null));
        mClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tid", mTeacher.getTId() + "");
        params.put("cid", mCourse.getCId() + "");
        //Log.id("点击", tid+"...."+uriString);
        mClient.post(HttpUtil.server_teacher_homework_select, params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        JSONArray object = arg1.optJSONArray("result");
                        if (object.length() == 0) {
                            Toast.makeText(getActivity(), "这门课程您还没有布置过作业", Toast.LENGTH_SHORT).show();
                        } else {
                            mClassroomTestList = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0; i < arg1.optJSONArray("result").length(); i++) {
                                JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
                                Classroomtest classroomtest = gson.fromJson(object_temp.toString(), Classroomtest.class);
                                mClassroomTestList.add(classroomtest);
                                List<String> sml_temp = new ArrayList<String>();
                                ArrayAdapter<String> aaRadioButtonAdapter = new ArrayAdapter<String>(
                                        getActivity(), android.R.layout.simple_list_item_2, sml_temp);
                                hms.setAdapter(aaRadioButtonAdapter);
                                hms.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                            }
                        }

                        super.onSuccess(arg0, arg1);
                    }
                });
        hms.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub

            }

        });
        /*check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (text3 == null) {
                    Toast.makeText(getActivity(), "您没有选择哪份作业", Toast.LENGTH_SHORT).show();
                } else {
                    P.p("insert");
                    RequestParams params = new RequestParams();
                    params.put("sminfo", text3);
                    mClient.post(HttpUtil.server_teacher_check_homework, params,
                            new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int arg0, JSONObject arg1) {
                                    JSONArray object = arg1.optJSONArray("result");
                                    P.p(object.toString() + 1111);

                                    if (object.length() == 0) {
                                        Toast.makeText(getActivity(), "您还没有学生提交这门作业", Toast.LENGTH_SHORT).show();
                                    } else {
                                        ArrayList<String> list = new ArrayList<String>();
                                        for (int i = 0; i < arg1.optJSONArray("result").length(); i++) {
                                            JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
                                            P.p(object_temp.toString() + 2222);
                                            list.add(i, (object_temp.optInt("shid") + " " + object_temp.optString("shname")));
                                        }

                                        Intent intent = new Intent(getActivity(), HomeworkManageJobInfoActivity.class);
                                        intent.putStringArrayListExtra("smlist", list);
                                        intent.putExtra("title", text3);

                                        startActivity(intent);
                                    }

                                    super.onSuccess(arg0, arg1);
                                }
                            });
                }


            }
        });*/

    }

    private void initView() {

        hms = (ListView) mView.findViewById(R.id.homeworkmanagecourseinfoListview);

    }

    /**
     * 保存到sd卡上的方法
     */
    public void saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".JPG");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.v("在保存图片时出错：", e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
