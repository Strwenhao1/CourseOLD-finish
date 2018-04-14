package com.coursemis.model;

/**
 * Evaluation entity. @author MyEclipse Persistence Tools
 */

public class Evaluation implements java.io.Serializable {

	// Fields

	private Integer EId;
	private Course course;
	private Integer EPid;
	private String EGrades;

	// Constructors

	/** default constructor */
	public Evaluation() {
	}

	/** full constructor */
	public Evaluation(Course course, Integer EPid, String EGrades) {
		this.course = course;
		this.EPid = EPid;
		this.EGrades = EGrades;
	}

	// Property accessors

	public Integer getEId() {
		return this.EId;
	}

	public void setEId(Integer EId) {
		this.EId = EId;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getEPid() {
		return this.EPid;
	}

	public void setEPid(Integer EPid) {
		this.EPid = EPid;
	}

	public String getEGrades() {
		return this.EGrades;
	}

	public void setEGrades(String EGrades) {
		this.EGrades = EGrades;
	}

}