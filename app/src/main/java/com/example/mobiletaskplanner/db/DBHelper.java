package com.example.mobiletaskplanner.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mobiletaskplanner.models.AccountContract;
import com.example.mobiletaskplanner.models.DateTasks;
import com.example.mobiletaskplanner.models.Task;
import com.example.mobiletaskplanner.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private SQLiteDatabase mDatabase;

    public DBHelper(Context context){
        SQLLiteConnectionHelper connectionHelper = new SQLLiteConnectionHelper(context);
        mDatabase = connectionHelper.getWritableDatabase();
        connectionHelper.onCreate(connectionHelper.getWritableDatabase());
    }

    private Cursor getAllItems(){
        return mDatabase.query(AccountContract.AccountEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                AccountContract.AccountEntry.COLUMN_NAME_EMAIL);
    }

    public boolean validatePassword(String passwordText) {
        Cursor c = getAllItems();
        if (c.moveToFirst()) {
            @SuppressLint("Range") String password = c.getString(
                    c.getColumnIndex(AccountContract.AccountEntry.COLUMN_NAME_PASSWORD));
            c.close();
            if(!password.equals(passwordText)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public void updatePassword(String passwordText, String email) {

        // Define the values to update
        ContentValues values = new ContentValues();
        values.put(AccountContract.AccountEntry.COLUMN_NAME_PASSWORD, passwordText);

        // Define the criteria to select the rows to update
        String selection = AccountContract.AccountEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};

        // Perform the update operation
        mDatabase.update(AccountContract.AccountEntry.TABLE_NAME,
                values, selection, selectionArgs);
    }

    public List<Task> loadTasks(Long timestamp) {
        String[] columns = {
                AccountContract.TaskEntry.COLUMN_NAME_TASK_ID,
                AccountContract.TaskEntry.COLUMN_NAME_TASK_TITLE,
                AccountContract.TaskEntry.COLUMN_NAME_START_TIME,
                AccountContract.TaskEntry.COLUMN_NAME_END_TIME,
                AccountContract.TaskEntry.COLUMN_NAME_DESCRIPTION,
                AccountContract.TaskEntry.COLUMN_NAME_PRIORITY
        };

        String selection = AccountContract.TaskEntry.COLUMN_NAME_DATE_TIMESTAMP + " = ?";
        String[] selectionArgs = {timestamp.toString()};

        Cursor cursor = mDatabase.query(
                AccountContract.TaskEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        List<Task> tasks = new ArrayList<>();
        while(cursor.moveToNext()) {
            int taskId = cursor.getInt(cursor.getColumnIndexOrThrow(AccountContract.TaskEntry.COLUMN_NAME_TASK_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(AccountContract.TaskEntry.COLUMN_NAME_TASK_TITLE));
            int startTime = cursor.getInt(cursor.getColumnIndexOrThrow(AccountContract.TaskEntry.COLUMN_NAME_START_TIME));
            int endTime = cursor.getInt(cursor.getColumnIndexOrThrow(AccountContract.TaskEntry.COLUMN_NAME_END_TIME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(AccountContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow(AccountContract.TaskEntry.COLUMN_NAME_PRIORITY));
            Task task = new Task(taskId, title, startTime, endTime, description, Util.integerPriorityMap.get(priority));
            tasks.add(task);
        }
        if(tasks.size() > 0)
            Log.e("Task", "Tasks for " + timestamp);
        for(Task task : tasks) {
            Log.e("Task", task.getTitle() + " " + task.getId());
        }
        cursor.close();
        return tasks;
    }

    public void saveTasks(DateTasks dateTasks) {
        deleteTasks(dateTasks.getUnixTime());
        for(Task task : dateTasks.getTasks()) {
            ContentValues cv = new ContentValues();
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_TASK_ID, dateTasks.getId());
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_TASK_TITLE, task.getTitle());
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_START_TIME, task.getStartTimeMinutes());
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_END_TIME, task.getEndTimeMinutes());
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_PRIORITY, Util.priorityIntegerMap.get(task.getPriority()));
            cv.put(AccountContract.TaskEntry.COLUMN_NAME_DATE_TIMESTAMP, dateTasks.getUnixTime());
            mDatabase.insert(AccountContract.TaskEntry.TABLE_NAME, null, cv);
        }
    }

    public void deleteTasks(Long timestamp) {
        String whereClause = AccountContract.TaskEntry.COLUMN_NAME_DATE_TIMESTAMP + " = ?";
        String[] whereArgs =  {String.valueOf(timestamp)};
        int rowsDeleted = mDatabase.delete(AccountContract.TaskEntry.TABLE_NAME, whereClause, whereArgs);
        Log.d("DELETE", "Rows deleted: " + rowsDeleted);
    }

}
