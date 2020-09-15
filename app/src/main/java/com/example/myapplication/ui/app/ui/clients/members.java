package com.example.myapplication.ui.app.ui.clients;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Clients;
import com.example.myapplication.data.model.ClientsDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.data.model.InventoryEventsPlayers;
import com.example.myapplication.data.model.InventoryEventsPlayersDao;
import com.example.myapplication.data.model.JoinEventsWithClients;
import com.example.myapplication.data.model.JoinEventsWithClientsDao;
import com.example.myapplication.utils.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с фрагментами участников мероприятий
 */
public class members extends Fragment implements View.OnClickListener{
  TableLayout tableLayout;
  View.OnClickListener oclBtnCancel;
  Integer id_events = 0;
  private JoinEventsWithClientsDao joinEventsWithClientsDao;
  private ClientsDao clientsDao;
  private EventsDao eventsDao;
  private InventoryEventsPlayersDao inventoryEventsPlayersDao;
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    oclBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteClients(v.getId());
        Toast.makeText(getContext(),"Удаление успешно произведено!!!", Toast.LENGTH_SHORT).show();
      }
    };
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    initViews(view);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    clientsDao = daoSession.getClientsDao();
    eventsDao  = daoSession.getEventsDao();
    joinEventsWithClientsDao = daoSession.getJoinEventsWithClientsDao();
    inventoryEventsPlayersDao = daoSession.getInventoryEventsPlayersDao();
    Bundle bundle = getArguments();
    if (bundle != null){
      id_events = getArguments().getInt("id_events");
    }
    return inflater.inflate(R.layout.fragment_members, container, false);
  }

  public void initViews(View view){
    tableLayout = (TableLayout) view.findViewById(R.id.tableLayout66);
    addHeaders(view);
    Events events = eventsDao.loadByRowId(id_events);
    List<JoinEventsWithClients> joinEventsWithClientsList = joinEventsWithClientsDao.queryBuilder()
        .where(JoinEventsWithClientsDao.Properties.EventId.eq(events.getId())).list();
    List<Clients> clientsList = new ArrayList<>();
    if (joinEventsWithClientsList != null){
      for (JoinEventsWithClients i: joinEventsWithClientsList) {
        clientsList.add(clientsDao.loadByRowId(i.getClientId()));
      }
    }
    addRows(clientsList);
  }

  public void addHeaders(View view) {
    TableLayout tl = view.findViewById(R.id.tableLayout66);
    TableRow tr = new TableRow(getContext());
    tr.setLayoutParams(getLayoutParams());
    tr.addView(getTextView(0, "Имя", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tr.addView(getTextView(0, "Отчество", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tr.addView(getTextView(0, "Фамилия", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tr.addView(getTextView(0, "Номер телефона", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tr.addView(getTextView(0, "Возраст", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tr.addView(getTextView(0, "Серия и номер паспорта", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tr.addView(getTextView(0, "", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape ));
    tl.addView(tr, getTblLayoutParams());
  }

  private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
    TextView tv = new TextView(getContext());
    tv.setId(id);
    tv.setText(title.toUpperCase());
    tv.setTextColor(color);
    tv.setPadding(10, 10, 10, 10);
    tv.setTypeface(Typeface.DEFAULT, typeface);
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

  @Override
  public void onClick(View v) {

  }

  /**
   * Удалить участников
   * @param id
   */
  public void deleteClients(int id){
    List<InventoryEventsPlayers> inventoryEventsPlayersList = inventoryEventsPlayersDao.queryBuilder()
        .where(InventoryEventsPlayersDao.Properties.ClientId.eq(id), InventoryEventsPlayersDao.Properties.EventId.eq(id_events)).list();
    List<JoinEventsWithClients> joinEventsWithClientsList = joinEventsWithClientsDao.queryBuilder()
        .where(JoinEventsWithClientsDao.Properties.EventId.eq(id_events),JoinEventsWithClientsDao.Properties.ClientId.eq(id)).list();
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
  }
  public void addRows(List<Clients> clientsList){
    for (int i = 0; i < clientsList.size(); i++) {
      TableRow tr = new TableRow(getContext());
      tr.setLayoutParams(getLayoutParams());
      tr.addView(getRowsTextView(0, clientsList.get(i).getName(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape ));
      tr.addView(getRowsTextView(0, clientsList.get(i).getPatronymic(), Color.BLACK, Typeface.NORMAL ,R.drawable.cell_shape ));
      tr.addView(getRowsTextView(0, clientsList.get(i).getSurname(), Color.BLACK, Typeface.NORMAL ,R.drawable.cell_shape ));
      tr.addView(getRowsTextView(0, clientsList.get(i).getPhoneNumber(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape ));
      tr.addView(getRowsTextView(0, clientsList.get(i).getAge().toString(), Color.BLACK, Typeface.NORMAL ,R.drawable.cell_shape ));
      tr.addView(getRowsTextView(0,  clientsList.get(i).getSeriesNumber(), Color.BLACK, Typeface.NORMAL ,R.drawable.cell_shape ));
      tr.addView(getRowsButtonView( clientsList.get(i).getId().intValue()));
      tableLayout.addView(tr, getTblLayoutParams());
    }

  }

  private TextView getRowsButtonView(int id) {
    Button tv = new Button(getContext());
    tv.setId(id);
    tv.setText("Удалить");
    tv.setTextColor(Color.BLACK);
    tv.setPadding(10, 10, 10, 10);
    tv.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    tv.setBackgroundResource(R.drawable.cell_shape);
    tv.setLayoutParams(getLayoutParams());
    tv.setOnClickListener(oclBtnCancel);
    return tv;
  }
  private TextView getRowsTextView(int id, String title, int color, int typeface,int bgColor) {
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
}