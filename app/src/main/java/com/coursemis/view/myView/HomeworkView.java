package com.coursemis.view.myView;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.coursemis.R;

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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Created by zhxchao on 2018/3/15.
 */

public class HomeworkView extends LinearLayout{
    private Context mContext;
    private AttributeSet mAttributeSet;
    private int mDefStyleAttr;
    private ViewPager mPictures;
    private LinearLayout mOperation;
    private int mHeight;
    private int mWidth;
    private boolean mDo = false;
    private int mDownPosition;
    private int mOldPosition;
    private boolean mMoveUp = false;
    private int mOperationTopMarginFirst ;
    private FrameLayout.LayoutParams mLayoutParams;
    private FrameLayout mFrame;
    private View mView;

    public HomeworkView(Context context) {
        this(context, null);
    }

    public HomeworkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeworkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
        initView();
        initData();
    }

    private void initData() {
        //获取到屏幕相关的数据
        WindowManager windows = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windows.getDefaultDisplay();
        mLayoutParams = (FrameLayout.LayoutParams) mOperation.getLayoutParams();
        /*FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mFrame.getLayoutParams();
        mHeight = layoutParams.height ;
        mWidth = layoutParams.width  ;*/
        mWidth = defaultDisplay.getWidth();
        mHeight = defaultDisplay.getHeight();
        /*mView.setOnTouchListener(this);
        mOperation.setOnTouchListener(this);*/
        mLayoutParams.height = mHeight ;
        //设置上划界面的参数
        setFirst();
        //TODO 测试用
        final List<ImageView> imageViews = new ArrayList<>() ;
        ImageView imageView = new ImageView(getContext()) ;
        ImageView imageView1 = new ImageView(getContext()) ;
        Glide.with(getContext()).load(R.drawable.application).into(imageView) ;
        Glide.with(getContext()).load(R.drawable.application).into(imageView1) ;
        imageViews.add(imageView);
        imageViews.add(imageView1);
        mPictures.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView2 = imageViews.get(position);
                mPictures.addView(imageView2);
                return imageView2 ;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ImageView imageView2 = imageViews.get(position);
                mPictures.removeView(imageView2);
            }
        });
        mPictures.addView(imageView);
        mPictures.addView(imageView1);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_homework, this);
        mPictures = (ViewPager) findViewById(R.id.picture);
        mOperation = (LinearLayout) findViewById(R.id.operation);
        mFrame = (FrameLayout) findViewById(R.id.frame);
        mView = findViewById(R.id.view);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        mAttributeSet = attrs;
        mDefStyleAttr = defStyleAttr;
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下事件
                Log.e("测试", "ACTION_DOWN");
                if (!down(event)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("测试", "ACTION_MOVE");
                //移动事件
                move(event);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("测试", "ACTION_UP");
                up(event);
                //抬起事件
                break;
        }
        return true;
    }*/

    private void up(MotionEvent event) {
        float rawY = event.getRawY();
        if (rawY >= (mHeight * 3 / 4)) {
            //停止的位置过低
            //直接恢复到最初状态
            setFirst();
        } else if (mMoveUp) {
            //设置成最终状态
            setEnd();
        } else {
            //设置成初始状态
            setFirst();
        }
        //设置移动为false
        mDo = false;
    }

    private void move(MotionEvent event) {
        //FrameLayout.LayoutParams ly = (FrameLayout.LayoutParams) mOperation.getLayoutParams();
        int topMargin = mLayoutParams.topMargin;
        if (mDo) {
            //设置移动事件
            //判断移动的高度
            int i = mOldPosition - mDownPosition;
            if (mHeight + i < mHeight / 2) {
                //移动高度过大
                mMoveUp = true ;
            } else if (topMargin>=mHeight/2){
                //FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mOperation.getLayoutParams();
                /*Canvas canvas = new Canvas() ;
                canvas.translate(0,-i);
                mOperation.draw(canvas);*/
                mLayoutParams.setMargins(0, mOperationTopMarginFirst + i, 0, mHeight / 2 + i);
                mOperation.setLayoutParams(mLayoutParams);
                //判断移动方向
                if (mOldPosition > event.getRawY()) {
                    //向上移动
                    mMoveUp = true;
                }
                //设置此次的位置
                mOldPosition = (int) event.getRawY();
            }

        }
    }

    private boolean down(MotionEvent event) {
        float rawY = event.getRawY();
        //FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mOperation.getLayoutParams();
        int topMargin = mLayoutParams.topMargin;
        if (rawY >= (mHeight - mHeight / 10)) {
            Log.e("测试", "rawY<=mHeight/10..." + (mHeight - mHeight / 10));
            mDownPosition = (int) rawY;
            mOldPosition = (int) rawY;
            mDo = true;
        } else if (topMargin == mHeight/2&&(rawY>mHeight/2-10&&rawY<mHeight)){
            //下滑操作
            mDownPosition = (int) rawY;
            mOldPosition = (int) rawY;
            mDo = true;
            Log.e("测试","向下移动") ;
        }else {
            return false ;
        }
        return true;
    }

    /**
     * 设置上划界面的初始状态
     */
    private void setFirst() {
        //FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mOperation.getLayoutParams();
        //layoutParams.height = mHeight / 2;
        mLayoutParams.setMargins(0, mHeight, 0, -mHeight / 2);
        mOperation.setLayoutParams(mLayoutParams);
        mOperationTopMarginFirst = mHeight ;
    }

    /**
     * 设置上划界面的最终状态
     */
    private void setEnd() {
        //FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mOperation.getLayoutParams();
        //layoutParams.height = mHeight / 2;
        Log.e("高度",mHeight+"") ;
        mLayoutParams.setMargins(0, 622, 0, 0);
        mOperation.setLayoutParams(mLayoutParams);
        //mOperation.layout(0, (mHeight-mHeight/2), 0, 0);
        mOperationTopMarginFirst = mHeight / 2 ;
    }

    /*@Override
    public boolean onTouch(View v, MotionEvent event) { Log.e("测试", "Id...." + (v.getId() == R.id.view));
        if (v.getId() == R.id.view) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //按下事件
                    Log.e("测试", "ACTION_DOWN");
                    if (!down(event)) {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("测试", "ACTION_MOVE");
                    //移动事件
                    move(event);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e("测试", "ACTION_UP");
                    up(event);
                    //抬起事件
                    break;
            }

        }
        return true;
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //按下事件
                    Log.e("测试", "ACTION_DOWN");
                    if (!down(event)) {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("测试", "ACTION_MOVE");
                    //移动事件
                    move(event);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e("测试", "ACTION_UP");
                    up(event);
                    //抬起事件
                    break;
            }
        return true;
    }
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mDo){
            return false ;
        }
        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("测试",ev.getRawY()+"..."+mLayoutParams.topMargin) ;
        if (ev.getRawY()>mLayoutParams.topMargin-30&&ev.getRawY()<mLayoutParams.topMargin){
            return true ;
        }else {Log.e("测试","hhha") ;
            return false;
        }


    }
}
