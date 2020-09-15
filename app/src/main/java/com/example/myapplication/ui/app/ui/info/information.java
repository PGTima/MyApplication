package com.example.myapplication.ui.app.ui.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.app.MainActivityDriwer;

public class information extends Fragment {
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    EditText info = view.findViewById(R.id.textView21);
    info.setText("fsfbsfbsfs");

  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_information, container, false);
  }
}