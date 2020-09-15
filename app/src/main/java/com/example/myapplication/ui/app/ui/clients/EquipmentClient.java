package com.example.myapplication.ui.app.ui.clients;

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
import com.example.myapplication.data.model.ColorTypeDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.EquipmentTypeDao;
import com.example.myapplication.data.model.Inventory;
import com.example.myapplication.data.model.InventoryDao;
import com.example.myapplication.data.model.InventoryEventsPlayers;
import com.example.myapplication.data.model.InventoryEventsPlayersDao;
import com.example.myapplication.data.model.JoinEventsWithClientsDao;
import com.example.myapplication.data.model.SizeTypeDao;
import com.example.myapplication.data.model.TypeOfConditionDao;
import com.example.myapplication.utils.App;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для работы с Оборудованием клиента
 */
public class EquipmentClient extends Fragment implements View.OnClickListener {
  TableLayout tableLayout;
  View.OnClickListener addBtn;
  private InventoryDao inventoryDao;
  private InventoryEventsPlayersDao inventoryEventsPlayersDao;
  private SizeTypeDao sizeTypeDao;
  private TypeOfConditionDao typeOfConditionDao;
  private ColorTypeDao colorTypeDao;
  private EquipmentTypeDao equipmentTypeDao;
  private JoinEventsWithClientsDao joinEventsWithClientsDao;
  private Long id_user;
  private Integer id_events;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    addBtn = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addEquipment(v.getId());
        Toast.makeText(getContext(), "Оборудование забронировано!!!", Toast.LENGTH_SHORT).show();
      }
    };
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    initViews(view);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    inventoryDao = daoSession.getInventoryDao();
    sizeTypeDao = daoSession.getSizeTypeDao();
    typeOfConditionDao = daoSession.getTypeOfConditionDao();
    colorTypeDao = daoSession.getColorTypeDao();
    equipmentTypeDao = daoSession.getEquipmentTypeDao();
    inventoryEventsPlayersDao = daoSession.getInventoryEventsPlayersDao();
    joinEventsWithClientsDao = daoSession.getJoinEventsWithClientsDao();
    Bundle bundle = getArguments();
    if (bundle != null) {
      id_events = getArguments().getInt("id_events");
      id_user = getArguments().getLong("id_user");
    }
    return inflater.inflate(R.layout.fragment_equipment_client, container, false);
  }

  /**
   * Привязать оборудование к клиенту и мероприятию
   *
   * @param id_inv
   */
  private void addEquipment(Integer id_inv) {
    InventoryEventsPlayers inventoryEventsPlayers = new InventoryEventsPlayers();
    inventoryEventsPlayers.setClientId(id_user);
    inventoryEventsPlayers.setEventId((long) id_events);
    inventoryEventsPlayers.setInventoryId((long) id_inv);
    inventoryEventsPlayersDao.insert(inventoryEventsPlayers);
    FragmentManager manager = getFragmentManager();
    manager.beginTransaction()
        .replace(R.id.nav_host_fragment, new EventsForClient())
        .addToBackStack(null)
        .commit();
  }

  public void initViews(View view) {
    tableLayout = (TableLayout) view.findViewById(R.id.tableLayout24);
    addHeaders(view);
    QueryBuilder<Inventory> queryBuilder = inventoryDao.queryBuilder();
    queryBuilder.where(new WhereCondition.StringCondition(
        "_ID NOT IN" + "(SELECT INVENTORY_ID FROM INVENTORY_EVENTS_PLAYERS )"
    )).build();
    List<Inventory> inventoryList = queryBuilder.list();

    if (inventoryList.size() != 0) {
      addRows(inventoryList);
    }

  }

  public void addHeaders(View view) {
    TableLayout tl = view.findViewById(R.id.tableLayout24);
    TableRow tr = new TableRow(getContext());
    tr.setLayoutParams(getLayoutParams());
    tr.addView(getTextView(0, "Артикул", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Название", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Категория", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Стоимость проката", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Количество резервов", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Размер", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Состояние", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Цвет", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
    tr.addView(getTextView(0, "Характеристика", Color.BLACK, Typeface.BOLD, R.drawable.cell_shape));
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

  public void addRows(List<Inventory> inventoryList) {
    for (int i = 0; i < inventoryList.size(); i++) {
      TableRow tr = new TableRow(getContext());
      String size = sizeTypeDao.loadByRowId(inventoryList.get(i).getSizeTypeId()).getName();
      String typeOf = typeOfConditionDao.loadByRowId(inventoryList.get(i).getTypeOfConditionId()).getName();
      String color = colorTypeDao.loadByRowId(inventoryList.get(i).getColorTypeId()).getName();
      String category = equipmentTypeDao.loadByRowId(inventoryList.get(i).getEquipmentTypeId()).getName();
      tr.setLayoutParams(getLayoutParams());
      tr.addView(getRowsTextView(0, inventoryList.get(i).getVendor(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, inventoryList.get(i).getName(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, category, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0,
          inventoryList.get(i).getRentalCost() != null ? inventoryList.get(i).getRentalCost().toString() : " ", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, inventoryList.get(i).getBookingPresence(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, size, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, typeOf, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, color, Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsTextView(0, inventoryList.get(i).getSpecification(), Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
      tr.addView(getRowsButtonView(inventoryList.get(i).getId().intValue(), "Забронировать", Color.BLACK, Typeface.NORMAL, R.drawable.cell_shape));
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
    tv.setTextSize(7);
    tv.setOnClickListener(addBtn);
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