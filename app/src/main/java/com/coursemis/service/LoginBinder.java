package com.coursemis.service;

import android.os.Binder;

import java.io.Serializable;

import static com.coursemis.service.LoginService.STUDENT;
import static com.coursemis.service.LoginService.TEACHER;

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
public class LoginBinder extends Binder {
    public void setMess(String type,Serializable serializable){
        switch (type){
            case STUDENT :
                break;
            case TEACHER :
                break;
        }
    }
    public void init(){

    }
}
