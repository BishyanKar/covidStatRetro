package com.example.architectuecomp;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Case.class},version = 2,exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance;
    public abstract CaseDao caseDao();

    public static synchronized Database getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),Database.class,"case_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
