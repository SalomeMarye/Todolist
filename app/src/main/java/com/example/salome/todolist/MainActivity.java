package com.example.salome.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity
public class MainActivity extends AppCompatActivity {


    @ViewById
    ListView tasksListView;

    @ViewById
    FloatingActionButton addFab;
    @Click({R.id.addFab})
    void onAddFabClick()
    {
        startActivity(new Intent(getApplicationContext(), CreateTaskActivity_.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TaskBdd taskBdd = new TaskBdd(this);
        taskBdd.open();
        ArrayList<PassedTask> for_list = taskBdd.getAllTitles();

        CustomAdapter adapter = new CustomAdapter(this, for_list);
        tasksListView.setItemsCanFocus(true);
        tasksListView.setAdapter(adapter);
    }

}
