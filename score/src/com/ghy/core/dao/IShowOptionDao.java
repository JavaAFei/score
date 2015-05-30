package com.ghy.core.dao;

import java.util.List;

import com.ghy.core.entity.ShowOption;


public abstract interface IShowOptionDao {

	void updateBySql(String sql);

	List<Object[]> queryBySql(StringBuffer sql);

	ShowOption getById(String id);

	void update(ShowOption showOption);

}
