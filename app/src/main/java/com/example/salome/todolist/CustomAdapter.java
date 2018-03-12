package com.example.salome.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by 54088 on 04/02/2018.
 */

public class CustomAdapter extends ArrayAdapter<PassedTask> {

    public Context context;
    public ArrayList<PassedTask> passedTasks;

        public CustomAdapter(Context ctx, ArrayList<PassedTask> _passedTasks){
            super(ctx, 0, _passedTasks);
            this.context = ctx;
            this.passedTasks = _passedTasks;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PassedTask passedTask = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_text_view_layout,
                        parent, false);
            }
            TextView titles = (TextView) convertView.findViewById(R.id.rowTextView);
            titles.setText(passedTask.title);

            titles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditingActivity_.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", passedTask.id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            CheckBox checkBox = convertView.findViewById(R.id.isDoneCheckBox);
            checkBox.setChecked(passedTask.status);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    passedTask.status = !passedTask.status;
                    TaskBdd taskBdd = new TaskBdd(context);
                    taskBdd.open();
                    taskBdd.updateStatus(passedTask.id, passedTask.status);
                    taskBdd.close();
                }
            });

            return convertView;
        }
}
