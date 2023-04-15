package com.example.mobiletaskplanner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mobiletaskplanner.models.AccountContract;
import com.example.mobiletaskplanner.models.AccountContract.AccountEntry.*;

public class SQLLiteConnectionHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mobiletaskplanner.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " +
            AccountContract.AccountEntry.TABLE_NAME +  " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            AccountContract.AccountEntry.COLUMN_NAME_EMAIL + " TEXT NOT NULL," +
            AccountContract.AccountEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL," +
            AccountContract.AccountEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL" +
            ")";

    public SQLLiteConnectionHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS account");
        onCreate(db);
    }
}
