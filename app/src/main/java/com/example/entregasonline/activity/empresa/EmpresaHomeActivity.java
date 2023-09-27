package com.example.entregasonline.activity.empresa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.entregasonline.R;

public class EmpresaHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_home);
        getWindow().setStatusBarColor(Color.parseColor("#EA1C2B"));

    }
}