package com.example.finaltodoapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finaltodoapp.model.entity.EToDo;

import java.util.List;

@Dao
public interface TodoDAO {
    @Insert
    void insert(EToDo toDo);

    @Delete
    void deleteById(EToDo toDo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(EToDo... toDos);

    @Query("SELECT * FROM todo_table ORDER BY todo_date,priority desc")
    LiveData<List<EToDo>> getAllTodos();

    @Query("SELECT * FROM todo_table WHERE id=:id")
    EToDo getTodoById(int id);




}
