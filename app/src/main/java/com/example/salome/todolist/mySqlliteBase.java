/**
 * Created by 54088 on 30/01/2018.
 */
//package com.tutomobile.android.sqlite;

package com.example.salome.todolist;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class mySqlliteBase extends SQLiteOpenHelper
{
    private static final String TASK_TABLE = "task_table";
    private static final String COL_ID = "Id";
    private static final String COL_TITLE = "Title";
    private static final String COL_CONTENT = "Content";
    private static final String COL_DEADLINE = "Deadline";
    private static final String COL_STATUS = "Status";

    private static final String CREATE_BDD = "CREATE TABLE " + TASK_TABLE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITLE + " TEXT NOT NULL, "
            + COL_CONTENT + " TEXT NOT NULL, " + COL_DEADLINE + " TEXT NOT NULL, "
            + COL_STATUS + " INTEGER NOT NULL);";

    public mySqlliteBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL("DROP TABLE " + TASK_TABLE + ";");
        onCreate(database);
    }

    public void dropDB(SQLiteDatabase database)
    {
        database.execSQL("DROP TABLE " + TASK_TABLE + ";");
        onCreate(database);
    }
}
