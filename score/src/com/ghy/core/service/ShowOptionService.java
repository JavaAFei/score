package com.ghy.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ghy.core.dao.IShowOptionDao;
import com.ghy.core.entity.ShowOption;

public class ShowOptionService implements IShowOptionService {

	private IShowOptionDao showOptionDao;

	public IShowOptionDao getShowOptionDao() {
		return showOptionDao;
	}

	public void setShowOptionDao(IShowOptionDao showOptionDao) {
		this.showOptionDao = showOptionDao;
	}

	@Override
	public void updateColumn() {
		StringBuffer sql  = new StringBuffer();
		sql.append(" INSERT INTO t_show_option(id,columnName,isShow,sort)  ");
		sql.append("  SELECT UUID(),t2.* FROM ( ");
		sql.append("    SELECT  DISTINCT(t.`name`) column_name ,'0' is_show,t.`sort` FROM t_score t  ");
		sql.append("    WHERE NOT EXISTS ( ");
		sql.append("      SELECT '' FROM t_show_option s WHERE s.`columnName` = t.`name`) ");
		sql.append("  ORDER BY t.`sort` )t2 ");
		showOptionDao.updateBySql(sql.toString());
	}

	@Override
	public Map<String, Object> queryOption() {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT t.id,t.`columnName`, CASE t.`isShow` WHEN '0' THEN 'ÏÔÊ¾' ELSE 'Òþ²Ø' END isShow FROM t_show_option t  ");
		sql.append("  WHERE EXISTS ( ");
		sql.append("        SELECT '' FROM t_score s WHERE s.`name` = t.`columnName`) ");
		sql.append("  ORDER BY t.sort  ");
		List<Object[]> list = showOptionDao.queryBySql(sql);
		List<Map<String,String>> rowsList = new ArrayList<Map<String,String>>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(list!=null && list.size()>0){
			for(Object[] value : list){
				Map<String,String> valuesMap = new HashMap<String,String>(); 
				valuesMap.put("id", value[0] ==null ? "" : value[0].toString());
				valuesMap.put("columnName", value[1] ==null ? "" : value[1].toString());
				valuesMap.put("isShow", value[2] ==null ? "" : value[2].toString());
				
				rowsList.add(valuesMap);
			}
		}
		resultMap.put("total", rowsList.size());
		resultMap.put("rows", rowsList);
		return resultMap;
	}

	@Override
	public ShowOption getById(String id) {
		return showOptionDao.getById(id);
	}

	@Override
	public void update(ShowOption showOption) {
		showOptionDao.update(showOption);
	}

}
