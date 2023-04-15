package com.example.mobiletaskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.viewmodels.SplashViewModel;

public class LoginPage extends AppCompatActivity {

    private SplashViewModel splashViewModel;
    private TextView emailError;
    private TextView usernameError;
    private TextView passwordError;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button login;

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
        setContentView(R.layout.activity_login_page);
        init();
    }

    private void init() {
        initView();
        initListeners();
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

            //check email and password
            //if correct, go to homepage

//            Intent intent = new Intent(this, HomePage.class);
//            startActivity(intent);
        });
    }


}