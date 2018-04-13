package com.coursemis.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coursemis.R;
import com.coursemis.model.Classroomtest;
import com.coursemis.model.Course;
import com.coursemis.model.Score;
import com.coursemis.util.HttpUtil;
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
    private RecyclerView mScoreList = null;
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
        mClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tid", mTeacher.getTId() + "");
        params.put("cid", mCourse.getCId() + "");
        params.put("type","all");
        //Log.id("点击", tid+"...."+uriString);
        mClient.post(HttpUtil.server_get_score, params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {

                        JSONArray object = arg1.optJSONArray("result") ;
                        Gson gson = new Gson() ;
                        List<Score> scores = new ArrayList<>() ;
                        List<Score> list = gson.fromJson(object.toString(), scores.getClass());
                        Log.e("hahhah",".....>>>>>"+list.size()) ;
                        mScoreList.setAdapter(new ScoreAdapter(list));
                        super.onSuccess(arg0, arg1);
                    }
                });
       mScoreList.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initView() {

        mScoreList = (RecyclerView) mView.findViewById(R.id.homeworkmanagecourseinfoListview);

    }

    private class ScoreAdapter extends RecyclerView.Adapter{

        private List<Score> list ;

        public ScoreAdapter(List<Score> list) {
            this.list = list ;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
