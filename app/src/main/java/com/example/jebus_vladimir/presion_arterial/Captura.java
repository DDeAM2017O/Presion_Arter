package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jebus on 28/11/2017.
 */

public class Captura extends Activity {
    private EditText a, b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
    }

    public void cancelar(View v) {
        finish();
    }

    public void aceptar(View v){
        String aa= a.getText().toString().trim();
        String bb= b.getText().toString().trim();
        Calendar c = Calendar.getInstance();
        DataBase sistema = new DataBase(this, "app", null, 1);

        SQLiteDatabase db = sistema.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        ContentValues inst = new ContentValues();
        Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_LONG).show();
        inst.put("fecha",strDate);
        inst.put("alta", aa );
        inst.put("baja", bb );
        long idT = db.insert("lectura", null, inst);
        //Toast.makeText(getApplicationContext(), "Fila:  " + idT, Toast.LENGTH_LONG).show();

        if( Integer.valueOf(aa)>=160 || Integer.valueOf(bb)>=100  ) {
            String qu = "select * from medico;";
            Cursor fila = db.rawQuery(qu, null);
            if (fila.moveToFirst()) {
                String cc = fila.getString(fila.getColumnIndex("tel"));
                String dd = "Paciente critico\nPresión"+Integer.valueOf(aa)+" / "+Integer.valueOf(bb)+" mm/HG";
                //Toast.makeText(getApplicationContext(), cc, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), dd, Toast.LENGTH_LONG).show();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(cc, null, dd, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage().toString(),Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        }
        finish();
    }
}

        //org.joda.time.format.DateTimeFormatter dtf = org.joda.time.format.DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
        //org.joda.time.DateTime date = dtf.parseDateTime();
        //String time=date.toString("dd/MMM/yyyy, HH:mm"));
