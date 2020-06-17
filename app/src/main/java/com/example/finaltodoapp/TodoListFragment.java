package com.example.finaltodoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltodoapp.R;
import com.example.finaltodoapp.model.entity.EToDo;
import com.example.finaltodoapp.viewmodel.TodoViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;


public class TodoListFragment extends Fragment {

    View rootView;
    TodoViewModel mTodoViewModel;
    RecyclerView todoRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_todo_list, container, false);
        mTodoViewModel= ViewModelProviders.of(this).get(TodoViewModel.class);
        todoRecyclerView = rootView.findViewById(R.id.recyler_view);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        todoRecyclerView.setLayoutManager(layoutManager);
        updateRV();

         new ItemTouchHelper(
                 new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
             @Override
             public boolean onMove(
                     @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {




                 return false;
             }

             @Override
             public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                List<EToDo> toDoList= mTodoViewModel.getAllTodoList().getValue();
                TodoAdapter adaptor = new TodoAdapter(toDoList);
                EToDo todo=adaptor.getTodoAt(viewHolder.getAdapterPosition());
                mTodoViewModel.deleteById(todo);
             }
         }).attachToRecyclerView(todoRecyclerView);


        return rootView;
    }
    void updateRV(){
        mTodoViewModel.getAllTodoList().observe( this, new Observer<List<EToDo>>() {
            @Override
            public void onChanged(List<EToDo> todos) {
                TodoAdapter adapter = new TodoAdapter(todos);
                todoRecyclerView.setAdapter(adapter);
            }
        });
    }
    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder>{
        List<EToDo> mTodoList;
        public TodoAdapter(List<EToDo> todoList){
            mTodoList = todoList;
        }

        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            EToDo todo = mTodoList.get(position);
            LinearLayout layout= (LinearLayout)
                    ((ViewGroup)holder.mTitle.getParent());
                           switch (todo.getPriority())
                           {
                               case 1:
                                   layout.setBackgroundColor(getResources().getColor(R.color.color_high_priority));
                                   break;
                               case 2:
                                   layout.setBackgroundColor(getResources().getColor(R.color.color_medium_priority));
                                   break;
                               case 3:
                                   layout.setBackgroundColor(getResources().getColor(R.color.color_low_priority));
                                   break;

            }


            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return mTodoList.size();
        }

        public EToDo getTodoAt(int index){
            return mTodoList.get(index);
        }
    }
    private class TodoHolder extends RecyclerView.ViewHolder{
        TextView mTitle, mDate;


        public TodoHolder(LayoutInflater inflater, ViewGroup parentViewGroup) {
            super(inflater.inflate(R.layout.list_item_todo, parentViewGroup, false));
            mTitle = itemView.findViewById(R.id.list_title);
            mDate = itemView.findViewById(R.id.list_date);

            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoAdapter adaptor = new TodoAdapter(mTodoViewModel.getAllTodoList().getValue());
                    int position = getAdapterPosition();
                    EToDo eToDo = adaptor.getTodoAt(position);
                    Intent intent = new Intent(getActivity(), EditTodoActivity.class);
                    intent.putExtra("TodoId", eToDo.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(EToDo todo){
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            mTitle.setText(todo.getTitle());
            mDate.setText(dateFormatter.format(todo.getTodo_date()));
        }


    }
}
