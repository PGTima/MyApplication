package com.example.myapplication.ui.app;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Класс для работы с Nawigation Driwer
 */
public class MainActivityDriwer extends AppCompatActivity {
  private String userNameSurname;
  private String roleName;
  private String userPhone;
  private int clientsMenu;
  private int eventsMenu;
  private int infoMenuConsult;
  private int exitMenu;
  private int equipmentManager;
  private int eventsManager;
  private int myPlay;
  private int myOrder;
  private int eventClientAll;

  private AppBarConfiguration mAppBarConfiguration;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_driwer);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Intent intent = getIntent();
    roleName = intent.getStringExtra("user_role");
    userNameSurname = intent.getStringExtra("user_name");
    userPhone = intent.getStringExtra("user_phone");
    clientsMenu = R.id.nav_clients;
    eventsMenu = R.id.nav_events_manager;
    infoMenuConsult = R.id.nav_info_manager;
    equipmentManager = R.id.nav_equipment;
    eventsManager = R.id.nav_events_manager_true;
    exitMenu = R.id.nav_exit;
    myPlay = R.id.nav_my_game;
    myOrder = R.id.nav_my_order;
    eventClientAll = R.id.nav_events_clients_all;
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    View navHeader = navigationView.getHeaderView(0);
    TextView userNameSurnameEdit = navHeader.findViewById(R.id.userNameSurname);
    userNameSurnameEdit.setText(userNameSurname);
    TextView userPhoneEdit = navHeader.findViewById(R.id.phoneUserName);
    userPhoneEdit.setText(userPhone);
    mAppBarConfiguration = new AppBarConfiguration.Builder(clientsMenu,equipmentManager,eventsManager, eventsMenu, infoMenuConsult, exitMenu
        , myPlay , myOrder, eventClientAll)
        .setDrawerLayout(drawer)
        .build();
    Menu nav_Menu = navigationView.getMenu();
    if (roleName.equals("CONSULT")) {
      nav_Menu.findItem(equipmentManager).setVisible(false);
      nav_Menu.findItem(eventsManager).setVisible(false);
      nav_Menu.findItem(myPlay).setVisible(false);
      nav_Menu.findItem(myOrder).setVisible(false);
      nav_Menu.findItem(eventClientAll).setVisible(false);
    } else if (roleName.equals("USER")){
      nav_Menu.findItem(equipmentManager).setVisible(false);
      nav_Menu.findItem(clientsMenu).setVisible(false);
      nav_Menu.findItem(eventsMenu).setVisible(false);
      nav_Menu.findItem(eventsManager).setVisible(false);
    }else if (roleName.equals("MANAGER")){
      nav_Menu.findItem(clientsMenu).setVisible(false);
      nav_Menu.findItem(eventsMenu).setVisible(false);
      nav_Menu.findItem(myPlay).setVisible(false);
      nav_Menu.findItem(myOrder).setVisible(false);
      nav_Menu.findItem(eventClientAll).setVisible(false);
    }
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    return true;
  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }
}