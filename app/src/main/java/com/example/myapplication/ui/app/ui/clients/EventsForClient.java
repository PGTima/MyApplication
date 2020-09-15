package com.example.myapplication.ui.app.ui.clients;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.ClientsDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.data.model.InventoryEventsPlayers;
import com.example.myapplication.data.model.InventoryEventsPlayersDao;
import com.example.myapplication.data.model.JoinEventsWithClients;
import com.example.myapplication.data.model.JoinEventsWithClientsDao;
import com.example.myapplication.utils.App;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с фрагементом привязанных мероприятий
 */
public class EventsForClient extends Fragment  implements View.OnClickListener {
  TableLayout tableLayout;
  private EventsDao eventsDao;
  View.OnClickListener delBtnCancel;
  private JoinEventsWithClientsDao joinEventsWithClientsDao;
  private InventoryEventsPlayersDao inventoryEventsPlayersDao;
  View.OnClickListener addBtnCancel;
  private JoinEventsWithClientsDao eventsWithClientsDao;
  Long user_id;
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    delBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        delRelationClientEquipment(v.getId());
        Toast.makeText(getContext(), "Удаление успешно произведено!!!", Toast.LENGTH_SHORT).show();
      }
    };
    addBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addEquipmentClient(v.getId());
        Toast.makeText(getContext(), "Обновление мероприятия!!!", Toast.LENGTH_SHORT).show();
      }
    };
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    initViews(view);
  }

  /**
   * Удаление связей клиента и мероприятия из линковочных таблиц
   * @param id
   */
  private void delRelationClientEquipment(int id) {
    // Необходимо почистить связи при удалении клиента из системы
    List<InventoryEventsPlayers> inventoryEventsPlayersList = inventoryEventsPlayersDao.queryBuilder()
        .where(InventoryEventsPlayersDao.Properties.ClientId.eq(user_id), InventoryEventsPlayersDao.Properties.EventId.eq(id) ).list();
    List<JoinEventsWithClients> joinEventsWithClientsList = joinEventsWithClientsDao.queryBuilder()
        .where(JoinEventsWithClientsDao.Properties.ClientId.eq(user_id), JoinEventsWithClientsDao.Properties.EventId.eq(id)).list();
    if (joinEventsWithClientsList.size() != 0){
      for (JoinEventsWithClients i: joinEventsWithClientsList) {
        joinEventsWithClientsDao.delete(i);
      }
    }
    if (inventoryEventsPlayersList.size() != 0){
      for (InventoryEventsPlayers i: inventoryEventsPlayersList) {
        inventoryEventsPlayersDao.delete(i);
      }
    }
    FragmentManager manager = getFragmentManager();
    EventsForClient newFragment = new EventsForClient();
    manager.beginTransaction()
        .replace(R.id.nav_host_fragment, newFragment)
        .addToBackStack(null)
        .commit();
  }

  /**
   * Добавить оборудование клиенту
   * редирект на другой фрагмент
   * @param id
   */
  private void addEquipmentClient(int id) {
    FragmentManager manager = getFragmentManager();
    Bundle args = new Bundle();
    args.putInt("id_events", id);
    args.putLong("id_user", user_id);
    EquipmentClient newFragment = new EquipmentClient();
    newFragment.setArguments(args);
    manager.beginTransaction()
        .replace(R.id.nav_host_fragment, newFragment)
        .addToBackStack(null)
        .commit();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    eventsDao = daoSession.getEventsDao();
    eventsWithClientsDao = daoSession.getJoinEventsWithClientsDao();
    joinEventsWithClientsDao = daoSession.getJoinEventsWithClientsDao();
    inventoryEventsPlayersDao = daoSession.getInventoryEventsPlayersDao();
    Intent intent = getActivity().getIntent();
    user_id = intent.getLongExtra("user_id",0);
    return inflater.inflate(R.layout.fragment_events_for_client, container, false);
  }

  public void initViews(View view) {
    tableLayout = (TableLayout) view.findViewById(R.id.tableLayout23);
    addHeaders(view);
    List<JoinEventsWithClients> joinEventsWithClientsList = eventsWithClientsDao.queryBuilder()
        .where(JoinEventsWithClientsDao.Properties.ClientId.eq(user_id)).list();
    List<Events> eventsList = new ArrayList<>();
    if (joinEventsWithClientsList.size() !=0 ){
      for (JoinEventsWithClients i: joinEventsWithClientsList){
        Events events = new Events();
        events = eventsDao.loadByRowId(i.getEventId());
        eventsList.add(events);
      }
    }
    if (eventsList.size() != 0) {
      addRows(eventsList);
    }
  }

  public void addHeaders(View view) {
    TableLayout tl = view.findViewById(R.id.tableLayout23);
    TableRow tr = new TableRow(getContext());
    tr.setLayoutParams(getLayoutParams());
    tr.addView(getTextView(0, "Название", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Дата проведения", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Время начала", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Время окончания", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Место проведения", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Стоимость участия", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Справка", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tl.addView(tr, getTblLayoutParams());
  }

  private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
    TextView tv = new TextView(getContext());
    tv.setId(id);
    tv.setText(title.toUpperCase());
    tv.setTextColor(color);
    tv.setPadding(10, 10, 10, 10);
    tv.setTypeface(Typeface.DEFAULT, typeface);
    tv.setTextSize(8);
    tv.setBackgroundColor(bgColor);
    tv.setBackgroundResource(bgColor);
    tv.setLayoutParams(getLayoutParams());
    tv.setOnClickListener(this);
    return tv;
  }

  @NonNull
  private TableRow.LayoutParams getLayoutParams() {
    TableRow.LayoutParams params = new TableRow.LayoutParams(
        TableRow.LayoutParams.MATCH_PARENT,
        TableRow.LayoutParams.MATCH_PARENT);
    params.setMargins(1, 1, 1, 1);
    params.weight = 1;
    return params;
  }

  @NonNull
  private TableLayout.LayoutParams getTblLayoutParams() {
    return new TableLayout.LayoutParams(
        TableRow.LayoutParams.MATCH_PARENT,
        TableRow.LayoutParams.MATCH_PARENT);
  }

  public void addRows(List<Events> eventsList) {
    for (int i = 0; i < eventsList.size(); i++) {
      TableRow tr = new TableRow(getContext());
      tr.setLayoutParams(getLayoutParams());
      if (eventsList.get(i).getVacancies() != null || eventsList.get(i).getEntranceFee() != null) {
        String pattern = "dd.MM.yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String eventDateAsString = df.format(eventsList.get(i).getEventDate());
        tr.addView(getRowsTextView(0, eventsList.get(i).getEventName(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventDateAsString, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getTimeFrom(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getTimeTo(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getPlace(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getEntranceFee().toString(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getReference(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Добавить снаряжение", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape, "add"));
        tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Удалить", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape, "del"));
        tableLayout.addView(tr, getTblLayoutParams());
      }
    }

  }
  private TextView getRowsButtonView(int id, String title, int color, int typeface, int bgColor, String type) {
    Button tv = new Button(getContext());
    tv.setId(id);
    tv.setText(title);
    tv.setTextColor(color);
    tv.setPadding(10, 10, 10, 10);
    tv.setTypeface(Typeface.DEFAULT, typeface);
    tv.setBackgroundResource(bgColor);
    tv.setLayoutParams(getLayoutParams());
    tv.setTextSize(8);
    switch (type) {
      case "add":
        tv.setOnClickListener(addBtnCancel);
        break;
      case "del":
        tv.setOnClickListener(delBtnCancel);
        break;
    }
    return tv;
  }

  private TextView getRowsTextView(int id, String title, int color, int typeface, int bgColor) {
    TextView tv = new TextView(getContext());
    tv.setId(id);
    tv.setText(title);
    tv.setTextColor(color);
    tv.setPadding(10, 10, 10, 10);
    tv.setTypeface(Typeface.DEFAULT, typeface);
    tv.setBackgroundResource(bgColor);
    tv.setLayoutParams(getLayoutParams());
    tv.setOnClickListener(this);
    return tv;
  }

  @Override
  public void onClick(View view) {

  }
}