package com.ghy.core.service;

import java.util.Date;
import java.util.List;

import com.ghy.core.entity.Student;

public abstract interface IStudentService {

	List<Student> getStudent(String name, String examNo);

	Date getLastUpdateTime();

	void saveAll(List<Student> stuList);

	void deleteById(String studentId);

	void deleteAll();

}
