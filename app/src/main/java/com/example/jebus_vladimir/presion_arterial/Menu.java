package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jebus on 28/11/2017.
 */

public class Menu extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void captura(View v) {
        Intent intent = new Intent(Menu.this, Captura.class);
        startActivity(intent);
    }

    public void openGraph( View v )  {
        Intent intent = new Intent(Menu.this, LineChartActivity.class);
        startActivity(intent);
    }

    public void openTable( View v )  {
        Intent intent = new Intent(Menu.this, Tabular.class);
        startActivity(intent);
    }
}
