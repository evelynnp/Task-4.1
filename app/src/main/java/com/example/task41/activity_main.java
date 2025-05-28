package com.example.task41;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class activity_main extends AppCompatActivity {

    ArrayList<Task> task_list;
    SQLite_helper task_database;
    recycler_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initialise an SQLite_helper to read from the database, along with an ArrayList for the list of tasks
        task_database = new SQLite_helper(this);
        task_list = new ArrayList<>();

        //Assign a variable for the recycler, then set the recycler adapter, display the list of tasks and add an onClickListener
        RecyclerView recyclerView = findViewById(R.id.recyclerTaskList);
        adapter = new recycler_adapter(this, task_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
        setOnClickListener();

        //Assign an onClickListener for the new task button to start the create_task activity
        Button button = findViewById(R.id.buttonNewTask);
        button.setOnClickListener(view -> {
            startActivity(new Intent(activity_main.this, activity_create_task.class));
        });
    }

    //Override the onResume function of the activity to rerun the displayData function so that the data in the list updates
    @Override
    protected void onResume() {
        super.onResume();
        displayData();
    }

    //Clear the existing task_list, run the function to add all tasks to the task_list, then notify the adapter that the data set has changed
    public void displayData() {
        task_list.clear();
        task_list.addAll(task_database.getData());
        //Sort the data in the list by date, using the supplied date format
        task_list.sort(new Comparator<Task>() {
            SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(Task task_1, Task task_2) {
                try {
                    Date date_1 = date_format.parse(task_1.getDate());
                    Date date_2 = date_format.parse(task_2.getDate());
                    return date_1.compareTo(date_2);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return 0;
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    //Set an onClickListener to each of the tasks in the recycler, which starts the view_task activity and passes through the task in a parcel
    private void setOnClickListener() {
        adapter.setOnItemClickListener(position -> {
            Intent view_task_intent = new Intent(this, activity_view_task.class);
            view_task_intent.putExtra("task", task_list.get(position));
            startActivity(view_task_intent);
        });
    }
}