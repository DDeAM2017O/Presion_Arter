package com.example.jebus_vladimir.presion_arterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `medico` (" +
                " `id` INTEGER NOT NULL UNIQUE PRIMARY KEY, " +
                "`nombre` VARCHAR(50) NOT NULL," +
                "`dir` VARCHAR(50) NOT NULL, " +
                "`tel` VARCHAR(50) NOT NULL, " +
                "`email` VARCHAR(50) NOT NULL," +
                "UNIQUE (`nombre`) )");

        db.execSQL("CREATE TABLE `persona`(" +
                "`id` INTEGER NOT NULL UNIQUE PRIMARY KEY, " +
                "`user` VARCHAR(50) NOT NULL, " +
                "`pass` VARCHAR(50) NOT NULL, " +
                "`nombre` VARCHAR(50) NOT NULL, " +
                "`dir` VARCHAR(70) NOT NULL, " +
                "`tel` VARCHAR(50) NOT NULL, " +
                "`altura` VARCHAR(50) NOT NULL," +
                "`peso` VARCHAR(50) NOT NULL," +
                "`edad` VARCHAR(50) NOT NULL," +
                "`to_medico_id` INTEGER," +
                " FOREIGN KEY( to_medico_id ) REFERENCES `medico` (id) );");

        db.execSQL("CREATE TABLE `lectura` (" +
                "`id` INTEGER NOT NULL UNIQUE PRIMARY KEY, " +
                "`fecha` VARCHAR(50) NOT NULL, " +
                "`lfecha` LONG NOT NULL, " +
                "`alta` FLOAT NOT NULL, " +
                "`baja` FLOAT NOT NULL ," +
                "`to_persona_id` INTEGER NOT NULL," +
                " FOREIGN KEY( to_persona_id ) REFERENCES `persona` (id) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS medico");
        //db.execSQL("DROP TABLE IF EXISTS persona");
        //db.execSQL("DROP TABLE IF EXISTS lecturas");
        //onCreate(db);
    }
}