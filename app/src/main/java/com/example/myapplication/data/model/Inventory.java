package com.example.myapplication.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(active = true, nameInDb = "inventory" )
@Data
public class Inventory{
  @Id(autoincrement = true)
  private Long id;
  private String name;
  private String vendor;
  private Integer rentalCost;
  private String bookingPresence;
  private Integer bookingAmount;
  private String specification;
  private long equipmentTypeId;
  private long sizeTypeId;
  private long typeOfConditionId;
  private long colorTypeId;
  @ToOne(joinProperty = "equipmentTypeId")
  private EquipmentType equipmentType;
  @ToOne(joinProperty = "sizeTypeId")
  private SizeType sizeType;
  @ToOne(joinProperty = "typeOfConditionId")
  private TypeOfCondition typeOfCondition;
  @ToOne(joinProperty = "colorTypeId")
  private ColorType colorType;
  /** Used to resolve relations */
  @Generated(hash = 2040040024)
  private transient DaoSession daoSession;
  /** Used for active entity operations. */
  @Generated(hash = 433391049)
  private transient InventoryDao myDao;
  @Generated(hash = 757385814)
  public Inventory(Long id, String name, String vendor, Integer rentalCost,
          String bookingPresence, Integer bookingAmount, String specification,
          long equipmentTypeId, long sizeTypeId, long typeOfConditionId,
          long colorTypeId) {
      this.id = id;
      this.name = name;
      this.vendor = vendor;
      this.rentalCost = rentalCost;
      this.bookingPresence = bookingPresence;
      this.bookingAmount = bookingAmount;
      this.specification = specification;
      this.equipmentTypeId = equipmentTypeId;
      this.sizeTypeId = sizeTypeId;
      this.typeOfConditionId = typeOfConditionId;
      this.colorTypeId = colorTypeId;
  }
  @Generated(hash = 375708430)
  public Inventory() {
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
  public String getVendor() {
      return this.vendor;
  }
  public void setVendor(String vendor) {
      this.vendor = vendor;
  }
  public Integer getRentalCost() {
      return this.rentalCost;
  }
  public void setRentalCost(Integer rentalCost) {
      this.rentalCost = rentalCost;
  }
  public String getBookingPresence() {
      return this.bookingPresence;
  }
  public void setBookingPresence(String bookingPresence) {
      this.bookingPresence = bookingPresence;
  }
  public Integer getBookingAmount() {
      return this.bookingAmount;
  }
  public void setBookingAmount(Integer bookingAmount) {
      this.bookingAmount = bookingAmount;
  }
  public String getSpecification() {
      return this.specification;
  }
  public void setSpecification(String specification) {
      this.specification = specification;
  }
  public long getEquipmentTypeId() {
      return this.equipmentTypeId;
  }
  public void setEquipmentTypeId(long equipmentTypeId) {
      this.equipmentTypeId = equipmentTypeId;
  }
  public long getSizeTypeId() {
      return this.sizeTypeId;
  }
  public void setSizeTypeId(long sizeTypeId) {
      this.sizeTypeId = sizeTypeId;
  }
  public long getTypeOfConditionId() {
      return this.typeOfConditionId;
  }
  public void setTypeOfConditionId(long typeOfConditionId) {
      this.typeOfConditionId = typeOfConditionId;
  }
  public long getColorTypeId() {
      return this.colorTypeId;
  }
  public void setColorTypeId(long colorTypeId) {
      this.colorTypeId = colorTypeId;
  }
  @Generated(hash = 521727303)
  private transient Long equipmentType__resolvedKey;
  /** To-one relationship, resolved on first access. */
  @Generated(hash = 690894425)
  public EquipmentType getEquipmentType() {
      long __key = this.equipmentTypeId;
      if (equipmentType__resolvedKey == null
              || !equipmentType__resolvedKey.equals(__key)) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          EquipmentTypeDao targetDao = daoSession.getEquipmentTypeDao();
          EquipmentType equipmentTypeNew = targetDao.load(__key);
          synchronized (this) {
              equipmentType = equipmentTypeNew;
              equipmentType__resolvedKey = __key;
          }
      }
      return equipmentType;
  }
  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 121135644)
  public void setEquipmentType(@NotNull EquipmentType equipmentType) {
      if (equipmentType == null) {
          throw new DaoException(
                  "To-one property 'equipmentTypeId' has not-null constraint; cannot set to-one to null");
      }
      synchronized (this) {
          this.equipmentType = equipmentType;
          equipmentTypeId = equipmentType.getId();
          equipmentType__resolvedKey = equipmentTypeId;
      }
  }
  @Generated(hash = 1204915788)
  private transient Long sizeType__resolvedKey;
  /** To-one relationship, resolved on first access. */
  @Generated(hash = 1944946857)
  public SizeType getSizeType() {
      long __key = this.sizeTypeId;
      if (sizeType__resolvedKey == null || !sizeType__resolvedKey.equals(__key)) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          SizeTypeDao targetDao = daoSession.getSizeTypeDao();
          SizeType sizeTypeNew = targetDao.load(__key);
          synchronized (this) {
              sizeType = sizeTypeNew;
              sizeType__resolvedKey = __key;
          }
      }
      return sizeType;
  }
  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 80771922)
  public void setSizeType(@NotNull SizeType sizeType) {
      if (sizeType == null) {
          throw new DaoException(
                  "To-one property 'sizeTypeId' has not-null constraint; cannot set to-one to null");
      }
      synchronized (this) {
          this.sizeType = sizeType;
          sizeTypeId = sizeType.getId();
          sizeType__resolvedKey = sizeTypeId;
      }
  }
  @Generated(hash = 1263781559)
  private transient Long typeOfCondition__resolvedKey;
  /** To-one relationship, resolved on first access. */
  @Generated(hash = 999914192)
  public TypeOfCondition getTypeOfCondition() {
      long __key = this.typeOfConditionId;
      if (typeOfCondition__resolvedKey == null
              || !typeOfCondition__resolvedKey.equals(__key)) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          TypeOfConditionDao targetDao = daoSession.getTypeOfConditionDao();
          TypeOfCondition typeOfConditionNew = targetDao.load(__key);
          synchronized (this) {
              typeOfCondition = typeOfConditionNew;
              typeOfCondition__resolvedKey = __key;
          }
      }
      return typeOfCondition;
  }
  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 1667077669)
  public void setTypeOfCondition(@NotNull TypeOfCondition typeOfCondition) {
      if (typeOfCondition == null) {
          throw new DaoException(
                  "To-one property 'typeOfConditionId' has not-null constraint; cannot set to-one to null");
      }
      synchronized (this) {
          this.typeOfCondition = typeOfCondition;
          typeOfConditionId = typeOfCondition.getId();
          typeOfCondition__resolvedKey = typeOfConditionId;
      }
  }
  @Generated(hash = 531150333)
  private transient Long colorType__resolvedKey;
  /** To-one relationship, resolved on first access. */
  @Generated(hash = 1099897849)
  public ColorType getColorType() {
      long __key = this.colorTypeId;
      if (colorType__resolvedKey == null
              || !colorType__resolvedKey.equals(__key)) {
          final DaoSession daoSession = this.daoSession;
          if (daoSession == null) {
              throw new DaoException("Entity is detached from DAO context");
          }
          ColorTypeDao targetDao = daoSession.getColorTypeDao();
          ColorType colorTypeNew = targetDao.load(__key);
          synchronized (this) {
              colorType = colorTypeNew;
              colorType__resolvedKey = __key;
          }
      }
      return colorType;
  }
  /** called by internal mechanisms, do not call yourself. */
  @Generated(hash = 654627580)
  public void setColorType(@NotNull ColorType colorType) {
      if (colorType == null) {
          throw new DaoException(
                  "To-one property 'colorTypeId' has not-null constraint; cannot set to-one to null");
      }
      synchronized (this) {
          this.colorType = colorType;
          colorTypeId = colorType.getId();
          colorType__resolvedKey = colorTypeId;
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
  /** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1763418221)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getInventoryDao() : null;
}
}
