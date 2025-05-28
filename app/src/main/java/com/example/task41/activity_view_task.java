package com.example.task41;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_view_task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign variables for each of the layout elements and initialise an SQLite_helper
        TextView title_text = findViewById(R.id.textviewTitleText);
        TextView date_text = findViewById(R.id.textviewDateText);
        TextView description_text = findViewById(R.id.textviewDescriptionText);
        SQLite_helper task_database = new SQLite_helper(this);

        //Pull the task parcel from the last activity, then assign each field based on the task details
        Task task = getIntent().getParcelableExtra("task");
        title_text.setText(task.getTitle());
        date_text.setText(task.getDate());
        description_text.setText(task.getDescription());

        //Assign an onClickListener for the edit button which starts the edit_task activity and passes the task parcel again
        Button buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(view -> {
            Intent editTaskIntent = new Intent(this, activity_edit_task.class);
            editTaskIntent.putExtra("task", task);
            startActivity(editTaskIntent);
            finish();
        });

        //Assign an onClickListener for the delete button which runs the deleteTask function in the SQLite_helper
        Button buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(view -> {
            task_database.deleteTask(task);
            finish();
        });

    }

}