package com.example.android.todoapp.Activities;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.todoapp.Base.BaseActivity;
import com.example.android.todoapp.DataBases.Models.Todo;
import com.example.android.todoapp.DataBases.MyDataBase;
import com.example.android.todoapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTodoActivity extends BaseActivity {

    EditText title;
    EditText date;
    EditText content;
    Button addButton;

    public static Todo todoItem;

    final Calendar myCalendar = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        }
    } ;


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        content = findViewById(R.id.content);
        addButton = findViewById(R.id.add_button);

        if (todoItem != null) {
            addButton.setText(R.string.update);
            title.setText(todoItem.getTitle());
            date.setText(todoItem.getDateTime());
            content.setText(todoItem.getContent());


        }



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTodoActivity.this,
                         datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (todoItem == null) { //Add

                    String sTitle = title.getText().toString();
                    String sDate = date.getText().toString();
                    String sContent = content.getText().toString();

                    //input validation
                    String empty = "";
                    if (sTitle.equals(empty) || sDate.equals(empty) || sContent.equals(empty)) {
                        Toast.makeText(AddTodoActivity.this,
                                "Complete Task Information!", Toast.LENGTH_SHORT)
                                .show();

                        return;
                    }

                    //create new todoItem
                    Todo todoItem = new Todo(sTitle, sContent, sDate);

                    //add the new todoItem to the database
                    MyDataBase.getInstance(AddTodoActivity.this)
                            .todoDao()
                            .addTodo(todoItem);

                    showConfirmationMessage(R.string.success,
                            R.string.note_added_successfully,
                            R.string.ok,
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).setCancelable(false);


                } else { //update

                    String sTitle = title.getText().toString();
                    String sDate = date.getText().toString();
                    String sContent = content.getText().toString();

                    //input validation
                    String empty = "";
                    if (sTitle.equals(empty) || sDate.equals(empty) || sContent.equals(empty)) {
                        Toast.makeText(AddTodoActivity.this,
                                "Complete Task Information!", Toast.LENGTH_SHORT)
                                .show();

                        return;
                    }

                    //update the information
                    todoItem.setTitle(sTitle);
                    todoItem.setDateTime(sDate);
                    todoItem.setContent(sContent);

                    //update the item in the database
                    MyDataBase.getInstance(AddTodoActivity.this)
                            .todoDao()
                            .updateTodo(todoItem);

                    showConfirmationMessage(R.string.success,
                            R.string.note_updated_successfully,
                            R.string.ok,
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).setCancelable(false);





                }


            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        todoItem = null;
    }
}
