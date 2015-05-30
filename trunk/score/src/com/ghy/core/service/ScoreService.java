package com.ghy.core.service;

import java.util.List;
import java.util.Map;

import com.ghy.core.dao.IScoreDao;
import com.ghy.core.entity.Score;
public class ScoreService implements IScoreService{

	private IScoreDao scoreDao;

	public IScoreDao getScoreDao() {
		return scoreDao;
	}

	public void setScoreDao(IScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}

	@Override
	public List<Score> getScoreByStudentId(String studentId) {
		return scoreDao.getScoreByStudentId(studentId);
	}

	@Override
	public Map<String, Object> getStudentAndScores(Map<String, String> parmMap) {
		return scoreDao.getStudentAndScores(parmMap);
	}

}
