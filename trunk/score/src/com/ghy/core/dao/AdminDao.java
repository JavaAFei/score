package com.ghy.core.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Query;

import com.ghy.core.entity.Admin;

public class AdminDao implements IAdminDao{
	
	private SessionFactory sessionFactory;
	
	public List<Admin> getAdminByLoginCodeAndPassword(String loginCode,
			String password) {
		Query query = this.sessionFactory.getCurrentSession().createQuery
				(" from Admin where state = '0' and loginCode = :loginCode and password = :password ");
		query.setString("loginCode", loginCode);
		query.setString("password", password);
		return query.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
 
	public void update(Admin admin) {
		this.sessionFactory.getCurrentSession().update(admin);
	}

}
