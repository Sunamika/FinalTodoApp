package com.example.finaltodoapp.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.finaltodoapp.model.entity.EToDo;

import java.util.Date;

@Database(entities = {EToDo.class},version = 1,exportSchema = false)
public abstract class TodoRoomDatabase extends RoomDatabase {
    public abstract TodoDAO mTodoDAO();


    private static TodoRoomDatabase INSTANCE;
     public static TodoRoomDatabase getDatabase(Context context)

    {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class, "todo.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static class populateDbAsynchtask extends AsyncTask<EToDo, Void, Void> {
        private TodoDAO mTodoDAO;

        private populateDbAsynchtask(TodoRoomDatabase db) {
            mTodoDAO = db.mTodoDAO();
        }

        @Override
        protected Void doInBackground(EToDo... toDos) {
            Date date= new Date();
            EToDo toDo= new EToDo("Demo Title","Demo Description",date,1,false);
            mTodoDAO.insert(toDo);


            return null;
        }
    }
    private static RoomDatabase.Callback sCallback= new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new populateDbAsynchtask(INSTANCE).execute();
        }
    };

}

