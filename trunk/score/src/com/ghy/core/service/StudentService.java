package com.ghy.core.service;

import java.util.Date;
import java.util.List;

import com.ghy.core.dao.IScoreDao;
import com.ghy.core.dao.IStudentDao;
import com.ghy.core.entity.Student;

public class StudentService implements IStudentService{

	private IStudentDao studentDao;
	private IScoreDao scoreDao;

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	public IScoreDao getScoreDao() {
		return scoreDao;
	}

	public void setScoreDao(IScoreDao scoreDao) {
		this.scoreDao = scoreDao;
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

	@Override
	public void deleteById(String studentId) {
		StringBuffer sql  = new StringBuffer();
		sql.append(" DELETE FROM t_score WHERE studentId = '").append(studentId).append("'");
		scoreDao.deleteBySql(sql);
		
		Student student = new Student();
		student.setId(studentId);
		studentDao.delete(student);
	}

	@Override
	public void deleteAll() {
		StringBuffer sql2  = new StringBuffer();
		sql2.append(" DELETE FROM t_score ");
		scoreDao.deleteBySql(sql2);
		
		StringBuffer sql1  = new StringBuffer();
		sql1.append(" DELETE FROM t_student ");
		studentDao.deleteBySql(sql1);
	}

}
