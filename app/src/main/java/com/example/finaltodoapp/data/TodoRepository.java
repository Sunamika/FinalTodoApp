package com.example.finaltodoapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.finaltodoapp.model.entity.EToDo;

import java.util.List;

public class TodoRepository {
    private TodoDAO mTodoDAO;
    private LiveData<List<EToDo>> mAllTodoList;

    public TodoRepository(Application application) {
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        mTodoDAO = database.mTodoDAO();
        mAllTodoList = mTodoDAO.getAllTodos();
    }

    public LiveData<List<EToDo>> getAllTodoList() {
        return mAllTodoList;
    }

public EToDo getTodoById(int id)
{
return mTodoDAO.getTodoById(id);
}

    public void insert(EToDo toDo) {
        new insertTodoAsynchtask(mTodoDAO).execute(toDo);
    }
    public void update(EToDo toDo)
    {
        new updateTodoAsynchtask(mTodoDAO).execute(toDo);

    }

    public void deleteAll()
    {
        new deleteAllTodoAsynchtask(mTodoDAO).execute();
    }
    public void deleteById(EToDo toDo)
    {
        new deleteByIdTodoAsynchtask(mTodoDAO).execute(toDo);
    }

    private static class insertTodoAsynchtask extends AsyncTask<EToDo, Void, Void> {
        private TodoDAO mTodoDAO;

        private insertTodoAsynchtask(TodoDAO todoDAO) {
            mTodoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(EToDo... toDos) {
            mTodoDAO.insert(toDos[0]);


            return null;
        }
    }

    private static class deleteAllTodoAsynchtask extends AsyncTask<EToDo, Void, Void> {
        private TodoDAO mTodoDAO;

        private deleteAllTodoAsynchtask(TodoDAO todoDAO) {
            mTodoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(EToDo... toDos) {
            mTodoDAO.deleteAll();


            return null;
        }
    }

    private static class deleteByIdTodoAsynchtask extends AsyncTask<EToDo, Void, Void> {
        private TodoDAO mTodoDAO;

        private deleteByIdTodoAsynchtask(TodoDAO todoDAO) {
            mTodoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(EToDo... toDos) {
            mTodoDAO.deleteById(toDos[0]);


            return null;
        }
    }
    private static class updateTodoAsynchtask extends AsyncTask<EToDo, Void, Void> {
        private TodoDAO mTodoDAO;

        private updateTodoAsynchtask(TodoDAO todoDAO) {
            mTodoDAO = todoDAO;
        }

        @Override
        protected Void doInBackground(EToDo... toDos) {
            mTodoDAO.update(toDos[0]);


            return null;
        }
    }
}
