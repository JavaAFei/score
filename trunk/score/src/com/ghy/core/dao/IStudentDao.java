package com.ghy.core.dao;

import java.util.Date;
import java.util.List;

import com.ghy.core.entity.Student;


public abstract interface IStudentDao {

	List<Student> getStudent(String name, String examNo);

	Date getLastUpdateTime();

	void saveAll(List<Student> stuList);

	void delete(Student student);

	void deleteBySql(StringBuffer sql1);
	
}
