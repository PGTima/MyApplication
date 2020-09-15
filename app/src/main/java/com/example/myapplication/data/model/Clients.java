package com.example.myapplication.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "clients" )
@Data
public class Clients {
  @Id(autoincrement = true)
  private Long id;
  @NotNull
  private String name;
  private String patronymic;
  private String surname;
  private String phoneNumber;
  private Integer age;
  private String seriesNumber;
  private String password;
  @NotNull
  @Unique
  private String login;
  private long roleId;
  @ToOne(joinProperty = "roleId")
  private Role role;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 1248535455)
  private transient ClientsDao myDao;
  @Generated(hash = 1375539074)
public Clients(Long id, @NotNull String name, String patronymic, String surname, String phoneNumber,
        Integer age, String seriesNumber, String password, @NotNull String login, long roleId) {
    this.id = id;
    this.name = name;
    this.patronymic = patronymic;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
    this.age = age;
    this.seriesNumber = seriesNumber;
    this.password = password;
    this.login = login;
    this.roleId = roleId;
}
@Generated(hash = 1891515851)
  public Clients() {
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
  public String getPatronymic() {
      return this.patronymic;
  }
  public void setPatronymic(String patronymic) {
      this.patronymic = patronymic;
  }
  public String getSurname() {
      return this.surname;
  }
  public void setSurname(String surname) {
      this.surname = surname;
  }
  public String getPhoneNumber() {
      return this.phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
  }
  public Integer getAge() {
      return this.age;
  }
  public void setAge(Integer age) {
      this.age = age;
  }
  public String getSeriesNumber() {
      return this.seriesNumber;
  }
  public void setSeriesNumber(String seriesNumber) {
      this.seriesNumber = seriesNumber;
  }
  public String getPassword() {
      return this.password;
  }
  public void setPassword(String password) {
      this.password = password;
  }
  public long getRoleId() {
      return this.roleId;
  }
  public void setRoleId(long roleId) {
      this.roleId = roleId;
  }
  @Generated(hash = 312471022)
  private transient Long role__resolvedKey;
  /** To-one relationship, resolved on first access. */
  @Generated(hash = 1981318017)
  public Role getRole() {
      long __key = this.roleId;
      if (role__resolvedKey == null || !role__resolvedKey.equals(__key)) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          RoleDao targetDao = daoSession.getRoleDao();
          Role roleNew = targetDao.load(__key);
          synchronized (this) {
              role = roleNew;
              role__resolvedKey = __key;
          }
      }
      return role;
  }
  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 1734660000)
  public void setRole(@NotNull Role role) {
      if (role == null) {
          throw new DaoException(
                  "To-one property 'roleId' has not-null constraint; cannot set to-one to null");
      }
      synchronized (this) {
          this.role = role;
          roleId = role.getId();
          role__resolvedKey = roleId;
      }
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
  public String getLogin() {
    return this.login;
}
public void setLogin(String login) {
    this.login = login;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1720024824)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getClientsDao() : null;
}

}
