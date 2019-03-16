package com.example.android.todoapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.todoapp.DataBases.Models.Todo;
import com.example.android.todoapp.R;

import java.util.List;

public class TodoRecyclerAdapter
        extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {

    List<Todo> mItems;

    OnItemClickListener onTodoClickListener;

    public void setOnTodoClickListener(OnItemClickListener onTodoClickListener) {
        this.onTodoClickListener = onTodoClickListener;
    }

    public TodoRecyclerAdapter (List <Todo> items) {
        mItems=items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Todo todo = mItems.get(position);
        viewHolder.title.setText(todo.getTitle());
        viewHolder.date.setText(todo.getDateTime());

        if (onTodoClickListener != null) {
            // use viewHolder.itemView if you want the whole item to respond to the click
            // if you want a specific view within the item to respond,
            // assign the view reference after the dot.
            // for example ---> viewHolder.title.setOnClickListener ()
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTodoClickListener.onItemClick(position,todo);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    public void changeData (List <Todo> items) {
        mItems = items;
        notifyDataSetChanged();

    }



    public Todo getTodoAtPosition (int position) {
        return mItems.get(position);
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date  = itemView.findViewById(R.id.date);


        }
    }

    public interface OnItemClickListener {
        public void onItemClick (int pos, Todo todo);
    }


}
