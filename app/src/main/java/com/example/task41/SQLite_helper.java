package com.example.task41;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLite_helper extends SQLiteOpenHelper {

    //Initialise an SQLite database, with the title "task_database" and a version of 1
    SQLiteDatabase sqlite_database = this.getWritableDatabase();
    public SQLite_helper(Context context) {
        super(context, "task_database", null, 1);
    }

    //Create the table for the SQLite database, with an ID number as the primary key, and fields for title, date and description
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table task_details(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, description TEXT)");
    }

    //Function for recreating the database if a new version is defined
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop Table if exists task_details");
        onCreate(sqLiteDatabase);
    }

    //Function for adding a new task to the database with a given title, date and description
    public void addTaskToDatabase(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", task.getTitle());
        contentValues.put("date", task.getDate());
        contentValues.put("description", task.getDescription());
        sqlite_database.insert("task_details", null, contentValues);
    }

    //Function for pulling all of the data from the database with a query
    public ArrayList<Task> getData() {
        ArrayList<Task> task_list = new ArrayList<>();
        Cursor cursor = sqlite_database.rawQuery("Select * from task_details", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String description = cursor.getString(3);
                task_list.add(new Task(id, title, date, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return task_list;
    }

    //Function for editing a task in the database by updating the details of the task of a supplied ID
    public void editTask(int id, String newTaskTitle, String newTaskDate, String newTaskDescription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", newTaskTitle);
        contentValues.put("date", newTaskDate);
        contentValues.put("description", newTaskDescription);
        sqlite_database.update("task_details", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    //Function for removing the task of a given ID from the database
    public void deleteTask(Task task) {
        int id = task.getId();
        sqlite_database.delete("task_details", "id = ?", new String[]{String.valueOf(id)});
    }
}
