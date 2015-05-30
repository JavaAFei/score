package com.ghy.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="t_student")
public class Student {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(length=32)
	private String id;
	@Column(length=128)
	private String name;
	@Column(length=128)
	private String examNo;
	@Column(length=32)
	private String creator;
	private Date cteateTime;
	@Column(length=32)
	private String updater;
	private Date updateTime;
	
	@OneToMany(targetEntity=Score.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="studentId",updatable=false)
	private Set<Score> sets = new HashSet<Score>();
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExamNo() {
		return examNo;
	}
	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCteateTime() {
		return cteateTime;
	}
	public void setCteateTime(Date cteateTime) {
		this.cteateTime = cteateTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Set<Score> getSets() {
		return sets;
	}
	public void setSets(Set<Score> sets) {
		this.sets = sets;
	}
	
}
