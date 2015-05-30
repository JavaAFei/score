package com.ghy.core.service;

import java.util.Map;

import com.ghy.core.entity.ShowOption;


public abstract interface IShowOptionService {

	void updateColumn();

	Map<String, Object> queryOption();

	ShowOption getById(String id);

	void update(ShowOption showOption);

}
