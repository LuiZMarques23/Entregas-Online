package com.example.entregasonline.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.entregasonline.R;

public class UsuarioHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_home);
        getWindow().setStatusBarColor(Color.parseColor("#EA1C2B"));
    }
}