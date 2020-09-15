package com.example.myapplication.ui.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Clients;
import com.example.myapplication.data.model.ClientsDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.data.model.JoinEventsWithClientsDao;
import com.example.myapplication.data.model.Role;
import com.example.myapplication.data.model.RoleDao;
import com.example.myapplication.utils.App;

import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Класс для работы активити регистрации пользователя
 */
public class AddClientActivity extends AppCompatActivity {
  private ClientsDao clientsDao;
  private RoleDao roleDao;
  private EventsDao eventsDao;
  private Integer id_events;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_client);
    DaoSession daoSession = ((App) getApplication()).getDaoSession();
    Intent intent = getIntent();
    if ( intent != null ) {
      id_events = intent.getIntExtra("id_events", 0);
    }
    roleDao = daoSession.getRoleDao();
    clientsDao = daoSession.getClientsDao();
    eventsDao = daoSession.getEventsDao();
    Button add = findViewById(R.id.button7);
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registr();
      }
    });
  }
  private  Boolean validateLogin(String login){
    EditText loginView = findViewById(R.id.editTextTextPersonName19);
    Clients clients = clientsDao.queryBuilder()
        .where(ClientsDao.Properties.Login.eq(login)).unique();
    if (login.isEmpty()) {
      loginView.setError("Field cannot be empty");
      return false;
    }else if (clients != null){
      loginView.setError("This login exists!!!");
      return false;
    }
    else {
      loginView.setError(null);
      return true;
    }
  }
  public void registr(){
    String name =((EditText) findViewById(R.id.editTextTextPersonName8)).getText().toString();
    String surname =((EditText) findViewById(R.id.editTextTextPersonName9)).getText().toString();
    String patronymic =((EditText) findViewById(R.id.editTextTextPersonName11)).getText().toString();
    String phoneNumber =((EditText) findViewById(R.id.editTextTextPersonName10)).getText().toString();
    String age = ((EditText) findViewById(R.id.editTextTextPersonName13)).getText().toString();
    String seriesNumber =((EditText) findViewById(R.id.editTextTextPersonName12)).getText().toString();
    String password =((EditText) findViewById(R.id.editTextTextPersonName18)).getText().toString();
    String login =((EditText) findViewById(R.id.editTextTextPersonName19)).getText().toString();
    if ( validateLogin(login) ){


    List<Role> role = roleDao.queryBuilder()
                             .where(RoleDao.Properties.Name.eq("USER")).list();
    Clients clients = new Clients();
    clients.setName(name);
    clients.setPassword(password);
    clients.setAge(parseInt(age));
    clients.setPatronymic(patronymic);
    clients.setSurname(surname);
    clients.setSeriesNumber(seriesNumber);
    clients.setPhoneNumber(phoneNumber);
    clients.setRole(role.get(0));
    clients.setRoleId(role.get(0).getId());
    clients.setLogin(login);
    clientsDao.insert(clients);
    if (id_events != 0 ){
      Events events = eventsDao.loadByRowId(id_events);
      List<Clients> clientsList = events.getClients();
      boolean a = clientsList.add(clients);
      if (a){
        events.setClients(clientsList);
        eventsDao.update(events);
        Toast.makeText(this, "Успешно!!!", Toast.LENGTH_SHORT).show();
      }else{
        Toast.makeText(this, "Неизвестная ошибка при добавлении участников!!!", Toast.LENGTH_SHORT).show();
      }
    }
    Intent intent = new Intent(this, MyMainActivity.class);
    startActivity(intent);
    }else {
      Toast.makeText(this, "Ошибка валидации даных!!!", Toast.LENGTH_SHORT).show();
    }
  }
}