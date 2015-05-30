package com.ghy.core.service;

import java.util.List;

import com.ghy.core.entity.Admin;

public abstract interface IAdminService {

	public abstract List<Admin> getAdminByLoginCodeAndPassword(String loginCode, String password);

	public abstract void update(Admin admin);

}
