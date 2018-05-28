package com.coursemis.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coursemis.R;
import com.coursemis.util.DialogUtil;
import com.coursemis.util.HttpUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EvaluateAddActivity extends Activity {
    public Context context;
    private AsyncHttpClient client;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private Button back;
    private TextView top_title;
    private Button button_finish;
    private RadioGroup rg_option1;
    private RadioGroup rg_option2;
    private RadioGroup rg_option3;
    private RadioGroup rg_option7;
    private RadioGroup rg_option4;
    private RadioGroup rg_option5;
    private RadioGroup rg_option6;
    private RadioGroup rg_option8;
    private EditText feekback_edit;
    private List<Double> options = new ArrayList<>() ;
    private String studentid;
    private String courseid;
    private String feekback_idea;
    private TextView text_criteria;
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_add);

        this.context = this;
        client = new AsyncHttpClient();
        text_criteria = (TextView) findViewById(R.id.note);

        preferences = getSharedPreferences("courseMis", 0);
        editor = preferences.edit();

        for(int i =0;i<8;i++){
            options.add(i,0.0)  ;
        }


        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        studentid = message.substring(0, message.indexOf("_"));
        //studentid = intent.getExtras().getInt("studentid");
        courseid = message.substring(message.indexOf("_")+1, message.length());

        back = (Button) findViewById(R.id.reback_btn);
//		top_title = (TextView)findViewById(R.id.tv_title);
//		top_title.setText("课程评分");
        button_finish = (Button) findViewById(R.id.evaluatefinish_btn);
        rg_option1 = (RadioGroup) findViewById(R.id.option1);
        rg_option2 = (RadioGroup) findViewById(R.id.option2);
        rg_option3 = (RadioGroup) findViewById(R.id.option3);
        rg_option4 = (RadioGroup)  findViewById(R.id.option4);
        rg_option5 = (RadioGroup)  findViewById(R.id.option5);
        rg_option6 = (RadioGroup)  findViewById(R.id.option6);
        rg_option7 = (RadioGroup)  findViewById(R.id.option7);
        rg_option8 = (RadioGroup)  findViewById(R.id.option8);

//        feekback_edit = (EditText) findViewById(R.id.feekback_edit);
        text_criteria.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EvaluateAddActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                builder.setMessage("教学态度（考查点：教师上课遵纪守时、有无接打电话、仪表仪态、声音和语速等）\n" +
                        "责任心(考查点：教师认真负责、尊重和关心学生的学习和生活，传递正能量、解答相关问题等) \n" +
                        "教学准备（考查点：教学内容设计的合理性、板书的逻辑性、课件质量、教材内容拓展等）\n" +
                        "教学过程（考查点：讲解熟练、表达清晰、教学方法多样、进度合理、激发学生学习兴趣等）\n" +
                        "教学能力（考查点：讲解内容的连续性和完整性、重点和难点的把握、理论联系实际、前沿研究与技术趋势讲解等）\n" +
                        "课堂管理（考查点：严格控制课堂纪律，经常提问与解答，鼓励学生参与教学，提高学生到课率和课堂注意力等）\n" +
                        "课外辅导（考查点：作业批改、讲解是否及时，课后的辅导答疑、对同学专业学习指导和帮助等）\n" +
                        "学习收获（考查点：通过学习，自己是否掌握了该课程的内容，提升了自己的知识能力和专业素养等）\n");
                builder.create().show();

            }
        });

//        for(int i = 0;i<8;i++){
//            options.add(i,i+0.0);
//        }

        rg_option1.setOnCheckedChangeListener(new My());
        rg_option2.setOnCheckedChangeListener(new My());
        rg_option3.setOnCheckedChangeListener(new My());
        rg_option4.setOnCheckedChangeListener(new My());
        rg_option5.setOnCheckedChangeListener(new My());
        rg_option6.setOnCheckedChangeListener(new My());
        rg_option7.setOnCheckedChangeListener(new My());
        rg_option8.setOnCheckedChangeListener(new My());

        button_finish.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                for(int i = 0;i<8;i++){
                    Log.e("sd",options.get(i)+" " );
                }

                // TODO Auto-generated method stub
                if(validate()){
                    RequestParams params = new RequestParams();
                    params.put("sid", studentid+"");
                    params.put("cid", courseid+"");
                    Gson gson = new Gson();
                    params.put("evaluation",  gson.toJson(options));


                    client.post(HttpUtil.server_send_callback, params,
                            new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int arg0, JSONObject arg1) {
                                    // TODO Auto-generated method stub

//                                    String addEvaluate_msg = arg1.optString("result");
//									DialogUtil.showDialog(context, addEvaluate_msg, true);

                                    final AlertDialog.Builder builder=new AlertDialog.Builder(EvaluateAddActivity.this);
                                    builder.setTitle("温馨提示");
//                                    if(addEvaluate_msg.equals("添加评分到该课程成功")){
                                    builder.setMessage("添加评分到该课程成功");
                                    builder.setPositiveButton("退出", new DialogInterface.OnClickListener (){

                                        @Override
                                        public void onClick(DialogInterface arg0,int arg1) {
                                          EvaluateAddActivity.this.finish();


                                        }
                                    });
//										builder.setNegativeButton("继续评教", new DialogInterface.OnClickListener () {
//
//											@Override
//											public void onClick(DialogInterface dialog, int which) {
//												EvaluateAddActivity.this.finish();
//											}
//										});
//                                    }else{
//                                        builder.setMessage(addEvaluate_msg+",请稍后再来");
//                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener (){
//
//                                            @Override
//                                            public void onClick(DialogInterface dialog,int which) {
//                                                Intent intent = new Intent(   getActivity(), StudentManager.class);
//                                                Bundle bundle = new Bundle();
//                                                bundle.putInt("studentid", studentid);
//                                                bundle.putString("type", "学生");
//                                                intent.putExtras(bundle);
//                                                getActivity().startActivity(intent);
//                                                getActivity().finish();
//                                            }
//                                        });
//                                    }
                                    builder.create().show();

                                    super.onSuccess(arg0, arg1);

                                }

                                @Override
                                public void onFailure(String responseBody, Throwable error) {

                                    EvaluateAddActivity.this.finish();

                                    super.onFailure(responseBody, error);

                                }
                            });
                }
            }
        });

    }

    // 对学生输入的评分情况进行校验
    private boolean validate() {
//        feekback_idea = feekback_edit.getText().toString().trim();
//        if (option1 == -1 || option2 == -1 || option3 == -1) {
//            DialogUtil.showDialog(this, "请评分完毕再点击完成！", false);
//            return false;
//        } else if (TextUtils.isEmpty(feekback_idea)) {
//            DialogUtil.showDialog(this, "请填写课程建议", false);
//            return false;
//        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.evaluate_add, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private class My implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int index = -1 ;
            RadioButton viewById = (RadioButton) findViewById(checkedId);
            String content = viewById.getText().toString() ;
            switch (group.getId()){
                case R.id.option1:
                    index = 0 ;
                    break;
                case R.id.option2:
                    index = 1 ;
                    break;
                case R.id.option3:
                    index = 2 ;
                    break;
                case R.id.option4:
                    index = 3 ;
                    break;
                case R.id.option5:
                    index = 4 ;
                    break;
                case R.id.option6:
                    index = 5 ;
                    break;
                case R.id.option7:
                    index = 6 ;
                    break;
                case R.id.option8:
                    index = 7 ;
                    break;

            }
            switch (content) {
                case "A":
                    options.set(index,12.5);
                    break;
                case "B":
                    options.set(index,10.0);
                    break;
                case "C":
                    options.set(index,7.5);
                    break;
                case "D":
                    options.set(index,5.0);
                    break;
                case "E":
                    options.set(index,2.5);
                    break;
                default:
                    break;
            }

        }
    }

}
