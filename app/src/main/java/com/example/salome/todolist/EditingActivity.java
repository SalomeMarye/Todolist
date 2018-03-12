package com.example.salome.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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
import java.util.Date;

@EActivity
public class EditingActivity extends AppCompatActivity {

    TaskBdd taskBdd = new TaskBdd(this);

    private Task task;

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    Calendar calendar;

    @ViewById
    EditText setNewTitleEditText;

    @ViewById
    EditText setNewDescriptionEditText;

    @ViewById
    TextView showNewDueDateTextView;

    @ViewById
    Button setNewDeadlineButton;
    @Click({R.id.setNewDeadlineButton})
    void onSetNewDeadlineButtonClick()
    {
        datePickerDialog.show();
    }

    @ViewById
    Button updateButton;
    @Click({R.id.updateButton})
    void onUpdateButtonClick ()
    {
        String deadline = String.valueOf(calendar.getTime().getTime());
        task.setTitle(setNewTitleEditText.getText().toString());
        task.setContent(setNewDescriptionEditText.getText().toString());
        task.setDeadline(deadline);

        taskBdd.open();
        taskBdd.updateTask(task.getId(), task);
        taskBdd.close();
        startActivity(new Intent(getApplicationContext(), MainActivity_.class));
    }

    @ViewById
    Button deleteButton;
    @Click({R.id.deleteButton})
    void onDeleteButtonClick()
    {
        taskBdd.open();
        taskBdd.removeTaskWithId(task.getId());
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
            showNewDueDateTextView.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(calendar.getTime()));
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
        setContentView(R.layout.activity_editing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle bundle = getIntent().getExtras();
        taskBdd.open();
        task = taskBdd.getTaskWithId(bundle.getInt("Id"));

        setNewTitleEditText.setText(task.getTitle());
        setNewDescriptionEditText.setText(task.getContent());
        Date due_date = task.getDeadline();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String dateToShow = simpleDateFormat.format(due_date);
        showNewDueDateTextView.setText("    Due date is :\n" + dateToShow);

        calendar = Calendar.getInstance();
        calendar.setTime(task.getDeadline());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(EditingActivity.this, onTimeSetListener, hour, minute, true);
        datePickerDialog = new DatePickerDialog(EditingActivity.this, onDateSetListener, year, month, day);

        setNewTitleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        setNewDescriptionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        showNewDueDateTextView.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(calendar.getTime()));
    }
}
