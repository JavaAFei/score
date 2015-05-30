package com.jialin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_user")
public class User
{

  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy="uuid")
  @Column(length=32)
  private String id;

  @Column(length=32)
  private String userName;

  @Column(length=32)
  private String age;

  public String getId()
  {
    return this.id; }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserName() {
    return this.userName; }

  public void setUserName(String userName) {
    this.userName = userName; }

  public String getAge() {
    return this.age; }

  public void setAge(String age) {
    this.age = age;
  }
}