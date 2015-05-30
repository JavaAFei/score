package com.ghy.core.service;

import java.util.List;
import java.util.Map;

import com.ghy.core.entity.Score;

public abstract interface IScoreService {

	List<Score> getScoreByStudentId(String studentId);

	Map<String, Object> getStudentAndScores(Map<String, String> parmMap);


}
