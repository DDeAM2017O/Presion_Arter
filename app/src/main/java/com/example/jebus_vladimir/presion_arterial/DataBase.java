package com.example.jebus_vladimir.presion_arterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jebus on 28/11/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `medico` ( `id` INTEGER AUTO_INCREMENT, " +
                "`nombre` VARCHAR(50) NOT NULL," +
                "`dir` VARCHAR(50) NOT NULL, " +
                "`tel` VARCHAR(50) NOT NULL, " +
                "`email` VARCHAR(50) NOT NULL," +
                "PRIMARY KEY(`id`) " +
                "UNIQUE (`nombre`) )");

        db.execSQL("CREATE TABLE `persona`(" +
                "`id` INTEGER AUTO_INCREMENT, " +
                "`user` VARCHAR(50) NOT NULL, " +
                "`pass` VARCHAR(50) NOT NULL, " +
                "`nombre` VARCHAR(50) NOT NULL, " +
                "`dir` VARCHAR(70) NOT NULL, " +
                "`tel` VARCHAR(50) NOT NULL, " +
                "`altura` VARCHAR(50) NOT NULL," +
                "`peso` VARCHAR(50) NOT NULL," +
                "`edad` VARCHAR(50) NOT NULL," +
                "PRIMARY KEY(`id`) " +
                "UNIQUE (`user`) );");

        db.execSQL("CREATE TABLE `lectura` (" +
                "`id` INTEGER AUTO_INCREMENT, " +
                "`fecha` VARCHAR(50) NOT NULL, " +
                "`alta` VARCHAR(50) NOT NULL, " +
                "`baja` VARCHAR(50) NOT NULL, " +
                "(`id`) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS Medico");
        //db.execSQL("DROP TABLE IF EXISTS persona");
        //db.execSQL("DROP TABLE IF EXISTS lecturas");
        //onCreate(db);
    }
}