package com.coursemis.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.util.HttpUtil;
import com.coursemis.util.P;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentCheckClassHomeworkActivity extends Activity {

	Intent intent1 =null;
	Button back = null;
	private ListView scch=null;
	int sid;
	private AsyncHttpClient client = new AsyncHttpClient();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_check_class_homework);
		intent1 = getIntent();
		SharedPreferences sharedata=StudentCheckClassHomeworkActivity.this.getSharedPreferences("courseMis", 0);
		sid = Integer.parseInt(sharedata.getString("userID",null));
		scch= (ListView) findViewById(R.id.studentcheckclasshomework_listview);

		final List<String> courseinfol= intent1.getStringArrayListExtra("studentCourseInfo1");
		ArrayList<String> courseinfol_temp = new ArrayList<String>();
		for(int i=0;i<courseinfol.size();i++)
		{
			String temp = courseinfol.get(i);
			String cname = temp.substring(temp.indexOf(" ")+1,temp.indexOf("_"));
			String tname = temp.substring(temp.indexOf("_")+1,temp.length());
			courseinfol_temp.add(cname+"              "+tname+" 老师");
		}
		ArrayAdapter<String> aaRadioButtonAdapter = new ArrayAdapter<String>(
				StudentCheckClassHomeworkActivity.this, android.R.layout.simple_list_item_checked, courseinfol_temp);
		scch.setAdapter(aaRadioButtonAdapter);
		scch.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		scch.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				RequestParams params = new RequestParams();
				params.put("courseinfo", courseinfol.get(arg2)+"");
				params.put("sid", sid+"");
				client.post(HttpUtil.server_student_StudentClassCourseCheckWhichhomework, params,
						new JsonHttpResponseHandler(){
							@Override
							public void onSuccess(int arg0, JSONObject arg1) {
								JSONArray object = arg1.optJSONArray("result");

								if(object.length()==0){
									Toast.makeText(StudentCheckClassHomeworkActivity.this,"这门课程没有任何作业", Toast.LENGTH_SHORT).show();
								}else{
									ArrayList<String> list=new ArrayList<String>();

									for(int i=0;i<arg1.optJSONArray("result").length();i++){
										JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
										P.p(object_temp.toString()+2222);
										list.add(i, object_temp.optInt("smid")+"");
									}

//                                    Intent intent = new Intent(getActivity(),StudentCheckClassHomeworkInfoActivity.class);
//                                    intent.putExtra("courseinfo", courseinfol.get(arg2)+"");
//                                    intent.putStringArrayListExtra("studentCourseHomeworkInfo", list);
//
//                                    startActivity(intent);
								}


								super.onSuccess(arg0, arg1);
							}
						});

			}});







	}


}
