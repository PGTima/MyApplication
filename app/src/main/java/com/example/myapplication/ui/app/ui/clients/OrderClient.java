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
import com.example.myapplication.data.model.Clients;
import com.example.myapplication.data.model.ClientsDao;
import com.example.myapplication.data.model.ColorTypeDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.EquipmentTypeDao;
import com.example.myapplication.data.model.Events;
import com.example.myapplication.data.model.EventsDao;
import com.example.myapplication.data.model.Inventory;
import com.example.myapplication.data.model.InventoryDao;
import com.example.myapplication.data.model.InventoryEventsPlayers;
import com.example.myapplication.data.model.InventoryEventsPlayersDao;
import com.example.myapplication.data.model.SizeTypeDao;
import com.example.myapplication.ui.app.ui.equipment.equipmentFragment;
import com.example.myapplication.utils.App;

import java.util.List;

/**
 *
 */
public class OrderClient extends Fragment implements View.OnClickListener{

  TableLayout tableLayout;
  private EventsDao eventsDao;
  private InventoryDao inventoryDao;
  private InventoryEventsPlayersDao inventoryEventsPlayersDao;
  private SizeTypeDao sizeTypeDao;
  private ColorTypeDao colorTypeDao;
  private EquipmentTypeDao equipmentTypeDao;
  private ClientsDao clientsDao;
  View.OnClickListener delBtnCancel;
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
    initViews(view);
  }

  private void delRelationClientEquipment(int id) {
    InventoryEventsPlayers inventoryEventsPlayers = inventoryEventsPlayersDao.loadByRowId(id);
    inventoryEventsPlayersDao.delete(inventoryEventsPlayers);
    FragmentManager manager = getFragmentManager();
    manager.beginTransaction()
        .replace(R.id.nav_host_fragment, new OrderClient())
        .addToBackStack(null)
        .commit();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    inventoryDao = daoSession.getInventoryDao();
    eventsDao = daoSession.getEventsDao();
    sizeTypeDao = daoSession.getSizeTypeDao();
    colorTypeDao = daoSession.getColorTypeDao();
    clientsDao = daoSession.getClientsDao();
    equipmentTypeDao = daoSession.getEquipmentTypeDao();
    inventoryEventsPlayersDao = daoSession.getInventoryEventsPlayersDao();
    Intent intent = getActivity().getIntent();
    user_id = intent.getLongExtra("user_id",0);
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    return inflater.inflate(R.layout.fragment_order_client, container, false);
  }
  public void initViews(View view) {
    tableLayout = (TableLayout) view.findViewById(R.id.tableLayout15);
    addHeaders(view);
    List<InventoryEventsPlayers> inventoryEventsPlayersList = inventoryEventsPlayersDao.queryBuilder()
        .where(InventoryEventsPlayersDao.Properties.ClientId.eq(user_id)).list();
    if (inventoryEventsPlayersList.size() != 0) {
      addRows(inventoryEventsPlayersList);
    }
  }

  public void addHeaders(View view) {
    TableLayout tl = view.findViewById(R.id.tableLayout15);
    TableRow tr = new TableRow(getContext());
    tr.setLayoutParams(getLayoutParams());
    tr.addView(getTextView(0, "Артикул", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Название", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Категория", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Стоимость проката", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Размер", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Цвет", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Фамилия", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Серия и номер паспорта", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Название мероприятия", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
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

  public void addRows(List<InventoryEventsPlayers> inventoryEventsPlayersList) {
    for (int i = 0; i < inventoryEventsPlayersList.size(); i++) {
      TableRow tr = new TableRow(getContext());
      Inventory inventory = inventoryDao.loadByRowId(inventoryEventsPlayersList.get(i).getInventoryId());
      Clients clients = clientsDao.loadByRowId(inventoryEventsPlayersList.get(i).getClientId());
      Events events = eventsDao.loadByRowId(inventoryEventsPlayersList.get(i).getEventId());
      String  size = sizeTypeDao.loadByRowId(inventory.getSizeTypeId()).getName();
      String  color = colorTypeDao.loadByRowId(inventory.getColorTypeId()).getName();
      String  category = equipmentTypeDao.loadByRowId(inventory.getEquipmentTypeId()).getName();
      tr.setLayoutParams(getLayoutParams());
      tr.addView(getRowsTextView(0, inventory.getVendor(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, inventory.getName(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, category, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0,
          inventory.getRentalCost() != null ? inventory.getRentalCost().toString() : " ", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, size, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, color, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, clients.getName(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, clients.getSeriesNumber(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, events.getEventName(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsButtonView(inventoryEventsPlayersList.get(i).getId().intValue(), "Удалить", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tableLayout.addView(tr, getTblLayoutParams());
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
    tv.setTextSize(8);
    tv.setOnClickListener(delBtnCancel);
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
  public void onClick(View v) {

  }
}