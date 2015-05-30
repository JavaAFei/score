package com.ghy.core.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.ghy.core.entity.ShowOption;


public class ShowOptionDao implements IShowOptionDao{
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void updateBySql(String sql) {
		Query query= this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	@Override
	public List<Object[]> queryBySql(StringBuffer sql) {
		return this.sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).list();
	}

	@Override
	public ShowOption getById(String id) {
		Query query = this.sessionFactory.getCurrentSession().createQuery
				(" from ShowOption where id = :id ");
		query.setString("id", id);
		List<ShowOption> list = query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(ShowOption showOption) {
		this.sessionFactory.getCurrentSession().update(showOption);
	}

	
}
