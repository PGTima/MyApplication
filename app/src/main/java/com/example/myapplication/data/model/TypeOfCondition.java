package com.example.myapplication.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "condition_type" )
@Data
public class TypeOfCondition {
  @Id(autoincrement = true)
  private Long id;
  private String name;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 1133173299)
  private transient TypeOfConditionDao myDao;
  @Generated(hash = 130349163)
  public TypeOfCondition(Long id, String name) {
      this.id = id;
      this.name = name;
  }
  @Generated(hash = 1533783097)
  public TypeOfCondition() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public String getName() {
      return this.name;
  }
  public void setName(String name) {
      this.name = name;
  }
  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 128553479)
  public void delete() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.delete(this);
  }
  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 1942392019)
  public void refresh() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.refresh(this);
  }
  /**
   * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
   * Entity must attached to an entity context.
   */
  @Generated(hash = 713229351)
  public void update() {
      if (myDao == null) {
          throw new DaoException("Entity is detached from DAO context");
      }
      myDao.update(this);
  }
  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 361688205)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getTypeOfConditionDao() : null;
}
}
