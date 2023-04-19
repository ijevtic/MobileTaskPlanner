package com.example.mobiletaskplanner.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobiletaskplanner.R;
import com.example.mobiletaskplanner.db.DBHelper;
import com.example.mobiletaskplanner.db.SQLLiteConnectionHelper;
import com.example.mobiletaskplanner.models.AccountContract;
import com.example.mobiletaskplanner.utils.Constants;
import com.example.mobiletaskplanner.utils.Util;
import com.example.mobiletaskplanner.view.activities.HomePage;

public class ProfileFragment extends Fragment {

    private TextView emailTv;
    private Button logoutBtn;
    private Button changePasswordBtn;
    private Button updatePasswordBtn;
    private EditText oldPasswordEt;
    private EditText newPasswordEt;
    private Button giveUp;

    private DBHelper dbHelper;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initDbConn();

        initView();
        initListeners();
        setupView();


    }

    private void initDbConn() {
        dbHelper = new DBHelper(getContext());
    }

    private void initView() {
        emailTv = getView().findViewById(R.id.student_name);
        changePasswordBtn = getView().findViewById(R.id.change_password_button);
        logoutBtn = getView().findViewById(R.id.logout_button);
        updatePasswordBtn = getView().findViewById(R.id.update_password_button);
        giveUp = getView().findViewById(R.id.give_up_button);
        oldPasswordEt = getView().findViewById(R.id.old_password_edit_text);
        newPasswordEt = getView().findViewById(R.id.new_password_edit_text);
    }

    private void initListeners() {
        logoutBtn.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                    getActivity().getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Constants.EMAIL);
            editor.remove(Constants.IS_LOGGED_IN);
            editor.apply();
            getActivity().finish();
        });

        changePasswordBtn.setOnClickListener(v -> {
            oldPasswordEt.setVisibility(View.VISIBLE);
            newPasswordEt.setVisibility(View.VISIBLE);
            updatePasswordBtn.setVisibility(View.VISIBLE);
            changePasswordBtn.setVisibility(View.GONE);
            giveUp.setVisibility(View.VISIBLE);
        });

        updatePasswordBtn.setOnClickListener(v -> {

            if(oldPasswordEt.getText().toString().isEmpty() || newPasswordEt.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), getContext().getString(R.string.empty_fields),
                        Toast.LENGTH_SHORT).show();
            }
            else if(oldPasswordEt.getText().equals(newPasswordEt.getText())) {
                Toast.makeText(getContext(), getContext().getString(R.string.passwords_cannot_be_the_name),
                        Toast.LENGTH_SHORT).show();
            }
            else if(!dbHelper.validatePassword(oldPasswordEt.getText().toString())) {
                Toast.makeText(getContext(), getContext().getString(R.string.invalid_old_password),
                        Toast.LENGTH_SHORT).show();
            }
            else if(!Util.isValidPassword(newPasswordEt.getText().toString())) {
                Toast.makeText(getContext(), getContext().getString(R.string.invalid_new_password),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                dbHelper.updatePassword(newPasswordEt.getText().toString(), emailTv.getText().toString());
                Toast.makeText(getContext(), getContext().getString(R.string.password_updated_successfully),
                        Toast.LENGTH_SHORT).show();
                oldPasswordEt.setVisibility(View.GONE);
                newPasswordEt.setVisibility(View.GONE);
                updatePasswordBtn.setVisibility(View.GONE);
                changePasswordBtn.setVisibility(View.VISIBLE);
                giveUp.setVisibility(View.GONE);
                cleanFields();
            }
        });

        giveUp.setOnClickListener(v -> {
            oldPasswordEt.setVisibility(View.GONE);
            newPasswordEt.setVisibility(View.GONE);
            updatePasswordBtn.setVisibility(View.GONE);
            changePasswordBtn.setVisibility(View.VISIBLE);
            giveUp.setVisibility(View.GONE);
            cleanFields();
        });
    }

    private void setupView() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                getActivity().getPackageName(), Context.MODE_PRIVATE);
        SpannableString content = new SpannableString(sharedPreferences.getString(Constants.EMAIL, ""));

        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        emailTv.setText(content);
    }

    private void cleanFields() {
        oldPasswordEt.setText("");
        newPasswordEt.setText("");
    }


}