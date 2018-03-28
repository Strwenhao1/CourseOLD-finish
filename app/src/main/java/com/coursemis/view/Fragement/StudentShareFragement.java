package com.coursemis.view.Fragement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.thread.FormFile;
import com.coursemis.thread.UploadThread;
import com.coursemis.util.HttpDownloader;
import com.coursemis.util.HttpUtil;
import com.coursemis.view.activity.AudioPlayerActivity;
import com.loopj.android.http.AsyncHttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 74000 on 2018/3/25.
 */


public class StudentShareFragement extends Fragment
        implements View.OnClickListener {

    String TAG = "UploadAudioActivity";
    private static final int RECORD_VIDEO = 1;
    private ListView upa = null;
    int id = 0;
    private String soundPath = null;
    private String sminfo = null;
    Intent intent = null;
    List<String> mediainfolist = null;
    private MediaRecorder mr;
    private AsyncHttpClient client = new AsyncHttpClient();
    private Handler handler = new Handler() {

        @Override

        //当有消息发送出来的时候就执行Handler的这个方法

        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            //处理UI
            Toast.makeText(getActivity(), "资源已经下载", Toast.LENGTH_SHORT).show();

        }

    };

    private Button broad;
    private Button btn;

    /*String string = getArguments().getString("key");*/

    View mRootView;

    @Override
    public void setArguments(Bundle args) {
        mediainfolist = args.getStringArrayList("mediainfolist");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.activity_upload_audio, container, false);
        broad = (Button) mRootView.findViewById(R.id.uploadaudio__broadcast);
        upa = (ListView) mRootView.findViewById(R.id.uploadaudio_listview);
        btn = (Button) mRootView.findViewById(R.id.uploadaudio__down);
        LinearLayout ll = (LinearLayout) mRootView.findViewById(R.id.ua_add);
        ArrayList<String> mediainfolist_temp = new ArrayList<String>();

        for (int i = 0; i < mediainfolist.size(); i++) {
            String temp = mediainfolist.get(i);
            String temp1 = temp.substring(temp.indexOf(" ") + 1, temp.length());
            mediainfolist_temp.add(temp1);

        }


        ArrayAdapter<String> aaRadioButtonAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_checked, mediainfolist_temp);
        upa.setAdapter(aaRadioButtonAdapter);
        upa.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        upa.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                String temp_string = mediainfolist.get(arg2);
                sminfo = temp_string;
                int end = sminfo.lastIndexOf(":");
                String str = sminfo.substring(end + 1, sminfo.length());
                if (!filesExists(str)) {
                    btn.setText("下载");
                    btn.setEnabled(true);
                } else {
                    btn.setText("已下载");
                    btn.setEnabled(false);
                }
            }

        });
        broad.setOnClickListener(this);
        btn.setOnClickListener(this);

        return mRootView;
    }

    public boolean filesExists(String str) {
        try {
            File file = new File("/storage/emulated/legacy/CourseMisMedia/" + str);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        Log.e(TAG, "true: ", null);
        return true;
    }


    public void ButtonOnclick_uploadaudio__down(View view) {
        final String smname = sminfo.substring(sminfo.indexOf(":") + 1, sminfo.length());
        if (sminfo == null) {
            Toast.makeText(getActivity(), "您没有选择哪份作业", Toast.LENGTH_SHORT).show();
        } else {

            final String address = HttpUtil.server + "/mediaShared" + "/" + smname;

            try {
                new Thread() {

                    @Override

                    public void run() {
                        String url = HttpUtil.server + "/mediaShared/" + smname;
                        HttpDownloader downloader = new HttpDownloader();
                        String result = downloader.download(url, "CourseMisMedia/");
                        handler.sendEmptyMessage(0);


                    }

                }.start();

                btn.setText("已下载");
                btn.setEnabled(false);
//				//二进制数据生成位图

//

            } catch (Exception e) {
                Log.e("NetActivity", e.toString());

                Toast.makeText(getActivity(), "下载出错", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void ButtonOnclick_uploadaudio_broadcast(View view) {
        Intent intent = new Intent();// intent可以过滤音频文件
        intent.setType("audio/*");//获取音频文件
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 24);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {


            case 22:
                if (data != null) {

                    Uri uri = data.getData();
                    soundPath = uri.toString();
                    Log.e("获得音频文件！", "uri = " + uri);
                    try {
                        String[] pojo = {MediaStore.Audio.Media.DATA};
                        Cursor cursor = getActivity().managedQuery(uri, pojo, null, null, null);
                        if (cursor != null) {
                            int colunm_index = cursor
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(colunm_index);
                            if (path.endsWith(".mp3") || path.endsWith(".mp4") || path.endsWith("amr"))

                            {
                                soundPath = path;
                                SharedPreferences sharedata = getActivity().getSharedPreferences("courseMis", 0);
                                id = Integer.parseInt(sharedata.getString("userID", null));
                                File file = new File(path);
                                uploadFile(file);


                            } else {
                                alert();
                            }
                        } else {
                            alert();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "您没有进行任何操作", Toast.LENGTH_SHORT).show();
                }
                break;
            case 24:
                try {
                    Uri uri = data.getData();
                    String[] pojo = {MediaStore.Audio.Media.DATA};
                    Cursor cursor = getActivity().managedQuery(uri, pojo, null, null, null);
                    if (cursor != null) {
                        int colunm_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(colunm_index);
                        Intent intent1 = new Intent(getActivity(), AudioPlayerActivity.class);
                        intent1.putExtra("videoPath", path);
                        startActivity(intent1);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "资源没有选中...", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }// onActivityResult结束

    public void uploadFile(File soundFile) {
        Log.i(TAG, "upload start");
        try {
            String requestUrl = HttpUtil.server_ShareMediaData;
            //请求普通信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("userid", id + "");
            //上传文件,第三个参数是struts2接收的参数
            FormFile formfile = new FormFile(soundFile.getName(), soundFile, "video", "audio/mpeg");
            UploadThread uploadThread = new UploadThread(requestUrl, params, formfile);

            Thread t1 = new Thread(uploadThread);
            t1.start();


            Log.i(TAG, "upload success");
            Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i(TAG, "upload error");
            e.printStackTrace();
        }
        Log.i(TAG, "upload end");
    }


    private void alert() {
        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")
                .setMessage("您选择的不是有效的音频文件!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        soundPath = null;
                    }
                }).create();
        dialog.show();
    }

    private void keepDialog(DialogInterface dialog) {
        try {
            dialog.getClass().getSuperclass().getDeclaredField("mShowing").setAccessible(true);
            dialog.getClass().getSuperclass().getDeclaredField("mShowing").set(dialog, false);
//				field.setAccessible(true);
//				field.set(dialog, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void distoryDialog(DialogInterface dialog) {
        try {
            dialog.getClass().getSuperclass().getDeclaredField("mShowing").setAccessible(true);
            dialog.getClass().getSuperclass().getDeclaredField("mShowing").set(dialog, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.uploadaudio__down:
                ButtonOnclick_uploadaudio__down(view);
                break;
            case R.id.uploadaudio__broadcast:
                ButtonOnclick_uploadaudio_broadcast(view);
                break;

        }
    }
}
