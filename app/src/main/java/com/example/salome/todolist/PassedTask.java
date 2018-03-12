package com.example.salome.todolist;

/**
 * Created by 54088 on 04/02/2018.
 */

public class PassedTask {
        public String title;
        public int  id;
        public boolean status;

        PassedTask(String _title, int _id, boolean _status) {
            this.title = _title;
            this.id = _id;
            this.status = _status;
        }
}
