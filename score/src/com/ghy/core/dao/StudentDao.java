package com.ghy.core.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import com.ghy.core.entity.Student;

public class StudentDao    implements IStudentDao{
 
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Student> getStudent(String name, String examNo) {
		
		Query query = sessionFactory.getCurrentSession().createQuery
				(" from Student where name = :name and examNo = :examNo ");
		query.setString("name", name);
		query.setString("examNo", examNo);
		return query.list();
	}

	@Override
	public Date getLastUpdateTime() {
		Query query = this.sessionFactory.getCurrentSession().createQuery
				(" select max(updateTime) from Student ");
		List list = query.list();
		if(list !=null && list.size()>0){
			Date lastUpdateTime =list.get(0)==null ? null: (Date) list.get(0);
			return lastUpdateTime;
		}
		return null;
	}

	@Override
	public void saveAll(List<Student> stuList) {
		//TODO 优化批量插入
		for(Student stu:stuList){
			 this.sessionFactory.getCurrentSession().save(stu);
		}
	}

	@Override
	public void delete(Student student) {
		 this.sessionFactory.getCurrentSession().delete(student);
	}
	
	@Override
	public void deleteBySql(StringBuffer sql) {
		Session s = this.sessionFactory.getCurrentSession();
		Query q = s.createSQLQuery(sql.toString());
		q.executeUpdate();
	}
}