package com.coursemis.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.coursemis.R;
import com.coursemis.model.Message;
import com.coursemis.model.Student;
import com.coursemis.model.Teacher;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

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
 * Created by zhxchao on 2018/3/31.
 */
public class LoginService extends Service {


    public static final String TYPE = "type";
    public static final String STUDENT = "student";
    public static final String TEACHER = "teacher";


    private String mSendMess;
    private String mHeartBeat;
    private String mMess;
    private WebSocket mWebSocket;
    private Timer mTimer = new Timer();

    private MyBinder myBinder;
    private OkHttpClient mOkHttpClient;
    private String mMessage;

    private MessageHandler mMessageHandler;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String stringExtra = intent.getStringExtra(TYPE);
        Serializable serializableExtra = intent.getSerializableExtra(stringExtra);
        int id = -1;
        switch (stringExtra) {
            case TEACHER:
                Teacher teacher = (Teacher) serializableExtra;
                mMess = teacher.getTId() + "_" + teacher.hashCode();
                id = teacher.getTId();
                break;
            case STUDENT:
                Student student = (Student) serializableExtra;
                mMess = student.getSId() + "_" + student.hashCode();
                id = student.getSId();
                break;
        }
        Gson gson = new Gson();
        Message login = new Message();
        login.setTag(stringExtra);
        login.setType(Message.LOGIN);
        login.setId(mMess);
        login.setUserId(id);
        mSendMess = gson.toJson(login);
        Message heard = new Message();
        heard.setTag(stringExtra);
        heard.setType(Message.HEARTBEAT);
        heard.setId(mMess);
        heard.setUserId(id);
        mHeartBeat = gson.toJson(heard);
        String url = "ws://zhxchao.ngrok.xiaomiqiu.cn/CourseMis/socket";
        Request request = new Request.Builder().url(url).build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mWebSocket = mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService();
        myBinder = new MyBinder();
        return myBinder;
    }

    @Override
    public void onCreate() {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
    }


    public class MyBinder extends Binder {
        public void onMessage(MessageHandler messageHandler) {
            mMessageHandler = messageHandler;
        }
    }


    public interface MessageHandler {
        void onMessage( Message message);
    }


    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            WebSocket mSocket = webSocket;
            mSocket.send(mSendMess);

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            mMessage = text;
            Gson gson = new Gson();
            Message message = gson.fromJson(text, Message.class);
            if (!(message.getType().equals(Message.HEARTBEAT))) {
                mMessageHandler.onMessage(message);
            }
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mWebSocket.send(mHeartBeat);
                }
            }, 2500);

        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            webSocket.send(mHeartBeat);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
        }
    }

    private void showNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(LoginService.this);
        mBuilder.setContentTitle("点名")//设置通知栏标题
                .setContentText("你被点名了") //<span style="font-family:Arial;">/设置通知栏显示内容</span>
                .setContentIntent(pendingIntent) //设置通知栏点击意图
                //.setNumber(10) //设置通知集合的数量
                .setTicker("你被点名了") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_VIBRATE Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
        mNotificationManager.notify(NotificationManager.INTERRUPTION_FILTER_ALARMS, mBuilder.build());
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        mWebSocket.close(1000, "关闭");
        super.onDestroy();
    }
}
