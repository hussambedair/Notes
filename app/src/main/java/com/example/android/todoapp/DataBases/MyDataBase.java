package com.example.android.todoapp.DataBases;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.todoapp.DataBases.DAOs.TodoDao;
import com.example.android.todoapp.DataBases.Models.Todo;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {


   public abstract TodoDao todoDao();

   private static MyDataBase myDataBase;

    public static MyDataBase getInstance(Context context) {
        if (myDataBase == null) { //Create new object

            myDataBase= Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class, "Todo-DataBase")
                    .allowMainThreadQueries() //this will allow us do the database calls in the main thread, but it is not a good practice
                   .build();

        }

        return myDataBase;


    }






}
