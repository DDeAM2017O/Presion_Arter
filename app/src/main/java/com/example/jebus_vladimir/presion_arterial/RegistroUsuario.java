package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroUsuario extends Activity {

    private EditText a, b, c, d, e, f, g, h;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
        c = (EditText) findViewById(R.id.name);
        d = (EditText) findViewById(R.id.dir);
        e = (EditText) findViewById(R.id.cel);
        f = (EditText) findViewById(R.id.altura);
        g = (EditText) findViewById(R.id.peso);
        h = (EditText) findViewById(R.id.edad);
    }

    public void cancelar(View v) {
        finish();
    }

    public void aceptar(View v){
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();

        String aa= a.getText().toString().trim();
        String bb= b.getText().toString().trim();
        String cc= c.getText().toString().trim();
        String dd= d.getText().toString().trim();
        String ee= e.getText().toString().trim();
        String ff= f.getText().toString().trim();
        String gg= g.getText().toString().trim();
        String hh= h.getText().toString().trim();
        ContentValues inst = new ContentValues();
        inst.put("`user`", aa );
        inst.put("`pass`", bb );
        inst.put("`nombre`", cc );
        inst.put("`dir`", dd );
        inst.put("`tel`", ee );
        inst.put("`altura`", ff );
        inst.put("`peso`", gg );
        inst.put("`edad`", hh );
        long idT = db.insert("persona", null, inst);
        db.close();
        if(idT != -1) {
            Toast.makeText(this, "Usuario ingresado" , Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
           Toast.makeText(this, "Hubo problema", Toast.LENGTH_SHORT).show();
        }

    }
}
