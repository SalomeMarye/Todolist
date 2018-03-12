/**
 * Created by 54088 on 30/01/2018.
 */
package com.example.salome.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import org.androidannotations.api.BackgroundExecutor;

import java.util.ArrayList;
import java.util.Date;

public class TaskBdd {

    private static final int BDD_VERSION = 1;
    private static String BDD_NAME = "tasks.db";

    private static final String TASK_TABLE = "task_table";

    private static final String COL_ID = "Id";
    private static final int NUM_ID_COL = 0;

    private static final String COL_TITLE = "Title";
    private static final int NUM_TITLE_COL = 1;

    private static final String COL_CONTENT = "Content";
    private static final int NUM_CONTENT_COL = 2;

    private static final String COL_DEADLINE = "Deadline";
    private static final int NUM_DEADLINE_COL = 3;

    private static final String COL_STATUS = "Status";
    private static final int NUM_STATUS_COL = 4;

    private SQLiteDatabase database;
    private mySqlliteBase mySqlliteBase;

    public TaskBdd(Context context)
    {
        mySqlliteBase = new mySqlliteBase(context, BDD_NAME, null, BDD_VERSION);
    }

    public void open()
    {
        database = mySqlliteBase.getWritableDatabase();
        //mySqlliteBase.dropDB(database);
    }

    public void close()
    {
        database.close();
    }

    public SQLiteDatabase getDatabase()
    {
        return database;
    }

    public long insertTask(Task newTask)
    {
        ContentValues newTaskValues = new ContentValues();

        newTaskValues.put(COL_TITLE, newTask.getTitle());
        newTaskValues.put(COL_CONTENT, newTask.getContent());
        newTaskValues.put(COL_DEADLINE, String.valueOf(newTask.getDeadline().getTime()));
        newTaskValues.put(COL_STATUS, newTask.getStatus() ? 1 : 0);

        return database.insert(TASK_TABLE, null, newTaskValues);
    }

    public long updateTask(int id, Task updateTask)
    {
        ContentValues updateValues = new ContentValues();

        updateValues.put(COL_TITLE, updateTask.getTitle());
        updateValues.put(COL_CONTENT, updateTask.getContent());
        updateValues.put(COL_DEADLINE, String.valueOf(updateTask.getDeadline().getTime()));
        updateValues.put(COL_STATUS, updateTask.getStatus() ? 1 : 0);

        return database.update(TASK_TABLE, updateValues, COL_ID + " = " + id , null);
    }

    public long updateStatus(int id, boolean newStatus)
    {
        Task task = getTaskWithId(id);
        task.setCurrentStatus(newStatus);
        return updateTask(id, task);
    }

    public int removeTaskWithId(int id)
    {
        return database.delete(TASK_TABLE, COL_ID + " = " + id, null);
    }

    private Task cursorToTask(Cursor cursor)
    {
        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        Task task = new Task(cursor.getString(NUM_TITLE_COL), cursor.getString(NUM_DEADLINE_COL));
        task.setContent(cursor.getString(NUM_CONTENT_COL));
        task.setId(cursor.getInt(NUM_ID_COL));
        //task.setCurrentStatus(cursor.getInt(NUM_STATUS_COL));

        cursor.close();
        return task;
    }

    public Task getTaskWithTitle(String title)
    {
        Cursor cursor = database.query(TASK_TABLE, new String[] {COL_ID, COL_TITLE, COL_CONTENT, COL_DEADLINE, COL_STATUS}
        , COL_TITLE + "LIKE\"" + title + "\"", null, null, null, null);
        return cursorToTask(cursor);
    }

    public Task getTaskWithId(int _id)
    {
        Cursor cursor = database.query(TASK_TABLE, new String[] {COL_ID, COL_TITLE, COL_CONTENT, COL_DEADLINE, COL_STATUS},
                COL_ID + " = " + _id + ' ', null, null , null, null);
        return cursorToTask(cursor);
    }

    public ArrayList<PassedTask> getAllTitles()
    {
        ArrayList<PassedTask> to_return = new ArrayList<PassedTask>();
        Cursor cursor = database.query(TASK_TABLE, new String[] {COL_ID, COL_TITLE, COL_STATUS},
                null, null, null, null, COL_DEADLINE);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            PassedTask passedTask = new PassedTask(cursor.getString(1), cursor.getInt(0), cursor.getInt(2) == 1);
            to_return.add(passedTask);
        }
        return to_return;
    }
}
