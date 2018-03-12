/**
 * Created by 54088 on 30/01/2018.
 */
package com.example.salome.todolist;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private int id;
    private String title;
    private String content;
    private boolean currentStatus;
    private Date deadline;

    public Task(String _title, String _deadline)
    {
        this.title = _title;
        this.deadline = new Date();
        deadline.setTime(Long.valueOf(_deadline));
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int newId)
    {
        this.id = newId;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getContent()
    {
        return this.content;
    }

    public boolean getStatus()
    {
        return this.currentStatus;
    }

    public Date getDeadline()
    {
        return this.deadline;
    }

    public void setTitle(String newTitle)
    {
        this.title = newTitle;
    }

    public void setContent(String newContent)
    {
        this.content = newContent;
    }

    public void setCurrentStatus(boolean newStatus)
    {
        this.currentStatus = newStatus;
    }

    public void setDeadline(String newDeadline)
    {
        deadline.setTime(Long.valueOf(newDeadline));
    }

}
