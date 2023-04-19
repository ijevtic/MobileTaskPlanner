package com.example.mobiletaskplanner.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mobiletaskplanner.models.AccountContract;

public class DBHelper {
    private SQLiteDatabase mDatabase;

    public DBHelper(Context context){
        SQLLiteConnectionHelper connectionHelper = new SQLLiteConnectionHelper(context);
        mDatabase = connectionHelper.getWritableDatabase();
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

}
