package com.example.jebus_vladimir.presion_arterial;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import net.danlew.android.joda.JodaTimeAndroid;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main);
        File file = new File(getFilesDir(), "resta.txt");
        if (!file.exists())  {
            FileOutputStream oS;
            try{
                oS = openFileOutput("resta.txt", Context.MODE_PRIVATE);
                oS.write(Integer.toString(-1).getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void startLogin(View v) {
        Intent intent = new Intent(MainActivity.this, LoginUsuario.class);
        startActivity(intent);
    }

    public void startUsuario(View v) {
        Intent intent = new Intent(MainActivity.this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void startMedico(View v) {
        Intent intent = new Intent(MainActivity.this, RegistroMedico.class);
        startActivity(intent);
    }
}
