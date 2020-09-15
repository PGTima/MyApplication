package com.example.myapplication.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import lombok.Data;
import org.greenrobot.greendao.annotation.Generated;

@Entity
@Data
public class JoinEventsWithClients {
  @Id(autoincrement = true)
  private Long id;
  private Long eventId;
  private Long clientId;
  @Generated(hash = 40515545)
  public JoinEventsWithClients(Long id, Long eventId, Long clientId) {
      this.id = id;
      this.eventId = eventId;
      this.clientId = clientId;
  }
  @Generated(hash = 752383041)
  public JoinEventsWithClients() {
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
}

