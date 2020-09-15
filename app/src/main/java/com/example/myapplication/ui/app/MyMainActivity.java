package com.example.myapplication.ui.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Clients;
import com.example.myapplication.data.model.ClientsDao;
import com.example.myapplication.data.model.ColorType;
import com.example.myapplication.data.model.ColorTypeDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.EquipmentType;
import com.example.myapplication.data.model.EquipmentTypeDao;
import com.example.myapplication.data.model.Role;
import com.example.myapplication.data.model.RoleDao;
import com.example.myapplication.data.model.SizeType;
import com.example.myapplication.data.model.SizeTypeDao;
import com.example.myapplication.data.model.TypeOfCondition;
import com.example.myapplication.data.model.TypeOfConditionDao;
import com.example.myapplication.utils.App;

import java.util.List;


public class MyMainActivity extends AppCompatActivity {

  private RoleDao roleDao;
  private ClientsDao clientsDao;
  private ColorTypeDao colorTypeDao;
  private SizeTypeDao sizeTypeDao;
  private TypeOfConditionDao typeOfConditionDao;
  private EquipmentTypeDao equipmentTypeDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    DaoSession daoSession = ((App) getApplication()).getDaoSession();
    roleDao = daoSession.getRoleDao();
    clientsDao = daoSession.getClientsDao();
    colorTypeDao = daoSession.getColorTypeDao();
    sizeTypeDao = daoSession.getSizeTypeDao();
    typeOfConditionDao = daoSession.getTypeOfConditionDao();
    equipmentTypeDao = daoSession.getEquipmentTypeDao();
    List<Role> ee = roleDao.loadAll();
    /*
    TODO: Вынести в отдельный метод или лучше написать SQL скрипты
     */
    if (ee.size() == 0) {
      Role role1 = new Role();
      Role role2 = new Role();
      Role role3 = new Role();
      role1.setName("USER");
      role2.setName("CONSULT");
      role3.setName("MANAGER");
      ;
      roleDao.insert(role1);
      roleDao.insert(role2);
      roleDao.insert(role3);
      // CONSULT
      Clients consult = new Clients();
      consult.setName("consult");
      consult.setPatronymic("patronymic");
      consult.setSurname("surname");
      consult.setPhoneNumber("phoneNumber");
      consult.setAge(25);
      consult.setPassword("password");
      consult.setSeriesNumber("ewewqe");
      consult.setRole(role2);
      consult.setLogin("consult");
      consult.setRoleId(role2.getId());

      // MANAGER
      Clients manager = new Clients();
      manager.setName("manager");
      manager.setPatronymic("manager");
      manager.setSurname("manager");
      manager.setPhoneNumber("manager");
      manager.setAge(25);
      manager.setPassword("password");
      manager.setSeriesNumber("ewewqe");
      manager.setRole(role3);
      manager.setLogin("manager");
      manager.setRoleId(role3.getId());

      clientsDao.insert(consult);
      clientsDao.insert(manager);

      ColorType colorType1 = new ColorType();
      ColorType colorType2 = new ColorType();
      ColorType colorType3 = new ColorType();
      ColorType colorType4 = new ColorType();
      ColorType colorType5 = new ColorType();
      ColorType colorType6 = new ColorType();
      ColorType colorType7 = new ColorType();
      colorType1.setName("Черный");
      colorType2.setName("Песочный");
      colorType3.setName("Хаки");
      colorType4.setName("Мультикам Зеленый");
      colorType5.setName("Зеленый");
      colorType6.setName("Песочный мультикам");
      colorType7.setName("Серый");
      colorTypeDao.insert(colorType1);
      colorTypeDao.insert(colorType2);
      colorTypeDao.insert(colorType3);
      colorTypeDao.insert(colorType4);
      colorTypeDao.insert(colorType5);
      colorTypeDao.insert(colorType6);
      colorTypeDao.insert(colorType7);

      SizeType sizeType1 = new SizeType();
      SizeType sizeType2 = new SizeType();
      SizeType sizeType3 = new SizeType();
      SizeType sizeType4 = new SizeType();
      SizeType sizeType5 = new SizeType();
      sizeType1.setName("S");
      sizeType2.setName("M");
      sizeType3.setName("L");
      sizeType4.setName("XL");
      sizeType5.setName("Отсутствует");
      sizeTypeDao.insert(sizeType1);
      sizeTypeDao.insert(sizeType2);
      sizeTypeDao.insert(sizeType3);
      sizeTypeDao.insert(sizeType4);
      sizeTypeDao.insert(sizeType5);

      TypeOfCondition typeOfCondition1 = new TypeOfCondition();
      TypeOfCondition typeOfCondition2 = new TypeOfCondition();
      TypeOfCondition typeOfCondition3 = new TypeOfCondition();
      TypeOfCondition typeOfCondition4 = new TypeOfCondition();
      typeOfCondition1.setName("Требуется замена");
      typeOfCondition2.setName("Требуется ремонт");
      typeOfCondition3.setName("Удовлетворительное");
      typeOfCondition4.setName("Отличное");
      typeOfConditionDao.insert(typeOfCondition1);
      typeOfConditionDao.insert(typeOfCondition2);
      typeOfConditionDao.insert(typeOfCondition3);
      typeOfConditionDao.insert(typeOfCondition4);

      EquipmentType equipmentType1 = new EquipmentType();
      EquipmentType equipmentType2 = new EquipmentType();
      EquipmentType equipmentType3 = new EquipmentType();
      EquipmentType equipmentType4 = new EquipmentType();
      EquipmentType equipmentType5 = new EquipmentType();
      EquipmentType equipmentType6 = new EquipmentType();
      EquipmentType equipmentType7 = new EquipmentType();
      equipmentType1.setName("Оружие");
      equipmentType2.setName("Модули");
      equipmentType3.setName("Разгрузочные жилеты");
      equipmentType4.setName("Каски");
      equipmentType5.setName("Защита лица");
      equipmentType6.setName("Перчатки");
      equipmentType7.setName("Наколенники");
      equipmentTypeDao.insert(equipmentType1);
      equipmentTypeDao.insert(equipmentType2);
      equipmentTypeDao.insert(equipmentType3);
      equipmentTypeDao.insert(equipmentType4);
      equipmentTypeDao.insert(equipmentType5);
      equipmentTypeDao.insert(equipmentType6);
      equipmentTypeDao.insert(equipmentType7);
    }

    Button auth = findViewById(R.id.button22);
    Button registr = findViewById(R.id.button21);
    auth.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        login();
      }
    });
    registr.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registr();
      }
    });
  }

  public void alert(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  public void registr() {
    Intent intent = new Intent(this, AddClientActivity.class);
    startActivity(intent);
  }

  public void login() {
    String login = ((EditText) findViewById(R.id.login)).getText().toString();
    String password = ((EditText) findViewById(R.id.password)).getText().toString();
    if (login.isEmpty() || password.isEmpty()) {
      alert("Задайте логин или пароль");
    } else {
      List<Clients> checkAuth = clientsDao.queryBuilder().where(ClientsDao.Properties.Login.eq(login), ClientsDao.Properties.Password.eq(password)).list();
      if (checkAuth.size() != 0) {
        String roleUser = checkAuth.get(0).getRole().getName();
        Intent intent = new Intent(this, MainActivityDriwer.class);
        intent.putExtra("user_name", checkAuth.get(0).getName() + " " + checkAuth.get(0).getSurname());
        intent.putExtra("user_phone", checkAuth.get(0).getPhoneNumber());
        intent.putExtra("user_id", checkAuth.get(0).getId());
        intent.putExtra("user_role", roleUser);
        alert("Вход успешно выполнен!!!");
        startActivity(intent);
      } else {
        alert("Логин или пароль введены не правильно!!!");
      }
    }
  }
}
