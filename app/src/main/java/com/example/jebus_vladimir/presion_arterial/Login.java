package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {
    private EditText a, b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
    }

    public void cancelar(View v) {
        finish();
    }

    public void aceptar(View v){
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();

        String u = a.getText().toString().trim();
        String p = b.getText().toString().trim();
        String qu = "select id from persona where user = '"+u+"' and pass = '"+p+"'";
        Cursor fila = db.rawQuery(qu, null);
        if (fila.moveToFirst()) {
            Toast.makeText(this, R.string.bienvenido, Toast.LENGTH_SHORT).show();
            db.close();
            Intent intent = new Intent(Login.this, Menu.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }


    }

}
