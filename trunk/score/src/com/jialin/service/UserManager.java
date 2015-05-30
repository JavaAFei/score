package com.jialin.service;

import com.jialin.dao.IUserDao;
import com.jialin.entity.User;
import java.util.List;

public class UserManager
  implements IUserManager
{
  private IUserDao userDao;

  public User getOneUser(User user)
  {
    return this.userDao.getOneUser(user);
  }

  public void setUserDao(IUserDao userDao) {
    this.userDao = userDao;
  }

  public boolean addUser(User user)
  {
    return this.userDao.addUser(user);
  }

  public boolean delUser(User user)
  {
    return this.userDao.delUser(user);
  }

  public boolean updateUser(User user)
  {
    return this.userDao.updateUser(user);
  }

  public List<User> getAllUser()
  {
    return this.userDao.getAllUser();
  }
}