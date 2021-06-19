package com.example.exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    Button btnRegistration;
    EditText edName, edSurname, edEmail, edPass1, edPass2;
    TextView tvAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        btnRegistration = findViewById(R.id.Registartion);
        edName = findViewById(R.id.Name);
        edSurname = findViewById(R.id.SurName);
        edEmail = findViewById(R.id.Email);
        edPass1 = findViewById(R.id.Pass1);
        edPass2 = findViewById(R.id.Pass2);
        tvAccount = findViewById(R.id.Account);

        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
            }
        });
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edName.getText().toString())||
                        TextUtils.isEmpty(edSurname.getText().toString())||
                        TextUtils.isEmpty(edEmail.getText().toString())||
                        TextUtils.isEmpty(edPass1.getText().toString())||
                        TextUtils.isEmpty(edPass2.getText().toString())) {
                    ShowDialogWindow ("Пустые поля");
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()) {
                        if (edPass1.getText().toString().equals(edPass2.getText().toString())) {
                            RegisterRequest registerRequest = new RegisterRequest();
                            registerRequest.setEdName(edName.getText().toString());
                            registerRequest.setEdSurName(edSurname.getText().toString());
                            registerRequest.setEdEmail(edEmail.getText().toString());
                            registerRequest.setEdPass(edPass1.getText().toString());
                            RegisterUser(registerRequest);
                        } else{
                                ShowDialogWindow("Повторите пароль!");
                            }
                        }
                        else{
                            ShowDialogWindow("Email некорректный");
                        }
                    }
                }
            });
        }
        private void RegisterUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(Registration.this, MainActivity.class));
                } else {
                    ShowDialogWindow("Что-то пошло не так, повторите попытку позже");
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ShowDialogWindow(t.getLocalizedMessage());
            }
        });
    }

    public void ShowDialogWindow(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).setMessage(text).setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }

    public void init(){
        edName = findViewById(R.id.Name);
        edSurname = findViewById(R.id.SurName);
        edEmail = findViewById(R.id.Email);
        edPass1 = findViewById(R.id.Pass1);
        edPass2 = findViewById(R.id.Pass2);
        tvAccount = findViewById(R.id.Account);
        btnRegistration = findViewById(R.id.Registartion);
    }
}