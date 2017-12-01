package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.jebus_vladimir.notimportant.Lectura;
import com.example.jebus_vladimir.notimportant.PersonListAdapter;

import java.util.ArrayList;


public class Tabular extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabular);





    }

    @Override
    protected void onStart() {
        String  c, d, v[];
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        String qu = "select * from lectura;";
        ArrayList<Lectura> lis = new ArrayList<>();
        Cursor fila = db.rawQuery(qu, null);
        if (fila.moveToLast()) {
            do  {
                v = fila.getString( fila.getColumnIndex("fecha") ).split( " " );
                c = fila.getString( fila.getColumnIndex("alta") );
                d = fila.getString( fila.getColumnIndex("baja") );

                lis.add( new Lectura( v[0], v[1], c, d ) );
                if( fila.isFirst() == true )
                    break;
                fila.moveToPrevious();
            }  while( true );
        }
        db.close();

        ListView mListView = (ListView) findViewById(R.id.listView);
        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.lista_misc, lis);
        mListView.setAdapter(adapter);

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

