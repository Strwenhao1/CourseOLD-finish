package com.coursemis.view.Fragement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coursemis.R;
import com.coursemis.util.HttpUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 74000 on 2018/4/8.
 */

public class CourseTest extends Fragment implements View.OnClickListener {

    private int sid;
    private View mRootView;
    private AsyncHttpClient client;
    SharedPreferences sharedata;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_course_test,container,false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void initDate(){
        sid = Integer.parseInt(sharedata.getString("userID",null));
        RequestParams params = new RequestParams();
        params.put("studentid", sid + "");
        client.post(HttpUtil.server_student_StudentClassCourseCheckWhichhomework, params,
                new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        JSONArray object = response.optJSONArray("result");


                        super.onSuccess(statusCode, response);
                    }
                });
    }
    @Override
    public void onClick(View view) {

    }
}
