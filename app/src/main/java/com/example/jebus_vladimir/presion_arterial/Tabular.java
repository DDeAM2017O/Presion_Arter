package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
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


        ListView mListView = (ListView) findViewById(R.id.listView);

        //Create the Lectura objects
        Lectura john = new Lectura("John","12-20-1998","Male", "56");
        Lectura steve = new Lectura("Steve","08-03-1987","Male", "56");
        Lectura stacy = new Lectura("Stacy","11-15-2000","Female", "56");
        Lectura ashley = new Lectura("Ashley","07-02-1999","Female", "56");
        Lectura matt = new Lectura("Matt","03-29-2001","Male", "56");
        Lectura matt2 = new Lectura("Matt2","03-29-2001","Male", "56");
        Lectura matt3 = new Lectura("Matt3","03-29-2001","Male", "56");
        Lectura matt4 = new Lectura("Matt4","03-29-2001","Male", "56");
        Lectura matt5 = new Lectura("Matt5","03-29-2001","Male", "56");
        Lectura matt6 = new Lectura("Matt6","03-29-2001","Male", "56");
        Lectura matt7 = new Lectura("Matt7","03-29-2001","Male", "56");
        Lectura matt8 = new Lectura("Matt8","03-29-2001","Male", "56");
        Lectura matt9 = new Lectura("Matt9","03-29-2001","Male", "56");
        Lectura matt10 = new Lectura("Matt10","03-29-2001","Male", "56");
        Lectura matt11 = new Lectura("Matt11","03-29-2001","Male", "56");
        Lectura matt12 = new Lectura("Matt9","03-29-2001","Male", "56");
        Lectura matt13 = new Lectura("Matt10","03-29-2001","Male", "56");
        Lectura matt14 = new Lectura("Matt","03-29-2001","Male", "56");
        Lectura matt15 = new Lectura("Matt2","03-29-2001","Male", "56");
        Lectura matt16 = new Lectura("Stacy","11-15-2000","Female", "56");
        Lectura matt17 = new Lectura("Ashley","07-02-1999","Female", "56");

        //Add the Lectura objects to an ArrayList
        ArrayList<Lectura> peopleList = new ArrayList<>();
        peopleList.add(john);
        peopleList.add(steve);
        peopleList.add(stacy);
        peopleList.add(ashley);
        peopleList.add(matt);
        peopleList.add(matt2);
        peopleList.add(matt3);
        peopleList.add(matt4);
        peopleList.add(matt5);
        peopleList.add(matt6);
        peopleList.add(matt7);
        peopleList.add(matt8);
        peopleList.add(matt9);
        peopleList.add(matt10);
        peopleList.add(matt11);
        peopleList.add(matt12);
        peopleList.add(matt13);
        peopleList.add(matt14);
        peopleList.add(matt15);
        peopleList.add(matt16);
        peopleList.add(matt17);

        PersonListAdapter adapter = new PersonListAdapter(this, R.layout.lista_misc, peopleList);
        mListView.setAdapter(adapter);
    }
}

