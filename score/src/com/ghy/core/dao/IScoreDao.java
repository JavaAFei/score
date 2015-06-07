package com.ghy.core.dao;

import java.util.List;
import java.util.Map;

import com.ghy.core.entity.Score;


public abstract interface IScoreDao {

	List<Score> getScoreByStudentId(String studentId);

	Map<String, Object> getStudentAndScores(Map<String, String> parmMap);

	void deleteBySql(StringBuffer sql);


}
