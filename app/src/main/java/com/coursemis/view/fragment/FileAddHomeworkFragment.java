package com.coursemis.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.model.Course;

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
 * 添加随堂测验的界面
 * Created by zhxchao on 2018/4/6.
 */
public class FileAddHomeworkFragment extends BaseFragment {

    private Spinner mSpinnerClass;
    private FrameLayout mFlClass;
    private Button mCommit;
    private EditText mName;
    private EditText mAnswer;
    private int mClickItem = 0;

    @Override
    public void refresh(Course course) {

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_file_add_homework, null);
        initView();
        initData();
        return mView;
    }

    private void initData() {
        String[] classes = new String[]{"文字描述", "上传图片"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, classes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerClass.setAdapter(adapter);
        mFlClass.addView(getTextFragment());
        mSpinnerClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFlClass.removeAllViews();
                mClickItem = position;
                switch (position) {
                    case 0:
                        mFlClass.addView(getTextFragment());
                        break;
                    case 1:
                        mFlClass.addView(getImageFragment());
                        break;
                }
            }
        });
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查数据是否完整
                if (!check()){
                    Toast.makeText(getActivity(),"您还没有填写完整",Toast.LENGTH_SHORT).show();
                }else {
                    //上传
                    commit() ;
                }

            }
        });

    }
    //上传信息
    private void commit() {

    }

    //检查数据是否完整
    private boolean check() {
        String name = mName.getText().toString().trim();
        String answer = mAnswer.getText().toString().trim();
        if (!((name!=null||!name.equals(""))&&(answer!=null||!answer.equals("")))){
            return false ;
        }
        switch (mClickItem){
            case 0:
                View textFragment = getTextFragment();
                EditText mDescribe = (EditText) textFragment.findViewById(R.id.et_describe);
                String trim = mDescribe.getText().toString().trim();
                if (!(trim!=null||trim.equals(""))){
                    return false ;
                }
                break;
            case 1:
                break;
        }
        return true ;
    }

    private void initView() {
        mSpinnerClass = (Spinner) mView.findViewById(R.id.s_class);
        mFlClass = (FrameLayout) mView.findViewById(R.id.fl_class);
        mCommit = (Button) mView.findViewById(R.id.commit);
        mName = (EditText) mView.findViewById(R.id.name_course_edit);
        mAnswer = (EditText) mView.findViewById(R.id.et_answer);
    }

    private View getTextFragment() {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.view_add_homework_text_fragment, null);
        return view;
    }

    private View getImageFragment() {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.view_add_homework_text_fragment, null);
        return view;
    }

}
