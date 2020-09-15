package com.example.myapplication.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
@Data
@Entity(active = true, nameInDb = "INVENTORY_EVENTS_PLAYERS")
public class InventoryEventsPlayers {
  @Id(autoincrement = true)
  private Long id;
  private Long eventId;
  private Long clientId;
  private Long inventoryId;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 289379325)
  private transient InventoryEventsPlayersDao myDao;
  @Generated(hash = 713186143)
  public InventoryEventsPlayers(Long id, Long eventId, Long clientId,
          Long inventoryId) {
      this.id = id;
      this.eventId = eventId;
      this.clientId = clientId;
      this.inventoryId = inventoryId;
  }
  @Generated(hash = 815171650)
  public InventoryEventsPlayers() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public Long getEventId() {
      return this.eventId;
  }
  public void setEventId(Long eventId) {
      this.eventId = eventId;
  }
  public Long getClientId() {
      return this.clientId;
  }
  public void setClientId(Long clientId) {
      this.clientId = clientId;
  }
  public Long getInventoryId() {
      return this.inventoryId;
  }
  public void setInventoryId(Long inventoryId) {
      this.inventoryId = inventoryId;
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
@Generated(hash = 550470710)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getInventoryEventsPlayersDao() : null;
}



}
