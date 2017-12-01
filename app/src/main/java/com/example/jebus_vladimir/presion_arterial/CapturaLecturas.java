package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;

public class CapturaLecturas extends Activity {
    private EditText a, b;
    private Calendar c = null;
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
    }

    public void cancelar(View v) {
        finish();
    }

    public void aceptar(View v){
        int i, s, d;
        Random r = new Random();
        generarPresion( Integer.parseInt( a.getText().toString().trim() ),
                Integer.parseInt( b.getText().toString().trim() ) );

        for( i = 0; i < 6; i++ )  {
            s = r.nextInt(183 - 67) + 67;
            d = r.nextInt(123 - 47) + 47;
            generarPresion( s, d );
        }

        finish();
    }

    private void generarPresion( int aa, int bb )  {
        long time, idT;
        String query, ti, mensaje;
        Cursor fila;
        ContentValues inst = new ContentValues();
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();

        if( c == null )  {
            query = "SELECT * FROM lectura WHERE to_persona_id = " + idPer + " ORDER BY lfecha;";
            fila = db.rawQuery( query , null);
            c = Calendar.getInstance();
            if ( fila.moveToFirst() )  {
                time = fila.getLong( fila.getColumnIndex("lfecha") );
                c.setTimeInMillis( time );
                c.add(Calendar.DAY_OF_MONTH, -1);
            }
        }

        ti = sdf.format( c.getTime() );
        time = c.getTimeInMillis();
        //Toast.makeText(getApplicationContext(), ti, Toast.LENGTH_LONG).show();
        inst.put( "fecha", ti );
        inst.put( "lfecha", time );
        inst.put( "alta", aa );
        inst.put( "baja", bb );
        inst.put( "to_persona_id", idPer );
        idT = db.insert("lectura", null, inst);
        Toast.makeText(getApplicationContext(), "Fila:  " + idT, Toast.LENGTH_SHORT).show();

        if( aa >=180 || bb >=120 || aa <= 70 || bb <= 50 ) {
            query = "SELECT tel FROM medico;";
            fila = db.rawQuery( query, null );
            if (fila.moveToFirst()) {
                ti = fila.getString( fila.getColumnIndex("tel" ) );
                query = "SELECT nombre, dir FROM persona WHERE id = " + idPer + ";";
                fila = db.rawQuery( query, null );
                fila.moveToFirst();
                query = fila.getString( fila.getColumnIndex("nombre" ) );
                mensaje = "Patient " + query + " has critical blood preasure\nof: " + aa + "/" + bb +
                        " mm/HG. \nHe lives at: +" + fila.getString( fila.getColumnIndex("dir" ) );
                //Toast.makeText(getApplicationContext(), cc, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), dd, Toast.LENGTH_LONG).show();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage( ti, null, mensaje, null, null );
                    Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        }

        Toast.makeText(this, sdf.format( c.getTime() ) , Toast.LENGTH_SHORT).show();
        c.add(Calendar.DAY_OF_MONTH, -1);
        db.close();
    }
}