package com.example.salome.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@EActivity
public class CreateTaskActivity extends AppCompatActivity {

    TaskBdd taskBdd = new TaskBdd(this);

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    Calendar calendar;

    @ViewById
    EditText setTitleEditText;

    @ViewById
    EditText setDescriptionEditText;

    @ViewById
    TextView showDueDateTextView;

    @ViewById
    Button setDeadlineButton;
    @Click({R.id.setDeadlineButton})
    void onSetDeadlineButtonClick()
    {
        datePickerDialog.show();
    }

    @ViewById
    Button doneButton;
    @Click({R.id.doneButton})
    void onDoneButtonClick ()
    {
        String title = setTitleEditText.getText().toString();
        String description = setDescriptionEditText.getText().toString();
        String deadline = String.valueOf(calendar.getTime().getTime());
        Task task = new Task(title, deadline);
        task.setContent(description);
        task.setCurrentStatus(false);

        taskBdd.open();
        taskBdd.insertTask(task);
        taskBdd.close();
        startActivity(new Intent(getApplicationContext(), MainActivity_.class));
    }


    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    final TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);
            showDueDateTextView.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(calendar.getTime()));
        }
    };

    final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);

            timePickerDialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(CreateTaskActivity.this, onTimeSetListener, hour, minute, true);
        datePickerDialog = new DatePickerDialog(CreateTaskActivity.this, onDateSetListener, year, month, day);

        setTitleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        setDescriptionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        showDueDateTextView.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(calendar.getTime()));
    }
}
