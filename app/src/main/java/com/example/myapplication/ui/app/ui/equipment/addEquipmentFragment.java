package com.example.myapplication.ui.app.ui.equipment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.ColorType;
import com.example.myapplication.data.model.ColorTypeDao;
import com.example.myapplication.data.model.DaoSession;
import com.example.myapplication.data.model.EquipmentType;
import com.example.myapplication.data.model.EquipmentTypeDao;
import com.example.myapplication.data.model.Inventory;
import com.example.myapplication.data.model.InventoryDao;
import com.example.myapplication.data.model.InventoryEventsPlayersDao;
import com.example.myapplication.data.model.SizeType;
import com.example.myapplication.data.model.SizeTypeDao;
import com.example.myapplication.data.model.TypeOfCondition;
import com.example.myapplication.data.model.TypeOfConditionDao;
import com.example.myapplication.ui.app.ui.events.events;
import com.example.myapplication.utils.App;

import java.util.ArrayList;
import java.util.List;


public class addEquipmentFragment extends Fragment {
  private InventoryDao inventoryDao;
  private InventoryEventsPlayersDao inventoryEventsPlayersDao;
  private SizeTypeDao sizeTypeDao;
  private TypeOfConditionDao typeOfConditionDao;
  private ColorTypeDao colorTypeDao;
  private EquipmentTypeDao equipmentTypeDao;
  private List<ColorType> colorTypeList= new ArrayList<>();
  private List<SizeType> sizeTypeList= new ArrayList<>();
  private List<TypeOfCondition> typeOfConditionList= new ArrayList<>();
  private List<EquipmentType> equipmentTypeList= new ArrayList<>();
  private List<String> colorTypeList1= new ArrayList<>();
  private List<String> sizeTypeList1= new ArrayList<>();
  private List<String> typeOfConditionList1= new ArrayList<>();
  private List<String> equipmentTypeList1= new ArrayList<>();
  Spinner sizeSpinner ;
  Spinner colorSpinner ;
  Spinner equipmentTypeSpinner ;
  Spinner conditionSpinner;
  Integer id_equipment = 0;
  @Override
  public void onViewCreated(final View view, Bundle savedInstanceState) {
    Button addEquipment = view.findViewById(R.id.button15);
    sizeSpinner = (Spinner) view.findViewById(R.id.spinner2);
    colorSpinner = (Spinner) view.findViewById(R.id.spinner4);
    equipmentTypeSpinner = (Spinner) view.findViewById(R.id.spinner);
    conditionSpinner = (Spinner) view.findViewById(R.id.spinner3);
    colorTypeList = colorTypeDao.loadAll();
    sizeTypeList= sizeTypeDao.loadAll();
    typeOfConditionList = typeOfConditionDao.loadAll();
    equipmentTypeList = equipmentTypeDao.loadAll();
    for (ColorType i:colorTypeList) {
      colorTypeList1.add(i.getName());
    }
    for (SizeType i:sizeTypeList) {
      sizeTypeList1.add(i.getName());
    }
    for (TypeOfCondition i:typeOfConditionList) {
      typeOfConditionList1.add(i.getName());
    }
    for (EquipmentType i:equipmentTypeList) {
      equipmentTypeList1.add(i.getName());
    }
    ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, colorTypeList1 );
    ArrayAdapter<String> adapterSizeType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sizeTypeList1 );
    ArrayAdapter<String> adapterTypeOfCond = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typeOfConditionList1 );
    ArrayAdapter<String> adapterEquipmentType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, equipmentTypeList1 );
    sizeSpinner.setAdapter(adapterSizeType);
    colorSpinner.setAdapter(adapterColor);
    equipmentTypeSpinner.setAdapter(adapterEquipmentType);
    conditionSpinner.setAdapter(adapterTypeOfCond);
    if (id_equipment != 0 ){
      Inventory inventory = inventoryDao.loadByRowId(id_equipment);
      ((EditText) view.findViewById(R.id.editTextTextPersonName15)).setText(inventory.getName());
      ((EditText) view.findViewById(R.id.editTextTextPersonName14)).setText(inventory.getVendor());
      ((EditText) view.findViewById(R.id.editTextTextPersonName16)).setText(inventory.getRentalCost().toString());
      ((EditText) view.findViewById(R.id.editTextTextPersonName17)).setText(inventory.getSpecification());
      sizeSpinner.setSelection((int) inventory.getSizeTypeId() - 1);
      colorSpinner.setSelection((int) inventory.getColorTypeId() - 1);
      equipmentTypeSpinner.setSelection((int) inventory.getEquipmentTypeId() - 1);
      conditionSpinner.setSelection((int) inventory.getTypeOfConditionId() - 1);
    }
    addEquipment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addEquipment(view);
        Toast.makeText(getContext(), "Добавление нового оборудования!!!", Toast.LENGTH_SHORT).show();
      }
    });
    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  private void addEquipment( View view) {
    String eqName = ((EditText) view.findViewById(R.id.editTextTextPersonName15)).getText().toString();
    String eqArticle = ((EditText) view.findViewById(R.id.editTextTextPersonName14)).getText().toString();
    long equipmentTypeId = equipmentTypeSpinner.getSelectedItemPosition() == 0 ? 1: equipmentTypeSpinner.getSelectedItemPosition() + 1;
    String rentalCost = ((EditText) view.findViewById(R.id.editTextTextPersonName16)).getText().toString();
    long typeOfConditionId = conditionSpinner.getSelectedItemPosition() == 0 ? 1: conditionSpinner.getSelectedItemPosition() + 1;
    long sizeId = sizeSpinner.getSelectedItemPosition() == 0 ? 1: sizeSpinner.getSelectedItemPosition() + 1 ;
    long colorTypeId = colorSpinner.getSelectedItemPosition() == 0 ? 1: colorSpinner.getSelectedItemPosition() + 1;
    String spec = ((EditText) view.findViewById(R.id.editTextTextPersonName17)).getText().toString();

    if (id_equipment == 0) {
      Inventory inventory = new Inventory();
      inventory.setName(eqName);
      inventory.setVendor(eqArticle);
      inventory.setEquipmentTypeId(equipmentTypeId);
      inventory.setRentalCost(Integer.valueOf(rentalCost));
      inventory.setTypeOfConditionId(typeOfConditionId);
      inventory.setSizeTypeId(sizeId);
      inventory.setColorTypeId(colorTypeId);
      inventory.setSpecification(spec);
      inventoryDao.insert(inventory);
      FragmentManager manager = getFragmentManager();
      manager.beginTransaction()
          .replace(R.id.nav_host_fragment, new equipmentFragment())
          .addToBackStack(null)
          .commit();
    } else {
      Inventory inventory = inventoryDao.loadByRowId(id_equipment);
      if (inventory != null) {
        inventory.setName(eqName);
        inventory.setVendor(eqArticle);
        inventory.setEquipmentTypeId(equipmentTypeId);
        inventory.setRentalCost(Integer.valueOf(rentalCost));
        inventory.setTypeOfConditionId(typeOfConditionId);
        inventory.setSizeTypeId(sizeId);
        inventory.setColorTypeId(colorTypeId);
        inventory.setSpecification(spec);
        inventoryDao.update(inventory);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
            .replace(R.id.nav_host_fragment, new equipmentFragment())
            .addToBackStack(null)
            .commit();
        Toast.makeText(getContext(), "Изменение успешно выполнено!!!", Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
    inventoryDao = daoSession.getInventoryDao();
    sizeTypeDao = daoSession.getSizeTypeDao();
    typeOfConditionDao = daoSession.getTypeOfConditionDao();
    colorTypeDao = daoSession.getColorTypeDao();
    equipmentTypeDao = daoSession.getEquipmentTypeDao();
    inventoryEventsPlayersDao = daoSession.getInventoryEventsPlayersDao();
    Bundle bundle = getArguments();
    if (bundle != null){
      id_equipment = getArguments().getInt("id_equipment");
    }
    return inflater.inflate(R.layout.fragment_add_equipment, container, false);
  }
}