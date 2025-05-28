package com.example.task41;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class activity_edit_task extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign variables for each of the layout elements and initialise an SQLite_helper
        EditText textEditTitle = findViewById(R.id.textEditTitle);
        TextView dateEditPicker = findViewById(R.id.textviewEditDate);
        EditText textEditDescription = findViewById(R.id.textEditDescription);
        SQLite_helper task_database = new SQLite_helper(this);

        //Create the onClickListener, initialize variables, and create the dialog for the date picker popup
        dateEditPicker.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(activity_edit_task.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        //Assign the chosen date to the textView
        dateSetListener = (view, year, month, day) -> {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            dateEditPicker.setText(date);
        };

        //Pull the task parcel from the last activity and prefill the text fields with the task details
        Task task = getIntent().getParcelableExtra("task");
        textEditTitle.setText(task.getTitle());
        dateEditPicker.setText(task.getDate());
        textEditDescription.setText(task.getDescription());

        //Assign an onClickListener to the confirm button which takes the values of each text field and runs the editTask function using them
        Button button = findViewById(R.id.buttonConfirm);
        button.setOnClickListener(view -> {
            if (String.valueOf(textEditTitle.getText()).isEmpty() || String.valueOf(dateEditPicker.getText()).isEmpty() || String.valueOf(textEditDescription.getText()).isEmpty()) {
                Toast.makeText(this, "All fields must be completed", Toast.LENGTH_SHORT).show();
            } else {
                String new_task_title = String.valueOf(textEditTitle.getText());
                String new_task_date = String.valueOf(dateEditPicker.getText());
                String new_task_description = String.valueOf(textEditDescription.getText());
                int task_id = task.getId();
                task_database.editTask(task_id, new_task_title, new_task_date, new_task_description);
                finish();
            }
        });
    }
}



