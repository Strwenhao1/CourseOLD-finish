package com.coursemis.model;

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
 * 发送信息的实体类
 * Created by zhxchao on 2018/3/31.
 */
public class Message {
    /**
     * 第一次进行socket连接时发送的信息
     */
    public static String LOGIN = "login" ;
    /**
     * 心跳包信息
     */
    public static String HEARTBEAT = "heartbeat" ;
    /**
     * 登录
     */
    public static String SIGN_IN = "sign in" ;
    /**
     * 点名信息
     */
    public static String CALL_THE_ROLL = "call the roll" ;
    /**
     * 提问信息
     */
    public static String QUIZ = "quiz" ;
    /**
     * 即时反馈信息
     */
    public static String CALL_BACK = "call back" ;
    /**
     * 测验信息
     */
    public static String TEST = "test" ;


    /**
     * 教师
     */
    public static String TEACHER = "teacher" ;
    /**
     * 学生
     */
    public static String STUDENT = "student" ;

    /**
     * 指定是教师还是学生
     */
    private String tag;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 唯一标识用户的字符串，用用户id+"_"+用户对象的hashcode组成
     */
    private String id ;
    /**
     * 消息内容
     */
    private String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
