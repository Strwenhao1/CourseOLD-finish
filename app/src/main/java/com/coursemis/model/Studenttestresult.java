package com.coursemis.model;

/**
 * Studenttestresult entity. @author MyEclipse Persistence Tools
 */

public class Studenttestresult implements java.io.Serializable {

	// Fields

	private Integer strId;
	private Student student;
	private Classroomtest classroomtest;
	private String strAnswer;

	// Constructors

	/** default constructor */
	public Studenttestresult() {
	}

	/** full constructor */
	public Studenttestresult(Student student, Classroomtest classroomtest,
			String strAnswer) {
		this.student = student;
		this.classroomtest = classroomtest;
		this.strAnswer = strAnswer;
	}

	// Property accessors

	public Integer getStrId() {
		return this.strId;
	}

	public void setStrId(Integer strId) {
		this.strId = strId;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Classroomtest getClassroomtest() {
		return this.classroomtest;
	}

	public void setClassroomtest(Classroomtest classroomtest) {
		this.classroomtest = classroomtest;
	}

	public String getStrAnswer() {
		return this.strAnswer;
	}

	public void setStrAnswer(String strAnswer) {
		this.strAnswer = strAnswer;
	}

}