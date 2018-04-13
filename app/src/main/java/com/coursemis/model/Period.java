package com.coursemis.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Period entity. @author MyEclipse Persistence Tools
 */

public class Period implements java.io.Serializable {

	// Fields

	private Integer PId;
	private Course course;
	private Integer PNum;
	private Set evaluations = new HashSet(0);
	private Set scores = new HashSet(0);
	private Set questionbanks = new HashSet(0);

	// Constructors

	/** default constructor */
	public Period() {
	}

	/** full constructor */
	public Period(Course course, Integer PNum, Set evaluations, Set scores,
			Set questionbanks) {
		this.course = course;
		this.PNum = PNum;
		this.evaluations = evaluations;
		this.scores = scores;
		this.questionbanks = questionbanks;
	}

	// Property accessors

	public Integer getPId() {
		return this.PId;
	}

	public void setPId(Integer PId) {
		this.PId = PId;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getPNum() {
		return this.PNum;
	}

	public void setPNum(Integer PNum) {
		this.PNum = PNum;
	}

	public Set getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(Set evaluations) {
		this.evaluations = evaluations;
	}

	public Set getScores() {
		return this.scores;
	}

	public void setScores(Set scores) {
		this.scores = scores;
	}

	public Set getQuestionbanks() {
		return this.questionbanks;
	}

	public void setQuestionbanks(Set questionbanks) {
		this.questionbanks = questionbanks;
	}

}