/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: HibernateDao.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package com.ghy.common.orm.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;


/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询. 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型 
 * 
 * @author calvin
 */
@SuppressWarnings({"unchecked","rawtypes","hiding"})
public class HibernateDao<T, PK extends Serializable> extends
		SimpleHibernateDao<T, PK> {
	/**
	 * 用于Dao层子类的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * HibernateDao<User, Long>{ }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数. 在构造函数中定义对象类型Class. eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory,
			final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}
	
	
	/**
	 * 根据sql和查询变量map查询，返回全部数据list
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Object[]> findBySql(String sql, Map<String, ?> params) {
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		if (params != null && params.size() > 0)
			queryObject.setProperties(params);
		return queryObject.list();
	}
	
	 
	/**
	 * 根据sql和查询条件变量map查询，返回数据条数
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer findBySqlCount(String sql, Map<String, ?> params) {
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		if (params != null && params.size() > 0)
			queryObject.setProperties(params);
		List<BigDecimal> qlist = queryObject.list();
		if (qlist != null && qlist.size() > 0) {
			BigDecimal obj = qlist.get(0);
			return obj.intValue();
		}
		return 0;
	}
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Float findBySqlFloat(String sql, Map<String, ?> params) {
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		if (params != null && params.size() > 0)
			queryObject.setProperties(params);
		List<BigDecimal> qlist = queryObject.list();
		if (qlist != null && qlist.size() > 0) {
			BigDecimal obj = qlist.get(0);
			return obj.floatValue();
		}
		return Float.valueOf(0);
	}
	/**
	 * 根据sql和查询条件变量map查询，支持两个map，返回数据条数
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer findBySqlCount(String sql, Map<String, ?> params,Map<String, ?> params2) {
		SQLQuery queryObject = getSession().createSQLQuery(sql);
		if (params != null && params.size() > 0){
			queryObject.setProperties(params);
		}
		if (params2 != null && params2.size() > 0){
			queryObject.setProperties(params2);
		}
		List<BigDecimal> qlist = queryObject.list();
		if (qlist != null && qlist.size() > 0) {
			BigDecimal obj = qlist.get(0);
			return obj.intValue();
		}
		return 0;
	}
	/**
	 * 根据 hql和查询条件变量map查询，返回全部list
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> findByHQL(String hql, Map<String, ?> params) {
		Query queryObject = getSession().createQuery(hql);
		if (params != null && params.size() > 0)
			queryObject.setProperties(params);
		return queryObject.list();
	}
	/**
	 * 根据 hql和更新条件变量map查询，执行更新
	 * @param sql
	 * @param params
	 * @return
	 */
	public void updateByHql(String hql, Map<String, ?> values) {
		Query query = getSession().createQuery(hql);
		if (values != null && values.size() > 0) {
			query.setProperties(values);
		}
		query.executeUpdate();
	}
	/**
	 * 根据 sql和更新条件变量map查询，执行更新
	 * @param sql
	 * @param params
	 * @return
	 */
	public void updateBySql(String sql, Map<String, ?> values) {
		Query query = getSession().createSQLQuery(sql);
		if (values != null && values.size() > 0) {
			query.setProperties(values);
		}
		query.executeUpdate();
	}
	
	

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);
		System.out.println("hql count ===============" + countHql);
		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	
}
