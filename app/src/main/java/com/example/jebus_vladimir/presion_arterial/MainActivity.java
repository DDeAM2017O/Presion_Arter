package com.example.jebus_vladimir.presion_arterial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import net.danlew.android.joda.JodaTimeAndroid;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback {
    final int REQUEST_MULTIPLE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main);
        final Activity parent = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ( ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(parent, new String[]{
                            Manifest.permission.SEND_SMS},REQUEST_MULTIPLE);
                }
            }
        }, 500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_MULTIPLE:
                if(grantResults.length>0) {
                    boolean smsPerm = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (!smsPerm) {
                        finish();
                    }
                }
                else {
                    finish();
                }
                break;
        }
    }

    public void startLogin(View v) {
        Intent intent = new Intent(MainActivity.this, LoginUsuario.class);
        startActivity(intent);
    }

    public void startUsuario(View v) {
        Intent intent = new Intent(MainActivity.this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void startMedico(View v) {
        Intent intent = new Intent(MainActivity.this, RegistroMedico.class);
        startActivity(intent);
    }
}
