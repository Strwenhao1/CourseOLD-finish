package com.coursemis.view.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.coursemis.R;
import com.coursemis.adapter.ContentAdapter;
import com.coursemis.model.ContentModel;
import com.coursemis.model.LocationData;
import com.coursemis.model.Message;
import com.coursemis.model.Student;
import com.coursemis.service.LoginService;
import com.coursemis.util.HttpUtil;
import com.coursemis.util.P;
import com.coursemis.view.Fragement.ClassHomeworkFragement;
import com.coursemis.view.Fragement.CourseTableFragement;
import com.coursemis.view.Fragement.CourseValuate_s;
import com.coursemis.view.Fragement.EvaluateFragement;
import com.coursemis.view.Fragement.HomeworkManagerFragement;
import com.coursemis.view.Fragement.StudentCheckSignFragement;
import com.coursemis.view.Fragement.StudentShareFragement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.WebSocket;

public class StudentManager extends FragmentActivity {

    private ListView listView;

    private DrawerLayout drawerLayout;
    private AsyncHttpClient client;
    SharedPreferences sharedata;
    private Button student_checkSign = null;
    private Button student_signIn = null;
    private Button student_checkHomework = null;
    private Button button_coursetable;
    private Button button_evaluate;
    private int sid;
    private TextView mTv = null;
    FragmentManager manager = getSupportFragmentManager();
    private Button mStartBtn;
    private boolean mIsStart;
    private static int count = 1;
    private Vibrator mVibrator01 = null;
    private LocationClient mLocClient;
    public static String TAG = "LocTestDemo11";
    private ImageView Toolbar;
    final List<String> list = new ArrayList<String>();
    private TextView title;
    private List<ContentModel> list11 = new ArrayList<ContentModel>();;
    private NotificationManager manager1;
    private int notification_id;
    private int sid1;
    private int cid1;
    private String mes;  //学号_课程号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.active_leftslide);
        LocationData.latitude = 0.0;
        LocationData.longitude = 0.0;
        LocationData.radius = 0.0f;
        //百度定位参数设置
        mTv = (TextView) findViewById(R.id.student_location);
        mStartBtn = (Button) findViewById(R.id.startStudentLoc);
        mIsStart = false;
        mLocClient = ((Location) getApplication()).mLocationClient;
        ((Location) getApplication()).mTv = mTv;
        mVibrator01 = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        ((Location) getApplication()).mVibrator01 = mVibrator01;
        client = new AsyncHttpClient();
        sharedata = getSharedPreferences("courseMis", 0);
        sid = Integer.parseInt(sharedata.getString("userID", null));
        student_checkSign = (Button) findViewById(R.id.student_checkSign);
        student_signIn = (Button) findViewById(R.id.s_signIn);
        student_checkHomework = (Button) findViewById(R.id.student_checkhomework);
        button_coursetable = (Button) findViewById(R.id.coursetablemanage);
        button_evaluate = (Button) findViewById(R.id.courseevaluate_stu);
        title = (TextView) findViewById(R.id.title);
        Toolbar = (ImageView) findViewById(R.id.Toolbar);
        manager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });
        initView();
        bindService() ;
    }

    private void bindService() {
        Intent intent = new Intent() ;
        Student student = new Student() ;
        student.setSId(sid);
        intent.putExtra(LoginService.TYPE,LoginService.STUDENT) ;
        intent.putExtra(LoginService.STUDENT,student) ;
        intent.setClass(this,LoginService.class) ;
        bindService(intent,connection,BIND_AUTO_CREATE) ;
    }
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LoginService.MyBinder myBinder = (LoginService.MyBinder) service;
            myBinder.onMessage(new LoginService.MessageHandler() {

                public void onMessage(Message message) {
                    Log.e("测试1111",message.getType()) ;

                    if(message.getType().equals(Message.TEST)){

                        mes = sid+"_"+message.getMessage().substring(0, message.getMessage().indexOf("_"));
                        Log.e(TAG, sid1+" "+cid1,null );
                        Notification.Builder builder = new Notification.Builder(StudentManager.this);
                        builder.setSmallIcon(R.drawable.icon_course_name);
                        builder.setTicker("测验");
                        builder.setWhen(System.currentTimeMillis());
                        builder.setContentTitle("随堂测试");
                        builder.setContentText("请点击进行随堂测试");
                        Intent intent = new Intent(StudentManager.this, StudentTestActivity.class);
                        intent.putExtra("message",mes);
                        PendingIntent ma = PendingIntent.getActivity(StudentManager.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(ma);//设置点击过后跳转的activity
                        builder.setDefaults(Notification.DEFAULT_ALL);//设置全部
                        Notification notification = null;//4.1以上用.build();

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            notification = builder.build();
                        }
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                        manager1.notify(notification_id,notification);

                    }else if(message.getType().equals(Message.QUIZ)){
                        mes = message.getMessage();
                        Log.e(TAG, "onMessage: "+mes );
                        Notification.Builder builder = new Notification.Builder(StudentManager.this);
                        builder.setSmallIcon(R.drawable.icon_course_name);
                        builder.setTicker("提问");
                        builder.setWhen(System.currentTimeMillis());
                        builder.setContentTitle("提问");
                        builder.setContentText("请起立，回答老师提问");
                        builder.setDefaults(Notification.DEFAULT_ALL);//设置全部
                        Notification notification = null;//4.1以上用.build();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            notification = builder.build();
                        }
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                        manager1.notify(notification_id,notification);
                    }else if(message.getType().equals(Message.CALL_BACK)){

                        mes = sid+"_"+message.getMessage().substring(0, message.getMessage().indexOf("_"));
                        Log.e(TAG, mes,null );
                        Notification.Builder builder = new Notification.Builder(StudentManager.this);
                        builder.setSmallIcon(R.drawable.icon_course_name);
                        builder.setTicker("反馈");
                        builder.setWhen(System.currentTimeMillis());
                        builder.setContentTitle("反馈");
                        builder.setContentText("请对这节课进行评教");
                        Intent intent = new Intent(StudentManager.this, EvaluateAddActivity.class);
                        intent.putExtra("message",mes);
                        PendingIntent ma = PendingIntent.getActivity(StudentManager.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(ma);//设置点击过后跳转的activity
                        builder.setDefaults(Notification.DEFAULT_ALL);//设置全部
                        Notification notification = null;//4.1以上用.build();

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            notification = builder.build();
                        }
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                        manager1.notify(notification_id,notification);

                    }else if(message.getType().equals(Message.CALL_THE_ROLL)){

                        Notification.Builder builder = new Notification.Builder(StudentManager.this);
                        builder.setSmallIcon(R.drawable.icon_course_name);
                        builder.setTicker("点名");
                        builder.setWhen(System.currentTimeMillis());
                        builder.setContentTitle("点名");
                        builder.setContentText("已被点名");
                        builder.setDefaults(Notification.DEFAULT_ALL);//设置全部
                        Notification notification = null;//4.1以上用.build();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            notification = builder.build();
                        }
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                        manager1.notify(notification_id,notification);
                    }else if(message.getType().equals(Message.SIGN_IN)){

                        Notification.Builder builder = new Notification.Builder(StudentManager.this);
                        builder.setSmallIcon(R.drawable.icon_course_name);
                        builder.setTicker("签到信息");
                        builder.setWhen(System.currentTimeMillis());
                        builder.setContentTitle("请立即签到");
                        builder.setContentText("签到正在进行");
                        builder.setDefaults(Notification.DEFAULT_ALL);//设置全部
                        Notification notification = null;//4.1以上用.build();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            notification = builder.build();
                        }
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                        manager1.notify(notification_id,notification);
                    }
                }
            });
        }
    };

    private void initView() {
        listView = (ListView) findViewById(R.id.v4_listview);
        drawerLayout = (DrawerLayout) findViewById(R.id.v4_drawerlayout);
        initDate();
    }
//阿斯顿


    private void initDate() {
//
        list.add("学生签到");
        list.add("资源共享");
        list.add("课表管理");

        list.add("随堂评测");
        list.add("到课情况");
        list.add("我在哪里");
        list.add(("定位"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list, list);
//        list11.add(new ContentModel(R.drawable.a,"asd",1));
//        list11.add(new ContentModel(R.drawable.a,"asd",2));
//        list11.add(new ContentModel(R.drawable.a,"asd",3));
//        ContentAdapter adapter = new ContentAdapter(this,list11);


        listView.setAdapter(adapter);
        listView.setBackgroundColor(0xf0f);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        signin();
                        break;
                    case 1:
                        title.setText("资源共享");
                        share();
                        break;
                    case 2:
                        title.setText("课程表");
                        courseTable();
                        break;
                    case 3:
                        title.setText("随堂评测");
                        homework_class();
                        break;
                    case 4:
                        title.setText("签到统计");
                        checkStudent();
                        break;
                    case 5:
                        title.setText("签到统计");
                        student_where();
                        break;
                    case 6:
                        title.setText("Location");
                        loc();
                        break;
                    case 7:
                        loc();
                        break;
                }
//                textView.setText(list.get(position));
                showDrawerLayout();
            }
        });
        drawerLayout.openDrawer(Gravity.LEFT);//侧滑打开  不设置则不会默认打开

    }

    public void signin() {
        setLocationOption();
        mLocClient.start();
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (LocationData.latitude == 0.0 || LocationData.longitude == 0.0) {
                    Toast.makeText(StudentManager.this, "请先获取您的位置信息之后再尝试签到。", Toast.LENGTH_SHORT).show();
                } else {

                    RequestParams params = new RequestParams();
                    params.put("sid", sid + "");
                    params.put("latitude", LocationData.latitude + "");
                    params.put("longitude", LocationData.longitude + "");
                    client.post(HttpUtil.server_student_SignIn, params,
                            new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int arg0, JSONObject arg1) {
                                    JSONArray object = arg1.optJSONArray("result");
                                    Log.e(TAG, object.length() + "", null);
                                    if (object.length() == 0) {
                                        Toast.makeText(StudentManager.this, "暂时没有需要课程需要签到!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(StudentManager.this, "签到成功", Toast.LENGTH_SHORT).show();
//                                        final ArrayList<String> list = new ArrayList<String>();
//
//                                        for (int i = 0; i <= arg1.optJSONArray("result").length(); i++) {
//                                            JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
//                                            if (object_temp != null && object_temp.optString("SCId") != null) {
//                                                list.add(i, object_temp.optString("SCId"));
//                                            }
//                                        }
//
//
//                                        if (list.size() == 0) {
//                                            AlertDialog dialog = new AlertDialog.Builder(StudentManager.this)
//                                                    .setTitle("消息").setMessage("您当前没有课程需要点到！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                                                        @Override
//                                                        public void onClick(DialogInterface arg0, int arg1) {
//                                                            // TODO Auto-generated method stub
//
//                                                        }
//                                                    })//在这里把写好的这个listview的布局加载dialog中
//                                                    .create();
//                                            P.p("执行到磁珠了吗");
//                                            dialog.show();
//                                        } else {
//                                            if (LocationData.latitude == 0.0 || LocationData.longitude == 0.0) {
//                                                Toast.makeText(StudentManager.this, "请先获取您的位置信息之后再尝试签到。", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                AlertDialog dialog = new AlertDialog.Builder(StudentManager.this)
//                                                        .setTitle("消息").setMessage("当前有课程需要签到，您需要立即签到吗！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                                                            @Override
//                                                            public void onClick(DialogInterface arg0, int arg1) {
//                                                                // TODO Auto-generated method stub
//                                                                RequestParams params = new RequestParams();
//                                                                P.p("这里2    " + list.size());
//                                                                for (int i = 0; i < list.size(); i++) {
//                                                                    params.put(i + "", list.get(i));
//                                                                    P.p("i is " + i + "   " + list.get(i));
//                                                                }
//                                                                params.put("size", list.size() + "");
//                                                                params.put("latitude", LocationData.latitude + "");
//                                                                params.put("longitude", LocationData.longitude + "");
//                                                                P.p("这里1");
//                                                                client.post(HttpUtil.server_student_SignInComfirm, params,
//                                                                        new JsonHttpResponseHandler() {
//                                                                            @Override
//                                                                            public void onSuccess(int arg0, JSONObject arg1) {
//                                                                                JSONObject object = arg1.optJSONObject("result");
//                                                                                String success = object.optString("success");
//                                                                                if (success == "您没有在课堂附近签到") {
//                                                                                    Toast.makeText(StudentManager.this, "您没有在课堂附近签到!", Toast.LENGTH_SHORT).show();
//                                                                                } else {
//                                                                                    if (success != null) {
//                                                                                        Toast.makeText(StudentManager.this, "签到成功!", Toast.LENGTH_SHORT).show();
//                                                                                    } else {
//                                                                                        Toast.makeText(StudentManager.this, "签到失败!", Toast.LENGTH_SHORT).show();
//                                                                                    }
//                                                                                }
//                                                                                super.onSuccess(arg0, arg1);
//                                                                            }
//                                                                        });
//                                                            }
//                                                        })//在这里把写好的这个listview的布局加载dialog中
//                                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                                                            @Override
//                                                            public void onClick(DialogInterface arg0, int arg1) {
//                                                                // TODO Auto-generated method stub
//
//                                                            }
//                                                        }).create();
//                                                dialog.show();
//
//                                            }
//                                        }
                                    }
                                    super.onSuccess(arg0, arg1);
                                }

                                @Override
                                public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {
                                    Toast.makeText(StudentManager.this, "签到失败", Toast.LENGTH_SHORT).show();
                                    super.onFailure(statusCode, e, errorResponse);
                                }
                            });
                }
                mLocClient.stop();

            }

        }, 2000);

    }

    public void share() {

        FragmentTransaction transaction = manager.beginTransaction();
        StudentShareFragement fragment = new StudentShareFragement();
        transaction.replace(R.id.v4_drawerlayout_frame, fragment);
        transaction.commit();

    }

    public void courseTable() {
        FragmentTransaction transaction = manager.beginTransaction();
        CourseTableFragement fragment = new CourseTableFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("studentid", sid);// ("fragData",fragData);
        fragment.setArguments(bundle);
        transaction.replace(R.id.v4_drawerlayout_frame, fragment);
        transaction.commit();
    }

    public void courseValuate() {

        FragmentTransaction transaction = manager.beginTransaction();
        CourseValuate_s fragment = new CourseValuate_s();
        Bundle bundle = new Bundle();
        bundle.putInt("studentid", sid);// ("fragData",fragData);
        fragment.setArguments(bundle);
        transaction.replace(R.id.v4_drawerlayout_frame, fragment);
        transaction.commit();

    }

    public void homework_class() {
//        SharedPreferences sharedata = getSharedPreferences("courseMis", 0);
//        sid = Integer.parseInt(sharedata.getString("userID", null));
//
//        RequestParams params = new RequestParams();
//        params.put("sid", sid + "");
//        client.post(HttpUtil.server_student_StudentCourse, params,
//                new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, JSONObject arg1) {
//                        JSONArray object = arg1.optJSONArray("result");
//
//                        if (object.length() == 0) {
//                            Toast.makeText(StudentManager.this, "您没有选修任何课程!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            ArrayList<String> list = new ArrayList<String>();
//
//                            for (int i = 0; i < arg1.optJSONArray("result").length(); i++) {
//                                JSONObject object_temp = arg1.optJSONArray("result").optJSONObject(i);
//                                P.p(object_temp.toString() + 2222);
//                                list.add(i, (object_temp.optInt("CNumber") + " " + object_temp.optString("CName") + "_" + object_temp.optString("CTname")));
//                            }
//                            FragmentTransaction transaction = manager.beginTransaction();
//                            ClassHomeworkFragement fragment = new ClassHomeworkFragement();
//                            Bundle bundle = new Bundle();
//                            bundle.putStringArrayList("studentCourseInfo1", list);// ("fragData",fragData);
//                            fragment.setArguments(bundle);
//                            transaction.replace(R.id.v4_drawerlayout_frame, fragment);
//                            transaction.commit();
//                            Intent i = new Intent(StudentManager.this,StudentCheckClassHomeworkActivity.class);
//                            i.putStringArrayListExtra("studentCourseInfo1", list);
//                            P.p(list+"");
//                            startActivity(i);

//                        }
//
//
//                        super.onSuccess(arg0, arg1);
//                    }
//                });
        Intent i = new Intent(StudentManager.this, StudentTestActivity.class);
        i.putExtra("message", mes);
        startActivity(i);

    }

    public void checkStudent() {
        FragmentTransaction transaction = manager.beginTransaction();
        StudentCheckSignFragement fragment = new StudentCheckSignFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("sid", sid);// ("fragData",fragData);
        fragment.setArguments(bundle);
        transaction.replace(R.id.v4_drawerlayout_frame, fragment);
        transaction.commit();
    }

    public void student_where() {


        if (LocationData.latitude != 0.0 || LocationData.longitude != 0.0) {
            Intent intent = new Intent(StudentManager.this, MapActivity.class);
            intent.putExtra("latitude", LocationData.latitude);
            intent.putExtra("longitude", LocationData.longitude);
            intent.putExtra("radius", LocationData.radius);
            startActivity(intent);
        } else {
            Toast.makeText(StudentManager.this, "您还没有定位哦，无法获得您的位置信息哟。", Toast.LENGTH_SHORT).show();
        }
    }

    public void loc() {
        if (!mIsStart) {
            setLocationOption();
            mLocClient.start();
            Toast.makeText(getApplicationContext(), "定位开始，请耐心等待",
                    Toast.LENGTH_SHORT).show();
            mIsStart = true;

        } else {
            mLocClient.stop();
            mIsStart = false;

            Toast.makeText(getApplicationContext(), "定位结束",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.disableCache(true);
        option.setCoorType("bd09ll");
        option.setPoiNumber(10);
//        option.disableCache(false);
        option.setPriority(LocationClientOption.NetWorkFirst);
        mLocClient.setLocOption(option);

    }


    private void showDrawerLayout() {
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void onDestroy() {
        mLocClient.stop();
        ((Location) getApplication()).mTv = null;
        unbindService(connection);
        super.onDestroy();
    }


}