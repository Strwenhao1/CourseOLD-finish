package com.coursemis.model;

import java.sql.Timestamp;

/**
 * Classroomtest entity. @author MyEclipse Persistence Tools
 */

public class Classroomtest implements java.io.Serializable {

	// Fields

	private Integer ctId;
	private Course course;
	private Classroomtestdetail classroomtestdetail;
	private String ctName;
	private Integer ctScore;
	private Timestamp ctDateTime;

	// Constructors

	/** default constructor */
	public Classroomtest() {
	}

	/** full constructor */
	public Classroomtest(Course course,
			Classroomtestdetail classroomtestdetail, String ctName,
			Integer ctScore, Timestamp ctDateTime) {
		this.course = course;
		this.classroomtestdetail = classroomtestdetail;
		this.ctName = ctName;
		this.ctScore = ctScore;
		this.ctDateTime = ctDateTime;
	}

	// Property accessors

	public Integer getCtId() {
		return this.ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Classroomtestdetail getClassroomtestdetail() {
		return this.classroomtestdetail;
	}

	public void setClassroomtestdetail(Classroomtestdetail classroomtestdetail) {
		this.classroomtestdetail = classroomtestdetail;
	}

	public String getCtName() {
		return this.ctName;
	}

	public void setCtName(String ctName) {
		this.ctName = ctName;
	}

	public Integer getCtScore() {
		return this.ctScore;
	}

	public void setCtScore(Integer ctScore) {
		this.ctScore = ctScore;
	}

	public Timestamp getCtDateTime() {
		return this.ctDateTime;
	}

	public void setCtDateTime(Timestamp ctDateTime) {
		this.ctDateTime = ctDateTime;
	}

}