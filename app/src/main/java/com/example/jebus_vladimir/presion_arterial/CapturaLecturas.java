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
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CapturaLecturas extends Activity {
    private EditText a, b;
    private Calendar c;
    private SimpleDateFormat sdf;
    private int idPer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_lecturas);
        Intent intent = getIntent();
        idPer = intent.getIntExtra("id",0);

        a = (EditText) findViewById(R.id.user);
        b = (EditText) findViewById(R.id.pass);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int val;
        String time;
        Random r = new Random();
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();

        String qu = "select * from lectura where autor="+idPer+" order by fecha;";
        Cursor fila = db.rawQuery(qu, null);
        if (!fila.moveToFirst()) {
            c = Calendar.getInstance();
        }
        else{
            time = fila.getString( fila.getColumnIndex("fecha") );
            Toast.makeText(this, time , Toast.LENGTH_SHORT).show();
            Date date;
            c = Calendar.getInstance();
            try {
                date = sdf.parse(time);
                c.setTime( date );
                c.add(Calendar.DAY_OF_MONTH, -1);
            } catch (ParseException e) {
                Log.d("Error",e.toString());
                Toast.makeText(this, "PTM" , Toast.LENGTH_SHORT).show();
            }

            val = r.nextInt(183 - 67) + 67;
            a.setText( Integer.toString(val) );
            val = r.nextInt(123 - 47) + 47;
            b.setText( Integer.toString(val) );
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
        String aa, bb ,ti ;
        long mm;
        aa = a.getText().toString().trim();
        bb = b.getText().toString().trim();
        ti = sdf.format(c.getTime());
        mm = c.getTimeInMillis();
        //Toast.makeText(getApplicationContext(), ti, Toast.LENGTH_LONG).show();
        inst.put("fecha",ti);
        inst.put("lfecha", c.getTimeInMillis());
        inst.put("alta", aa );
        inst.put("baja", bb );
        inst.put("to_persona_id",idPer);
        long idT = db.insert("lectura", null, inst);
        Toast.makeText(getApplicationContext(), "Fila:  " + idT, Toast.LENGTH_LONG).show();

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
        finish();

    }
}

        //org.joda.time.format.DateTimeFormatter dtf = org.joda.time.format.DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
        //org.joda.time.DateTime date = dtf.parseDateTime();
        //String time=date.toString("dd/MMM/yyyy, HH:mm"));
