package com.example.myapplication.ui.app.ui.events;

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
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.data.model.JoinEventsWithClients;
import com.example.myapplication.data.model.JoinEventsWithClientsDao;
import com.example.myapplication.ui.app.ui.clients.addClient;
import com.example.myapplication.ui.app.ui.clients.members;
import com.example.myapplication.utils.App;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class events extends Fragment implements View.OnClickListener {

  TableLayout tableLayout;
  View.OnClickListener delBtnCancel;
  View.OnClickListener updBtnCancel;
  View.OnClickListener addClientBtnCancel;
  View.OnClickListener lookClientBtnCancel;
  private EventsDao eventsDao;
  private JoinEventsWithClientsDao joinEventsWithClientsDao;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    Button addEvents = view.findViewById(R.id.eventAdd);
    addEvents.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addEvents();
        Toast.makeText(getContext(), "Добавление нового мероприятия!!!", Toast.LENGTH_SHORT).show();
      }
    });
    delBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteEvents(v.getId());
        Toast.makeText(getContext(), "Удаление успешно произведено!!!", Toast.LENGTH_SHORT).show();
      }
    };
    updBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateEvents(v.getId());
        Toast.makeText(getContext(), "Обновление мероприятия!!!", Toast.LENGTH_SHORT).show();
      }
    };
    addClientBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addClientEvents(v.getId());
        Toast.makeText(getContext(), "Добавление участников!!!", Toast.LENGTH_SHORT).show();
      }
    };
    lookClientBtnCancel = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int sa = v.getId();
        viewClientEvents(v.getId());
        Toast.makeText(getContext(), "Просмотр участников!!!", Toast.LENGTH_SHORT).show();
      }
    };
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    initViews(view);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    eventsDao = daoSession.getEventsDao();
    joinEventsWithClientsDao =daoSession.getJoinEventsWithClientsDao();
    View v = inflater.inflate(R.layout.fragment_events, container, false);
    return v;
  }

  private void addEvents() {
    FragmentManager manager = getFragmentManager();
    manager.beginTransaction()
        .replace(R.id.nav_host_fragment, new eventsAdd())
        .addToBackStack(null)
        .commit();
  }

  public void initViews(View view) {
    tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
    addHeaders(view);
    List<Events> eventsList = eventsDao.loadAll();
    if (eventsList.size() != 0) {
      addRows(eventsList);
    }
  }

  public void addHeaders(View view) {
    TableLayout tl = view.findViewById(R.id.tableLayout);
    TableRow tr = new TableRow(getContext());
    tr.setLayoutParams(getLayoutParams());
    tr.addView(getTextView(0, "Название", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Дата проведения", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Время начала", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Время окончания", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Место проведения", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Минимум игроков", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Стоимость участия", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Справка", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    //tr.addView(getTextView(0, "", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
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

  public void updateEvents(int id) {
    FragmentManager manager = getFragmentManager();
    Bundle args = new Bundle();
    args.putInt("id_events", id);
    eventsAdd newFragment = new eventsAdd();
    newFragment.setArguments(args);
    manager.beginTransaction().replace(R.id.nav_host_fragment, newFragment)
        .addToBackStack(null)
        .commit();
  }

  public void addClientEvents(int id) {
    FragmentManager manager = getFragmentManager();
    Bundle args = new Bundle();
    args.putInt("id_events", id);
    addClient newFragment = new addClient();
    newFragment.setArguments(args);
    manager.beginTransaction().replace(R.id.nav_host_fragment, newFragment)
        .addToBackStack(null)
        .commit();
  }

  public void viewClientEvents(int id) {
    FragmentManager manager = getFragmentManager();
    Bundle args = new Bundle();
    args.putInt("id_events", id);
    members newFragment = new members();
    newFragment.setArguments(args);
    manager.beginTransaction().replace(R.id.nav_host_fragment, newFragment)
        .addToBackStack(null)
        .commit();
  }

  public void deleteEvents(int id) {
    Events events = eventsDao.loadByRowId(id);
    List<JoinEventsWithClients> joinEventsWithClientsList = joinEventsWithClientsDao.queryBuilder()
        .where(JoinEventsWithClientsDao.Properties.EventId.eq(events.getId())).list();
    if (joinEventsWithClientsList != null){
      for (JoinEventsWithClients i: joinEventsWithClientsList) {
        joinEventsWithClientsDao.delete(i);
      }
    }
    events.delete();
    events events1 =new events();
    FragmentManager manager = getFragmentManager();
    manager.beginTransaction().replace(R.id.nav_host_fragment, events1)
        .addToBackStack(null)
        .commit();
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
        tr.addView(getRowsTextView(0, eventsList.get(i).getVacancies().toString(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getEntranceFee().toString(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsTextView(0, eventsList.get(i).getReference(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Изменить", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape, "edit"));
        tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Удалить", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape, "del"));
        //tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Добавить участников", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape, "add"));
        tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Посмотреть участников", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape, "look"));
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
    tv.setTextSize(7);
    switch (type) {
      case "edit":
        tv.setOnClickListener(updBtnCancel);
        break;
      case "del":
        tv.setOnClickListener(delBtnCancel);
        break;
      case "add":
        tv.setOnClickListener(addClientBtnCancel);
        break;
      case "look":
        tv.setOnClickListener(lookClientBtnCancel);
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