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

public class activity_create_task extends AppCompatActivity {

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
        EditText textCreateTitle = findViewById(R.id.textEditTitle);
        TextView dateCreatePicker = findViewById(R.id.textviewEditDate);
        EditText textCreateDescription = findViewById(R.id.textEditDescription);
        SQLite_helper task_database = new SQLite_helper(this);

        //Create the onClickListener, initialize variables, and create the dialog for the date picker popup
        dateCreatePicker.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(activity_create_task.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        //Assign the chosen date to the textView
        dateSetListener = (view, year, month, day) -> {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            dateCreatePicker.setText(date);
        };

        //Assign an onClickListener for the confirm button which pulls the value from each input and creates a new task with them
        Button button = findViewById(R.id.buttonConfirm);
        button.setOnClickListener(view -> {
            if (String.valueOf(textCreateTitle.getText()).isEmpty() || String.valueOf(dateCreatePicker.getText()).isEmpty() || String.valueOf(textCreateDescription.getText()).isEmpty()) {
                Toast.makeText(this, "All fields must be completed", Toast.LENGTH_SHORT).show();
            } else {
                Task task = new Task();
                task.setTitle(String.valueOf(textCreateTitle.getText()));
                task.setDate(String.valueOf(dateCreatePicker.getText()));
                task.setDescription(String.valueOf(textCreateDescription.getText()));
                task_database.addTaskToDatabase(task);
                finish();
            }
        });
    }
}



