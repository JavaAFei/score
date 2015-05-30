package com.ghy.core.dao;

import java.util.List;

import com.ghy.core.entity.Admin;

public abstract interface IAdminDao {

	public List<Admin> getAdminByLoginCodeAndPassword(String loginCode,String password);

	public void update(Admin admin);

}
