package com.ghy.core.service;

import java.util.Date;
import java.util.List;

import com.ghy.core.dao.IStudentDao;
import com.ghy.core.entity.Student;

public class StudentService implements IStudentService{

	private IStudentDao studentDao;

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public List<Student> getStudent(String name, String examNo) {
		return studentDao.getStudent(name,examNo);
	}

	@Override
	public Date getLastUpdateTime() {
		return studentDao.getLastUpdateTime();
	}

	@Override
	public void saveAll(List<Student> stuList) {
		studentDao.saveAll(stuList);
	}

}
