package com.coursemis.model;

/**
 * Evaluation entity. @author MyEclipse Persistence Tools
 */

public class Evaluation implements java.io.Serializable {

	// Fields

	private Integer EId;
	private Period period;
	private Course course;
	private String EGrades;

	// Constructors

	/** default constructor */
	public Evaluation() {
	}

	/** full constructor */
	public Evaluation(Period period, Course course, String EGrades) {
		this.period = period;
		this.course = course;
		this.EGrades = EGrades;
	}

	// Property accessors

	public Integer getEId() {
		return this.EId;
	}

	public void setEId(Integer EId) {
		this.EId = EId;
	}

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getEGrades() {
		return this.EGrades;
	}

	public void setEGrades(String EGrades) {
		this.EGrades = EGrades;
	}

}