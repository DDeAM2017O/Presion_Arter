package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends Activity {
    private int idPer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Intent intent = getIntent();
        idPer = intent.getIntExtra("id",0);
    }

    public void captura(View v) {
        Intent intent = new Intent(MenuPrincipal.this, CapturaLecturas.class);
        intent.putExtra("id", idPer );
        startActivity(intent);
    }

    public void openGraph( View v )  {
        Intent intent = new Intent(MenuPrincipal.this, LineChartDeLecturas.class);
        startActivity(intent);
    }

    public void openTable( View v )  {
        Intent intent = new Intent(MenuPrincipal.this, TablaDeLecturas.class);
        startActivity(intent);
    }
}
