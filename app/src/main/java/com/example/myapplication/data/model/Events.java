package com.example.myapplication.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "events" )
@Data
public class Events{
  @Id(autoincrement = true)
  private Long id;
  private String eventName;
  private Date eventDate;
  private String timeFrom;
  private String timeTo;
  private String place;
  private Integer vacancies;
  private Integer countPlace;
  private Integer entranceFee;
  private String reference;
  @ToMany
  @JoinEntity(
      entity = JoinEventsWithClients.class,
      sourceProperty = "eventId",
      targetProperty = "clientId"
  )
  private List<Clients> clients;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 1877632518)
  private transient EventsDao myDao;
  @Generated(hash = 114637042)
  public Events(Long id, String eventName, Date eventDate, String timeFrom,
          String timeTo, String place, Integer vacancies, Integer countPlace,
          Integer entranceFee, String reference) {
      this.id = id;
      this.eventName = eventName;
      this.eventDate = eventDate;
      this.timeFrom = timeFrom;
      this.timeTo = timeTo;
      this.place = place;
      this.vacancies = vacancies;
      this.countPlace = countPlace;
      this.entranceFee = entranceFee;
      this.reference = reference;
  }
  @Generated(hash = 2113269558)
  public Events() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public String getEventName() {
      return this.eventName;
  }
  public void setEventName(String eventName) {
      this.eventName = eventName;
  }
  public Date getEventDate() {
      return this.eventDate;
  }
  public void setEventDate(Date eventDate) {
      this.eventDate = eventDate;
  }
  public String getTimeFrom() {
      return this.timeFrom;
  }
  public void setTimeFrom(String timeFrom) {
      this.timeFrom = timeFrom;
  }
  public String getTimeTo() {
      return this.timeTo;
  }
  public void setTimeTo(String timeTo) {
      this.timeTo = timeTo;
  }
  public String getPlace() {
      return this.place;
  }
  public void setPlace(String place) {
      this.place = place;
  }
  public Integer getVacancies() {
      return this.vacancies;
  }
  public void setVacancies(Integer vacancies) {
      this.vacancies = vacancies;
  }
  public Integer getCountPlace() {
      return this.countPlace;
  }
  public void setCountPlace(Integer countPlace) {
      this.countPlace = countPlace;
  }
  public Integer getEntranceFee() {
      return this.entranceFee;
  }
  public void setEntranceFee(Integer entranceFee) {
      this.entranceFee = entranceFee;
  }
  public String getReference() {
      return this.reference;
  }
  public void setReference(String reference) {
      this.reference = reference;
  }
  /**
   * To-many relationship, resolved on first access (and after reset).
   * Changes to to-many relations are not persisted, make changes to the target entity.
   */
  @Generated(hash = 419980731)
  public List<Clients> getClients() {
      if (clients == null) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          ClientsDao targetDao = daoSession.getClientsDao();
          List<Clients> clientsNew = targetDao._queryEvents_Clients(id);
          synchronized (this) {
              if (clients == null) {
                  clients = clientsNew;
              }
          }
      }
      return clients;
  }
  /** Resets a to-many relationship, making the next get call to query for a fresh result. */
  @Generated(hash = 1833006718)
  public synchronized void resetClients() {
      clients = null;
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
@Generated(hash = 325012982)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getEventsDao() : null;
}
}
