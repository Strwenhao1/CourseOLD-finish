package com.coursemis.view.Fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coursemis.R;
import com.coursemis.view.myView.TitleView;

import java.util.ArrayList;

/**
 * Created by 74000 on 2018/3/26.
 */




public class StudentCheckSignFragement extends Fragment {



    ArrayList<String> list = null;
    private RecyclerView lv = null;
    private TitleView mTitleView;

    private View mRootView;
    @Override
    public void setArguments(Bundle args) {
        list = args.getStringArrayList("studentCourseSignInInfo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_tcoursesigninactivity,container,false);

        initView() ;
        initData() ;

        return mRootView;
    }

    private void initData() {


        lv.setLayoutManager(new LinearLayoutManager(getActivity())) ;
        lv.setAdapter(new MyAdapter());

    }

    private void initView() {

        lv = (RecyclerView) mRootView.findViewById(R.id.t_coursesignListview);
//        mTitleView = (TitleView) mRootView.findViewById(R.id.course_signin_title);
    }

    private class MyAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_course_signin, parent, false);
            return new MyAdapter.MyViewHolder(inflate );
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            String temp = list.get(position);

            String[]temps = temp.split("\\s+") ;
//            Log.e("temp",temp.split("\\s+").length+"" ) ;
            String number = temps[0] ;
            Log.e("number",number) ;
            String name = temps[1] ;
            Log.e("name",name) ;
            String time = temps[2] ;
            Log.e("time",time) ;
            String totaltime = temps[3] ;
            Log.e("totaltime",totaltime) ;
            MyAdapter.MyViewHolder myViewHolder = (MyAdapter.MyViewHolder) holder;
            myViewHolder.name.setText(name);

            myViewHolder.number.setText(number);
            myViewHolder.time.setText(time);
            myViewHolder.totalTime.setText(totaltime);
            myViewHolder.name.setTextColor(0xFF00FFFF);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView number ;
            public TextView name ;
            public TextView time ;
            public TextView totalTime ;
            public MyViewHolder(View itemView) {
                super(itemView);
                //itemView = View.inflate(TCourseSignInActivity.this,R.layout.item_course_signin,null) ;
                number = (TextView) itemView.findViewById(R.id.student_number);
                name = (TextView) itemView.findViewById(R.id.student_name);
                time = (TextView) itemView.findViewById(R.id.signin_time);
                totalTime = (TextView) itemView.findViewById(R.id.signin_total_time);
            }
        }
    }



}
