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
import com.example.myapplication.ui.app.ui.equipment.orderFragment;
import com.example.myapplication.utils.App;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class eventsManager extends Fragment implements View.OnClickListener  {

  TableLayout tableLayout;

  View.OnClickListener lookClientBtn;
  private EventsDao eventsDao;
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {

    lookClientBtn = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        lookClientEq(v.getId());
        Toast.makeText(getContext(), "Просмотр заказов!!!", Toast.LENGTH_SHORT).show();
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
    return inflater.inflate(R.layout.fragment_events_manager, container, false);
  }
  public void initViews(View view) {
    tableLayout = (TableLayout) view.findViewById(R.id.tableLayout65);
    addHeaders(view);
    List<Events> eventsList = eventsDao.loadAll();
    if (eventsList.size() != 0) {
      addRows(eventsList);
    }
  }

  public void addHeaders(View view) {
    TableLayout tl = view.findViewById(R.id.tableLayout65);
    TableRow tr = new TableRow(getContext());
    tr.setLayoutParams(getLayoutParams());
    tr.addView(getTextView(0, "Название", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Дата проведения", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Время начала", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Место проведения", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
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


  public void lookClientEq(int id) {
    FragmentManager manager = getFragmentManager();
    Bundle args = new Bundle();
    args.putInt("id_events", id);
    orderFragment newFragment = new orderFragment();
    newFragment.setArguments(args);
    manager.beginTransaction().replace(R.id.nav_host_fragment, newFragment)
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
        tr.addView(getRowsTextView(0, eventsList.get(i).getPlace(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tr.addView(getRowsButtonView(eventsList.get(i).getId().intValue(), "Посмотреть заказное снаряжение", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
        tableLayout.addView(tr, getTblLayoutParams());
      }
    }

  }

  private TextView getRowsButtonView(int id, String title, int color, int typeface, int bgColor) {
    Button tv = new Button(getContext());
    tv.setId(id);
    tv.setText(title);
    tv.setTextColor(color);
    tv.setPadding(10, 10, 10, 10);
    tv.setTypeface(Typeface.DEFAULT, typeface);
    tv.setBackgroundResource(bgColor);
    tv.setLayoutParams(getLayoutParams());
    tv.setTextSize(7);
    tv.setOnClickListener(lookClientBtn);
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