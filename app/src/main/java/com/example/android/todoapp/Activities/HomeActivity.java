package com.example.android.todoapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.todoapp.Adapters.TodoRecyclerAdapter;
import com.example.android.todoapp.Base.BaseActivity;
import com.example.android.todoapp.DataBases.Models.Todo;
import com.example.android.todoapp.DataBases.MyDataBase;
import com.example.android.todoapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    RecyclerView todoRecyclerView;
    TodoRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Todo> todos;

    TextView emptyView;


    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        todoRecyclerView = findViewById(R.id.todo_recycler_view);

        layoutManager = new LinearLayoutManager(HomeActivity.this);

        adapter = new TodoRecyclerAdapter(null);

        todoRecyclerView.setAdapter(adapter);
        todoRecyclerView.setLayoutManager(layoutManager);


        emptyView = findViewById(R.id.empty_state_text_view);


        adapter.setOnTodoClickListener(new TodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Todo todo) {

                AddTodoActivity.todoItem = todo;

                Intent intent = new Intent(HomeActivity.this, AddTodoActivity.class);
                startActivity(intent);


            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                List<Todo> items = MyDataBase.getInstance(HomeActivity.this)
                        .todoDao()
                        .getAllTodo();

                adapter.changeData(items);

                //hide the refresh indicator
                mSwipeRefreshLayout.setRefreshing(false);


            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });


        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getAdapterPosition();
                        final Todo myTodo = adapter.getTodoAtPosition(position);

                        //Using Snackbar:

                        /*MyDataBase.getInstance(HomeActivity.this)
                                .todoDao()
                                .removeTodo(myTodo);

                        List<Todo> items = MyDataBase.getInstance(HomeActivity.this)
                                .todoDao()
                                .getAllTodo();

                        adapter.changeData(items);




                        //for the first argument of the make() method in the Snackbar
                        //in content_home.xml you can either use the id of the SwipeRefreshLayout
                        //or you can give the ConstraintLayout an id and use it
                        //either way, you should use a view that fills the screen
                        Snackbar.make(findViewById(R.id.swipe_refresh),R.string.item_deleted, 4000)
                                .setAction(R.string.undo, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Re-Add the item
                                        MyDataBase.getInstance(HomeActivity.this)
                                                .todoDao()
                                                .addTodo(myTodo);


                                        List<Todo> items = MyDataBase.getInstance(HomeActivity.this)
                                                .todoDao()
                                                .getAllTodo();

                                        adapter.changeData(items);

                                    }
                                }).show();*/


                        //Using confirmation message:

                        showConfirmationMessage(R.string.warning, R.string.are_you_sure,
                                R.string.yes, new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        // Delete the item
                                        MyDataBase.getInstance(HomeActivity.this)
                                                .todoDao()
                                                .removeTodo(myTodo);

                                        List<Todo> items = MyDataBase.getInstance(HomeActivity.this)
                                                .todoDao()
                                                .getAllTodo();

                                        if (items.isEmpty()) {
                                            todoRecyclerView.setVisibility(View.GONE);
                                            emptyView.setVisibility(View.VISIBLE);
                                        } else {
                                            todoRecyclerView.setVisibility(View.VISIBLE);
                                            emptyView.setVisibility(View.GONE);
                                        }

                                        adapter.changeData(items);


                                    }
                                }, R.string.no, new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //dismess
                                        dialog.dismiss();
                                        List<Todo> items = MyDataBase.getInstance(HomeActivity.this)
                                                .todoDao()
                                                .getAllTodo();

                                        adapter.changeData(items);

                                    }
                                }).setCancelable(false);


                    }
                });
        helper.attachToRecyclerView(todoRecyclerView);


    }


    @Override
    protected void onStart() {
        super.onStart();

        //get all the data
        List<Todo> items = MyDataBase.getInstance(this)
                .todoDao()
                .getAllTodo();

        if (items.isEmpty()) {
            todoRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            todoRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }


        //change the data in the recycler view
        adapter.changeData(items);


        //We don't want to create a new adapter each time we start the activity
        /*adapter = new TodoRecyclerAdapter(items);
        todoRecyclerView.setAdapter(adapter);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
