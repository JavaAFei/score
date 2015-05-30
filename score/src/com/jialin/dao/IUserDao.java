package com.jialin.dao;

import com.jialin.entity.User;
import java.util.List;

public abstract interface IUserDao
{
  public abstract boolean addUser(User paramUser);

  public abstract boolean delUser(User paramUser);

  public abstract boolean updateUser(User paramUser);

  public abstract List<User> getAllUser();

  public abstract User getOneUser(User paramUser);
}