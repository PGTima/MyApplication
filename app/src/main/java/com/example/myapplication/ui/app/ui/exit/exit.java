package com.example.myapplication.ui.app.ui.exit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.app.MainActivityDriwer;
import com.example.myapplication.ui.app.ui.events.eventsAdd;
import com.example.myapplication.ui.app.ui.info.information;


public class exit extends Fragment {
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    Button exitYes = view.findViewById(R.id.exitYes);
    Button exitNo = view.findViewById(R.id.exitNo);
    exitYes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), MainActivityDriwer.class);
        startActivity(intent);
        Toast.makeText(getContext(), "До свидания!!!", Toast.LENGTH_SHORT).show();
      }
    });
    exitNo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentManager manager = getFragmentManager();
        information newFragment = new information();
        manager.beginTransaction().replace(R.id.nav_host_fragment, newFragment)
            .addToBackStack(null)
            .commit();
        Toast.makeText(getContext(), "Спасибо, что остались с нами!!!", Toast.LENGTH_SHORT).show();
      }
    });
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_exit, container, false);
  }
}