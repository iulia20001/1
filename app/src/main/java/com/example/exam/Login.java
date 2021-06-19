package com.example.exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText edEmailLogin, edPassLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edEmailLogin.getText().toString()) ||
                        TextUtils.isEmpty(edPassLogin.getText().toString()))
                {
                    ShowDialogWindow("Пустые поля");
                } else {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEdEmailLogin(edEmailLogin.getText().toString());
                    loginRequest.setEdPassLogin(edPassLogin.getText().toString());
                    LoginUser(loginRequest);
                }
            }
        });
    }

    public void LoginUser(LoginRequest loginRequest)
    {
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
                else{
                    ShowDialogWindow("Что-то пошло не так, повторите попытку позже");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ShowDialogWindow(t.getLocalizedMessage());
            }
        });
    }

    public void ShowDialogWindow(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(Login.this).setMessage(text).setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }

    public void init(){
        edEmailLogin = findViewById(R.id.EmailLogin);
        edPassLogin = findViewById(R.id.PassLogin);
        btnLogin = findViewById(R.id.Login);
    }
}