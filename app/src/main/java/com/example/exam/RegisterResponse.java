package com.example.exam;

public class RegisterResponse {

    private int id;
    private String edEmail, edPass;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEdEmail() {
        return edEmail;
    }

    public void setEdEmail(String edEmail) {
        this.edEmail = edEmail;
    }

    public String getEdPass() {
        return edPass;
    }

    public void setEdPass(String edPass) {
        this.edPass = edPass;
    }
}
