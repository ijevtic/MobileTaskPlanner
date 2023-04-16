package com.example.mobiletaskplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.db.SQLLiteConnectionHelper;
import com.example.mobiletaskplanner.models.AccountContract;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.view.viewmodels.SplashViewModel;

public class LoginPage extends AppCompatActivity {

    private SplashViewModel splashViewModel;
    private TextView emailError;
    private TextView usernameError;
    private TextView passwordError;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button login;

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        });
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        if(sharedPreferences.contains(Constants.IS_LOGGED_IN)
        && sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, true)){
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login_page);
        init();
    }

    private void init() {
        SQLLiteConnectionHelper connectionHelper = new SQLLiteConnectionHelper(this);
        mDatabase = connectionHelper.getWritableDatabase();
        initView();
        initListeners();
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

    private void addItems() {
        ContentValues cv = new ContentValues();
        cv.put(AccountContract.AccountEntry.COLUMN_NAME_EMAIL, "ivan@ivan.com");
        cv.put(AccountContract.AccountEntry.COLUMN_NAME_USERNAME, "ivan");
        cv.put(AccountContract.AccountEntry.COLUMN_NAME_PASSWORD, "ivan");
        mDatabase.insert(AccountContract.AccountEntry.TABLE_NAME, null, cv);
    }


    private void initView() {
        emailError = findViewById(R.id.email_error);
        usernameError = findViewById(R.id.username_error);
        passwordError = findViewById(R.id.password_error);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }

    private void initListeners() {
//        addItems();
        login.setOnClickListener(v -> {

            String emailText = email.getText().toString();
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();
            String errorMessage = "Invalid input:\n";
            if(!Util.isValidEmail(emailText)) {
                errorMessage += getString(R.string.email_wrong_info) + "\n";
                emailError.setVisibility(TextView.VISIBLE);
            } else {
                emailError.setVisibility(TextView.GONE);
            }
            if(!Util.isValidUsername(usernameText)) {
                errorMessage += getString(R.string.username_wrong_info) + "\n";
                usernameError.setVisibility(TextView.VISIBLE);
            } else {
                usernameError.setVisibility(TextView.GONE);
            }
            if(!Util.isValidPassword(passwordText)) {
                errorMessage += getString(R.string.password_wrong_info) + "\n";
                passwordError.setVisibility(TextView.VISIBLE);
            } else {
                passwordError.setVisibility(TextView.GONE);
            }
            if(!errorMessage.equals("Invalid input:\n")) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show(
                );
            }

            Cursor c = getAllItems();
            if (c.moveToFirst()) {
                @SuppressLint("Range") String email = c.getString(
                        c.getColumnIndex(AccountContract.AccountEntry.COLUMN_NAME_EMAIL));
                @SuppressLint("Range") String username = c.getString(
                        c.getColumnIndex(AccountContract.AccountEntry.COLUMN_NAME_USERNAME));
                @SuppressLint("Range") String password = c.getString(
                        c.getColumnIndex(AccountContract.AccountEntry.COLUMN_NAME_PASSWORD));
                c.close();
                if(!email.equals(emailText) || !username.equals(usernameText) || !password.equals(passwordText)) {
                    Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.apply();

                    Intent intent = new Intent(this, HomePage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}