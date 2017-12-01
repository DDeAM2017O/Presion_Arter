package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroMedico extends Activity {
    private EditText a, b, c, d;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);
        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
        c = (EditText) findViewById(R.id.name);
        d = (EditText) findViewById(R.id.dir);
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        String qu = "select * from medico;";
        Cursor fila = db.rawQuery(qu, null);
        if (fila.moveToFirst()) {
            String aa = fila.getString( fila.getColumnIndex("nombre") );
            String bb = fila.getString( fila.getColumnIndex("dir") );
            String cc = fila.getString( fila.getColumnIndex("tel") );
            String dd = fila.getString( fila.getColumnIndex("email") );
            a.setText(aa);
            b.setText(bb);
            c.setText(cc);
            d.setText(dd);
        }
        db.close();
    }

    public void cancelar(View v) {
        finish();
    }

    public void aceptar(View v){
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();

        ContentValues inst = new ContentValues();
        String aa = a.getText().toString().trim();
        String bb = b.getText().toString().trim();
        String cc = c.getText().toString().trim();
        String dd = d.getText().toString().trim();
        inst.put("nombre", aa);
        inst.put("dir", bb);
        inst.put("tel", cc);
        inst.put("email", dd);

        String qu = "select * from medico;";
        Cursor fila = db.rawQuery(qu, null);

        if (!fila.moveToFirst()) {

            long idT = db.insert("medico", null, inst);

            if (idT != -1) {
                Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hubo problema", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            try {
                String val = fila.getString( fila.getColumnIndex("nombre") );
                String val1 = fila.getString( fila.getColumnIndex("dir") );
                String val2 = fila.getString( fila.getColumnIndex("tel") );
                String val3 = fila.getString( fila.getColumnIndex("email") );

                /*Toast.makeText(this, val, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, val1, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, val2, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, val3, Toast.LENGTH_SHORT).show();*/

                int colums= db.update("medico",inst,"nombre=? and dir=? and tel=? and email=?",new String[]{val,val1,val2,val3});

                /*db.execSQL("UPDATE `medico` SET " +
                        "`nombre` = '"+aa+"', `dir` = '"+bb+"', " +
                        "`tel` = '"+cc+"', `email` = '"+dd+"' WHERE `" +
                        "medico`.`nombre` = '"+val+"';");*/
                Toast.makeText(this, "Información actualizada "+colums, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Log.d("=/",e.toString());
            }
        }

        db.close();
        finish();
    }
}
