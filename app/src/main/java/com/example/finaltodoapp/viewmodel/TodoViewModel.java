package com.example.finaltodoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finaltodoapp.data.TodoRepository;
import com.example.finaltodoapp.model.entity.EToDo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mTodoRepository;
    private LiveData<List<EToDo>> mAllTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);

        mTodoRepository = new TodoRepository(application);
        mAllTodos=mTodoRepository.getAllTodoList();
    }
  public void insert(EToDo toDo)
  {

      mTodoRepository.insert(toDo);
  }

  public void update(EToDo toDo)
  {
      mTodoRepository.update(toDo);
  }
    public LiveData<List<EToDo>> getAllTodoList()
    {
        return mAllTodos;
    }

    public EToDo getTodoById(int id)
    {
        return mTodoRepository.getTodoById(id);
    }


    public void deleteById(EToDo toDo)
    {
        mTodoRepository.deleteById(toDo);
    }

    public void deleteAll()
    {
        mTodoRepository.deleteAll();
    }

}
