package com.coursemis.model;

/**
 * Questionbank entity. @author MyEclipse Persistence Tools
 */

public class Questionbank implements java.io.Serializable {

	// Fields

	private Integer qbId;
	private Course course;
	private Period period;
	private String qbQ;
	private String qbA;
	private String qbO;

	// Constructors

	/** default constructor */
	public Questionbank() {
	}

	/** full constructor */
	public Questionbank(Course course, Period period, String qbQ, String qbA,
			String qbO) {
		this.course = course;
		this.period = period;
		this.qbQ = qbQ;
		this.qbA = qbA;
		this.qbO = qbO;
	}

	// Property accessors

	public Integer getQbId() {
		return this.qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getQbQ() {
		return this.qbQ;
	}

	public void setQbQ(String qbQ) {
		this.qbQ = qbQ;
	}

	public String getQbA() {
		return this.qbA;
	}

	public void setQbA(String qbA) {
		this.qbA = qbA;
	}

	public String getQbO() {
		return this.qbO;
	}

	public void setQbO(String qbO) {
		this.qbO = qbO;
	}

}