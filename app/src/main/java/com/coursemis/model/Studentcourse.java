package com.coursemis.model;

/**
 * Studentcourse entity. @author MyEclipse Persistence Tools
 */

public class Studentcourse implements java.io.Serializable {
    private Integer scId;
    private Course course;
    private Student student;
    private Integer scPointNum;
    private Integer scPointTotalNum;


    public Studentcourse() {
    }

    public Studentcourse(Course course, Student student, Integer scPointNum,
                         Integer scPointTotalNum) {
        this.course = course;
        this.student = student;
        this.scPointNum = scPointNum;
        this.scPointTotalNum = scPointTotalNum;
    }

    // Property accessors

    public Integer getScId() {
        return this.scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getScPointNum() {
        return this.scPointNum;
    }

    public void setScPointNum(Integer scPointNum) {
        this.scPointNum = scPointNum;
    }

    public Integer getScPointTotalNum() {
        return this.scPointTotalNum;
    }

    public void setScPointTotalNum(Integer scPointTotalNum) {
        this.scPointTotalNum = scPointTotalNum;
    }

}