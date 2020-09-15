package com.example.myapplication.ui.app.ui.events;

import android.content.Intent;
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
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.utils.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class eventsAdd extends Fragment {
  Integer id_events = 0;
  private EventsDao eventsDao;
  public void onViewCreated(final View view, Bundle savedInstanceState) {
    Button addEvents = view.findViewById(R.id.button5);
      if (id_events != 0) {
        Events events = eventsDao.loadByRowId(id_events);
        if (events != null) {
          String datePattern = "dd.mm.yyyy";
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
          String dateString = simpleDateFormat.format(events.getEventDate());
          ((EditText) view.findViewById(R.id.editTextTextPersonName)).setText(events.getEventName());
          ((EditText) view.findViewById(R.id.editTextDate)).setText(dateString);
          ((EditText) view.findViewById(R.id.editTextTextPersonName2)).setText(events.getTimeFrom());
          ((EditText) view.findViewById(R.id.editTextTextPersonName3)).setText(events.getTimeTo());
          ((EditText) view.findViewById(R.id.editTextTextPersonName4)).setText(events.getPlace());
          ((EditText) view.findViewById(R.id.editTextTextPersonName5)).setText(events.getVacancies().toString());
          ((EditText) view.findViewById(R.id.editTextTextPersonName6)).setText(events.getEntranceFee().toString());
          ((EditText) view.findViewById(R.id.editTextTextPersonName7)).setText(events.getReference());
        }
      }
    addEvents.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          addEventsFunc(id_events, view);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    });
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    eventsDao  = daoSession.getEventsDao();
    Bundle bundle = getArguments();
    if (bundle != null){
      id_events = getArguments().getInt("id_events");
    }
    return inflater.inflate(R.layout.fragment_events_add, container, false);
  }
  private Boolean validateDate(String strDate, View view){
    EditText eventDate = view.findViewById(R.id.editTextDate);
      if (strDate.trim().equals(""))
      {
        eventDate.setError("Поле не может быть пустым!!!");
        return false;
      }else {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.mm.yyyy");
        sdfrmt.setLenient(false);
        try
        {
          Date javaDate = sdfrmt.parse(strDate);
          eventDate.setError(null);
          return true;
        }
        catch (ParseException e)
        {
          eventDate.setError("Введите корректную дату!!!");
          return false;
        }
      }
  }
  private void addEventsFunc(Integer id_events, View view) throws ParseException {
    String eventName = ((EditText) view.findViewById(R.id.editTextTextPersonName)).getText().toString();
    String eventDate = ((EditText) view.findViewById(R.id.editTextDate)).getText().toString();
    if (validateDate(eventDate, view)) {
      String timeFrom = ((EditText) view.findViewById(R.id.editTextTextPersonName2)).getText().toString();
      String timeTo = ((EditText) view.findViewById(R.id.editTextTextPersonName3)).getText().toString();
      String place = ((EditText) view.findViewById(R.id.editTextTextPersonName4)).getText().toString();
      String countPlace = ((EditText) view.findViewById(R.id.editTextTextPersonName5)).getText().toString();
      String entranceFee = ((EditText) view.findViewById(R.id.editTextTextPersonName6)).getText().toString();
      String reference = ((EditText) view.findViewById(R.id.editTextTextPersonName7)).getText().toString();
      SimpleDateFormat format = new SimpleDateFormat();
      format.applyPattern("dd.mm.yyyy");
      Date docDate= format.parse(eventDate);
      if (id_events == 0) {
        List<Clients> clients = new ArrayList<Clients>();
        Events events = new Events();
        events.setEventName(eventName);
        events.setReference(reference);
        events.setEntranceFee(Integer.valueOf(entranceFee));
        events.setPlace(place);
        events.setTimeFrom(timeFrom);
        events.setTimeTo(timeTo);
        events.setEventDate(docDate);
        events.setVacancies(Integer.valueOf(countPlace));
        events.setClients(clients);
        eventsDao.insert(events);
        Toast.makeText(getContext(), "Мероприятие добавлено!!!", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
            .replace(R.id.nav_host_fragment, new events())
            .addToBackStack(null)
            .commit();
      } else {
        Events events = eventsDao.loadByRowId(id_events);
        if (events != null) {
          events.setEventName(eventName);
          events.setEventDate(docDate);
          events.setTimeTo(timeTo);
          events.setTimeFrom(timeFrom);
          events.setPlace(place);
          events.setVacancies(Integer.valueOf(countPlace));
          events.setEntranceFee(Integer.valueOf(entranceFee));
          events.setReference(reference);
          eventsDao.update(events);
          Toast.makeText(getContext(), "Изменение успешно выполнено!!!", Toast.LENGTH_SHORT).show();
          FragmentManager manager = getFragmentManager();
          manager.beginTransaction()
              .replace(R.id.nav_host_fragment, new events())
              .addToBackStack(null)
              .commit();
        }
      }
    } else {
      Toast.makeText(getContext(), "Ошибка валидации!!!", Toast.LENGTH_SHORT).show();
    }
  }
}