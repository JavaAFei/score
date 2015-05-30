package com.jialin.dao;

import com.jialin.entity.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDao
  implements IUserDao
{
  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory)
  {
    this.sessionFactory = sessionFactory;
  }

  public boolean addUser(User user)
  {
    this.sessionFactory.getCurrentSession().save(user);
    return true;
  }

  public boolean delUser(User user)
  {
    this.sessionFactory.getCurrentSession().delete(user);
    return true;
  }

  public boolean updateUser(User user)
  {
    this.sessionFactory.getCurrentSession().update(user);
    return true;
  }

  public List<User> getAllUser()
  {
    Query query = this.sessionFactory.getCurrentSession().createQuery("from User");
    List userlist = query.list();
    return userlist;
  }

  public User getOneUser(User user)
  {
    User user1 = (User)this.sessionFactory.getCurrentSession().load(User.class, user.getId());

    return user1;
  }
}