package com.example.myapplication.ui.app.ui.clients;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Clients;
import com.example.myapplication.data.model.ClientsDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.data.model.JoinEventsWithClients;
import com.example.myapplication.data.model.JoinEventsWithClientsDao;
import com.example.myapplication.data.model.Role;
import com.example.myapplication.data.model.RoleDao;
import com.example.myapplication.ui.app.ui.events.events;
import com.example.myapplication.utils.App;
import java.util.List;
import static java.lang.Integer.parseInt;

/**
 * Класс для работы с фрагментом добавления пользователей
 */
public class addClient extends Fragment {
  private ClientsDao clientsDao;
  private RoleDao roleDao;
  private JoinEventsWithClientsDao joinEventsWithClientsDao;
  private EventsDao eventsDao;
  Integer id_events = 0;

  @Override
  public void onViewCreated(final View view, Bundle savedInstanceState) {
    Button add = view.findViewById(R.id.button7);
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registr(view);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    roleDao = daoSession.getRoleDao();
    clientsDao = daoSession.getClientsDao();
    eventsDao = daoSession.getEventsDao();
    joinEventsWithClientsDao =daoSession.getJoinEventsWithClientsDao();
    Bundle bundle = getArguments();
    if (bundle != null){
      id_events = getArguments().getInt("id_events");
    }
    return inflater.inflate(R.layout.fragment_add_client, container, false);
  }

  /**
   * Регистрация нового пользователя
   * @param view
   */
  public void registr(View view){
    String name =((EditText) view.findViewById(R.id.editTextTextPersonName8)).getText().toString();
    String surname =((EditText) view.findViewById(R.id.editTextTextPersonName9)).getText().toString();
    String patronymic =((EditText) view.findViewById(R.id.editTextTextPersonName11)).getText().toString();
    String phoneNumber =((EditText) view.findViewById(R.id.editTextTextPersonName10)).getText().toString();
    String age = ((EditText) view.findViewById(R.id.editTextTextPersonName13)).getText().toString();
    String seriesNumber =((EditText) view.findViewById(R.id.editTextTextPersonName12)).getText().toString();
    String password =((EditText) view.findViewById(R.id.editTextTextPersonName18)).getText().toString();
    String login =((EditText) view.findViewById(R.id.editTextTextPersonName20)).getText().toString();
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
        JoinEventsWithClients joinEventsWithClients = new JoinEventsWithClients();
        joinEventsWithClients.setClientId(clients.getId());
        joinEventsWithClients.setEventId(events.getId());
        joinEventsWithClientsDao.insert(joinEventsWithClients);
        events.setClients(clientsList);
        eventsDao.update(events);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
            .replace(R.id.nav_host_fragment, new events())
            .addToBackStack(null)
            .commit();
        Toast.makeText(getContext(), "Успешно!!!", Toast.LENGTH_SHORT).show();
      }else{
        Toast.makeText(getContext(), "Неизвестная ошибка при добавлении участников!!!", Toast.LENGTH_SHORT).show();
      }
    }
  }
}