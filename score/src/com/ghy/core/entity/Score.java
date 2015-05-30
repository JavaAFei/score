package com.ghy.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;;


@Entity
@Table(name="t_score")
public class Score {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(length=32)
	private String id;
	@Column(length=128)
	private String name;
	@Column(length=128)
	private String value;
	@Column(length=3)
	private String sort;
	@Column(length=1)
	private String isShow;
	
	@ManyToOne(targetEntity = Student.class)
	@JoinColumn(name="studentId",updatable=false)
	private Student Student;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public Student getStudent() {
		return Student;
	}
	public void setStudent(Student student) {
		Student = student;
	}
}
