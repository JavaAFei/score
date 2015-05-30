package com.ghy.core.service;

import java.util.List;

import com.ghy.core.dao.IAdminDao;
import com.ghy.core.entity.Admin;

public class AdminService implements IAdminService{

	private IAdminDao adminDao;
	
	
	public List<Admin> getAdminByLoginCodeAndPassword(String loginCode,
			String password) {
		return adminDao.getAdminByLoginCodeAndPassword(loginCode,password);
	}

	public void update(Admin admin) {
		adminDao.update(admin);
	}
	
	public IAdminDao getAdminDao() {
		return adminDao;
	}


	public void setAdminDao(IAdminDao adminDao) {
		this.adminDao = adminDao;
	}




	
}
