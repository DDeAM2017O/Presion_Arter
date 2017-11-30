package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Jebus on 28/11/2017.
 */

public class Captura extends Activity {
    private EditText a, b;
    private Calendar c;
    private SimpleDateFormat sdf;
    private int res;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.captura);
        Intent intent = getIntent();
        idPer = intent.getIntExtra("id",0);
        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int val;


        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        Random r = new Random();
        String qu = "select * from lectura;";
        Cursor fila = db.rawQuery(qu, null);
        if (!fila.moveToFirst()) {
            c = Calendar.getInstance();
        }
        else{
            FileInputStream iS;
            try {
                iS = openFileInput("resta.txt");
                Reader reader = new InputStreamReader(iS);
                BufferedReader br = new BufferedReader( reader );
                res = Integer.parseInt(br.readLine());
            }catch (Exception e){
                e.printStackTrace();
            }
            c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, res);
            res--;
            val = r.nextInt(183 - 67) + 67;
            a.setText( Integer.toString(val) );
            val = r.nextInt(123 - 47) + 47;
            b.setText( Integer.toString(val) );
            FileOutputStream oS;
            try{
                oS = openFileOutput("resta.txt", Context.MODE_PRIVATE);
                oS.write(Integer.toString(res).getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void cancelar(View v) {
        finish();
    }

    public void Aceptar(View v){

        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        ContentValues inst = new ContentValues();
        String aa, bb ,ti ;

        aa = a.getText().toString().trim();
        bb = b.getText().toString().trim();
        ti = sdf.format(c.getTime());

        //Toast.makeText(getApplicationContext(), ti, Toast.LENGTH_LONG).show();
        inst.put("fecha",ti);
        inst.put("alta", aa );
        inst.put("baja", bb );
        inst.put("autor",idPer);
        long idT = db.insert("lectura", null, inst);
        //Toast.makeText(getApplicationContext(), "Fila:  " + idT, Toast.LENGTH_LONG).show();

        if( Integer.valueOf(aa)>=180 || Integer.valueOf(bb)>=120  ) {
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

    }
}

        //org.joda.time.format.DateTimeFormatter dtf = org.joda.time.format.DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
        //org.joda.time.DateTime date = dtf.parseDateTime();
        //String time=date.toString("dd/MMM/yyyy, HH:mm"));
