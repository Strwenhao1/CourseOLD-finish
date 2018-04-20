package com.coursemis.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.A;
import com.coursemis.R;
import com.coursemis.model.*;
import com.coursemis.model.Test;
import com.coursemis.util.HttpUtil;
import com.coursemis.util.P;
import com.coursemis.view.Fragement.EvaluateFragement;
import com.coursemis.view.Fragement.StudentCheckSignFragement;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 74000 on 2018/4/14.
 */

public class StudentTestActivity extends Activity {

    private String ca=null;
    private String TAG = "StudentTestActivity";
    private List<Integer> an = new ArrayList<>();
    String sid = null;
    String cid = null;
    private int cc;
    String message = null;
    private Button submit;
    int cou=0;
    private AsyncHttpClient client = new AsyncHttpClient();
    private RecyclerView rv;
    ArrayList<Questionbank> list1 = new ArrayList<Questionbank>();
    MyAdapter myadapter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_test);
        Intent i = getIntent();
//        lv = (ListView) findViewById(R.id.lv);
        message = i.getStringExtra("message");
        rv = (RecyclerView) findViewById(R.id.rv);
        submit = (Button) findViewById(R.id.submit);
        sid = message.substring(0, message.indexOf("_"));
        cid = message.substring(message.indexOf("_") + 1, message.length());
        Log.e(TAG, sid + "   " + cid, null);
        RequestParams params = new RequestParams();
        params.put("sid", sid+"");
        params.put("cid", sid+"");

        client.post(HttpUtil.server_get_test, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        JSONArray object = response.optJSONArray("result");

                        if (object.length() == 0) {
                            Toast.makeText(StudentTestActivity.this, "当前时间没有测验信息", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e(TAG, object.toString(), null);
                            for (int i = 0; i < object.length(); i++) {
                                JSONObject object_temp = response.optJSONArray("result").optJSONObject(i);
                                Gson gson = new Gson();
                                Questionbank questionbank = gson.fromJson(object_temp.toString(), Questionbank.class);
                                list1.add(questionbank);
                            }
                            myadapter = new MyAdapter();
                            rv.setLayoutManager(new LinearLayoutManager(StudentTestActivity.this));
                            rv.setAdapter(myadapter);

                        }
                        Log.e(TAG, list1 + "");
                        super.onSuccess(statusCode, response);
                    }
                }
        );

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0;i < an.size();i++){
                    cou+=an.get(i);
                }
                Log.e(TAG, cou+"",null);
                RequestParams params = new RequestParams();
                params.put("sid", sid+"");
                params.put("cid", sid+"");
                params.put("result",cou);
                client.post(HttpUtil.server_send_test, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        Toast.makeText(StudentTestActivity.this,"success",Toast.LENGTH_SHORT).show();
                        StudentTestActivity.this.finish();
                        }

                    @Override
                    public void onFailure(String responseBody, Throwable error) {
                        Log.e(TAG, "onFailure: " );
                        super.onFailure(responseBody, error);
                    }
                });
            }
        });



    }





    private class MyAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(StudentTestActivity.this).inflate(R.layout.selecter, parent,false);

            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final Questionbank temp = list1.get(position);
            Log.e(TAG, position+" " );
            Gson gson = new Gson();
            String tt = temp.getQbO().substring(1, temp.getQbO().length() - 1);
            com.coursemis.model.Test test = gson.fromJson(tt, com.coursemis.model.Test.class);
            String question = temp.getQbQ();
            String quesA = test.getA();
            String quesB = test.getB();
            String quesC = test.getC();
            String quesD = test.getD();
            StudentTestActivity.MyAdapter.MyViewHolder myViewHolder = (StudentTestActivity.MyAdapter.MyViewHolder) holder;
            myViewHolder.question.setText(position+1+"."+question);
            myViewHolder.quesA.setText("A"+"."+quesA);
            myViewHolder.quesB.setText("B"+"."+quesB);
            myViewHolder.quesC.setText("C"+"."+quesC);
            myViewHolder.quesD.setText("D"+"."+quesD);
            myViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {

                    switch (CheckedId){
                        case R.id.quesA:
                            if (temp.getQbA().equals("A")){
                                an.add(position,10);
                            }else {
                                an.add(position,0);
                            }

                            break;
                        case R.id.quesB:
                            if (temp.getQbA().equals("B")){
                                an.add(position,10);
                            }else {
                                an.add(position,0);
                            }
                            break;
                        case R.id.quesC:
                            if (temp.getQbA().equals("C")){
                                an.add(position,10);
                            }else {
                                an.add(position,0);
                            }
                            break;
                        case R.id.quesD:
                            if (temp.getQbA().equals("D")){
                                an.add(position,10);
                            }else {
                                an.add(position,0);
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {

            return list1.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView question;
            public RadioGroup radioGroup;
            public RadioButton quesA;
            public RadioButton quesB;
            public RadioButton quesC;
            public RadioButton quesD;

            public MyViewHolder(View itemView) {
                super(itemView);
                question = (TextView) itemView.findViewById(R.id.question);
                radioGroup = (RadioGroup) itemView.findViewById(R.id.choise);
                quesA = (RadioButton) itemView.findViewById(R.id.quesA);
                quesB = (RadioButton) itemView.findViewById(R.id.quesB);
                quesC = (RadioButton) itemView.findViewById(R.id.quesC);
                quesD = (RadioButton) itemView.findViewById(R.id.quesD);



//                WindowManager wm1 = StudentTestActivity.this.getWindowManager();
//                int width1 = wm1.getDefaultDisplay().getWidth();
//                number.setGravity(Gravity.CENTER);
//                number.setWidth(width1/4);
//                number.setTextSize(18);
//                name.setGravity(Gravity.CENTER);
//                name.setWidth(width1/4);
//                name.setTextSize(18);
//                time.setGravity(Gravity.CENTER);
//                time.setWidth(width1/4);
//                time.setTextSize(18);
//                totalTime.setGravity(Gravity.CENTER);
//                totalTime.setWidth(width1/4);
//                totalTime.setTextSize(18);
            }
        }
    }

}