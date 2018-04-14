package com.coursemis.model;

/**
 * Score entity. @author MyEclipse Persistence Tools
 */

public class Score implements java.io.Serializable {

	// Fields

	private Integer SId;
	private Course course;
	private Student student;
	private Integer SPid;
	private Integer SAtten;
	private Integer SQuiz;
	private Integer SCall;
	private Integer STest;

	// Constructors

	/** default constructor */
	public Score() {
	}

	/** full constructor */
	public Score(Course course, Student student, Integer SPid, Integer SAtten,
			Integer SQuiz, Integer SCall, Integer STest) {
		this.course = course;
		this.student = student;
		this.SPid = SPid;
		this.SAtten = SAtten;
		this.SQuiz = SQuiz;
		this.SCall = SCall;
		this.STest = STest;
	}

	// Property accessors

	public Integer getSId() {
		return this.SId;
	}

	public void setSId(Integer SId) {
		this.SId = SId;
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

	public Integer getSPid() {
		return this.SPid;
	}

	public void setSPid(Integer SPid) {
		this.SPid = SPid;
	}

	public Integer getSAtten() {
		return this.SAtten;
	}

	public void setSAtten(Integer SAtten) {
		this.SAtten = SAtten;
	}

	public Integer getSQuiz() {
		return this.SQuiz;
	}

	public void setSQuiz(Integer SQuiz) {
		this.SQuiz = SQuiz;
	}

	public Integer getSCall() {
		return this.SCall;
	}

	public void setSCall(Integer SCall) {
		this.SCall = SCall;
	}

	public Integer getSTest() {
		return this.STest;
	}

	public void setSTest(Integer STest) {
		this.STest = STest;
	}

}