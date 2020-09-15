package com.example.myapplication.utils;

import android.app.Application;

import com.example.myapplication.data.model.DaoMaster;
import com.example.myapplication.data.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

  private DaoSession daoSession;

  @Override
  public void onCreate() {
    super.onCreate();
    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
    Database db = helper.getWritableDb();
    daoSession = new DaoMaster(db).newSession();
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}