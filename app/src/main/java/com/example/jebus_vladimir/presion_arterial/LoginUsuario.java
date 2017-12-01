package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUsuario extends Activity {
    private EditText a, b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
    }

    public void cancelar(View v) {
        finish();
    }

    public void aceptar(View v){
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        int t;
        String u = a.getText().toString().trim();
        String p = b.getText().toString().trim();
        String qu = "SELECT id FROM persona WHERE user = '"+u+"' and PASS = '"+p+"'";
        Cursor fila = db.rawQuery(qu, null);
        if (fila.moveToFirst()) {
            t = fila.getInt( fila.getColumnIndex("id") );
            Toast.makeText(this, R.string.bienvenido, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginUsuario.this, MenuPrincipal.class);
            intent.putExtra("id", t );
            db.close();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            db.close();
        }



    }

}
