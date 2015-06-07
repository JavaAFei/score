package com.ghy.core.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import com.ghy.common.util.StringUtil;
import com.ghy.core.entity.Score;

public class ScoreDao implements IScoreDao{
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Score> getScoreByStudentId(String studentId) {
		Query query = this.sessionFactory.getCurrentSession().createQuery
				(" from Score t where   studentId = :studentId AND NOT EXISTS (SELECT '' FROM ShowOption t2 WHERE t2.columnName = t.name AND t2.isShow = '1')  ORDER BY t.sort");
		query.setString("studentId", studentId);
		return query.list();
	}

	@Override
	public Map<String, Object> getStudentAndScores(Map<String, String> parmMap) {
		
		int page = 1;
		if(!StringUtil.isBlank(parmMap.get("page"))){
			page = Integer.valueOf(parmMap.get("page"));
		} 
		int rows = 15;
		if(!StringUtil.isBlank(parmMap.get("rows"))){
			 rows = Integer.valueOf(parmMap.get("rows"));
		}
		String examNo = parmMap.get("examNo");
		String name =  parmMap.get("name");
		
		List<String> columnList =this.sessionFactory.getCurrentSession().createSQLQuery("SELECT DISTINCT(t.`name`) FROM t_score t ORDER BY t.`sort` ").list();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t.`studentId`,s.`examNo`,s.`name` ");
		sqlCount.append(" SELECT  COUNT(*)  FROM (SELECT  ''  FROM t_score t, t_student s  WHERE s.`id` = t.`studentId`    ");
		if(columnList!=null && columnList.size()>0){
			for(String column : columnList){
				sql.append(" ,MAX(CASE t.name WHEN '").append(column).append("' THEN VALUE ELSE '' END)  ");
			}
		}
		sql.append(" FROM t_score t,t_student s WHERE s.`id` = t.`studentId` ");
		if(!StringUtil.isBlank(examNo)){
			sql.append("  AND s.examNo like '%").append(examNo).append("%'  ");
			sqlCount.append("  AND s.examNo like '%").append(examNo).append("%'  ");
		}
		if(!StringUtil.isBlank(name)){
			sql.append("  AND s.name like '%").append(name).append("%'  ");
			sqlCount.append("  AND s.name like '%").append(name).append("%'  ");
		}
		sql.append("  GROUP BY t.`studentId`,s.`examNo`,s.`name` ");
		sqlCount.append("  GROUP BY t.`studentId`, s.`examNo`, s.`name`)t ");
		sql.append(" LIMIT  ").append((page-1)*rows).append(" , ").append(rows);
		 
		List<Object[]> list = this.sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).list();
		SQLQuery sQlCountQuery = this.sessionFactory.getCurrentSession().createSQLQuery(sqlCount.toString());
		int count = ((BigInteger) sQlCountQuery.uniqueResult()).intValue();
		List<Map<String,String>> rowsList = new ArrayList<Map<String,String>>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(list!=null && list.size()>0){
			for(Object[] value : list){
				Map<String,String> valuesMap = new HashMap<String,String>(); 
				valuesMap.put("studentId", value[0] ==null ? "" : value[0].toString());
				valuesMap.put("examNo", value[1] ==null ? "" : value[1].toString());
				valuesMap.put("name", value[2] ==null ? "" : value[2].toString());
				if(columnList!=null && columnList.size()>0){
					for(int i=0;i<columnList.size();i++){
						valuesMap.put(columnList.get(i), value[i+3] ==null ? "" : value[i+3].toString());
					}
				}
				rowsList.add(valuesMap);
			}
		}
		resultMap.put("columns", columnList);
		resultMap.put("total", count);
		resultMap.put("rows", rowsList);
		return resultMap;
	}

	@Override
	public void deleteBySql(StringBuffer sql) {
		Session s = this.sessionFactory.getCurrentSession();
		Query q = s.createSQLQuery(sql.toString());
		q.executeUpdate();
		
	}


}
