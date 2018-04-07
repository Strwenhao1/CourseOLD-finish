package com.coursemis.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Classroomtestdetail entity. @author MyEclipse Persistence Tools
 */

public class Classroomtestdetail implements java.io.Serializable {

	// Fields

	private Integer ctdId;
	private Integer ctdType;
	private Integer ctdSize;
	private String ctdAnswer;
	private String ctdPath;
	private Set classroomtests = new HashSet(0);

	// Constructors

	/** default constructor */
	public Classroomtestdetail() {
	}

	/** full constructor */
	public Classroomtestdetail(Integer ctdType, Integer ctdSize,
			String ctdAnswer, String ctdPath, Set classroomtests) {
		this.ctdType = ctdType;
		this.ctdSize = ctdSize;
		this.ctdAnswer = ctdAnswer;
		this.ctdPath = ctdPath;
		this.classroomtests = classroomtests;
	}

	// Property accessors

	public Integer getCtdId() {
		return this.ctdId;
	}

	public void setCtdId(Integer ctdId) {
		this.ctdId = ctdId;
	}

	public Integer getCtdType() {
		return this.ctdType;
	}

	public void setCtdType(Integer ctdType) {
		this.ctdType = ctdType;
	}

	public Integer getCtdSize() {
		return this.ctdSize;
	}

	public void setCtdSize(Integer ctdSize) {
		this.ctdSize = ctdSize;
	}

	public String getCtdAnswer() {
		return this.ctdAnswer;
	}

	public void setCtdAnswer(String ctdAnswer) {
		this.ctdAnswer = ctdAnswer;
	}

	public String getCtdPath() {
		return this.ctdPath;
	}

	public void setCtdPath(String ctdPath) {
		this.ctdPath = ctdPath;
	}

	public Set getClassroomtests() {
		return this.classroomtests;
	}

	public void setClassroomtests(Set classroomtests) {
		this.classroomtests = classroomtests;
	}

}