package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import com.example.jebus_vladimir.notimportant.MiLectura;
import com.example.jebus_vladimir.notimportant.MiPersonListAdapter;
import java.util.ArrayList;

public class TablaDeLecturas extends Activity {
    private int idPer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_de_lecturas);
        Intent intent = getIntent();
        this.idPer = intent.getIntExtra("id",0);
    }

    @Override
    protected void onStart() {
        String  c, d, v[];
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        String qu = "SELECT fecha, alta, baja FROM lectura WHERE to_persona_id = " + idPer + " ORDER BY lfecha;";
        ArrayList<MiLectura> lis = new ArrayList<>();
        Cursor fila = db.rawQuery(qu, null);
        if (fila.moveToLast()) {
            do  {
                v = fila.getString( fila.getColumnIndex("fecha") ).split( " " );
                c = fila.getString( fila.getColumnIndex("alta") );
                d = fila.getString( fila.getColumnIndex("baja") );

                lis.add( new MiLectura( v[0], v[1], c, d ) );
                if( fila.isFirst() == true )
                    break;
                fila.moveToPrevious();
            }  while( true );
        }
        db.close();

        ListView mListView = (ListView) findViewById(R.id.listView);
        MiPersonListAdapter adapter = new MiPersonListAdapter(this, R.layout.misk_elemento_de_lista, lis);
        mListView.setAdapter(adapter);

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

