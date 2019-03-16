package com.example.android.todoapp.DataBases.DAOs;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.RequiresPermission;

import com.example.android.todoapp.DataBases.Models.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    public void addTodo (Todo item) ;

    @Delete
    public void removeTodo (Todo item);

    @Update
    public void updateTodo (Todo item);

    @Query("select * from Todo;")
    public List<Todo> getAllTodo();

    @Query("select * from Todo where id =:id ;")
    public Todo searchById (int id);

    //search by title
    @Query("select * from Todo where title like :subText ;")
    public List<Todo> searchByText (String subText);





}
