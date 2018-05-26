package com.rp.moview;

import android.content.Context;

import java.util.ArrayList;

public class Login {

    private String username="";

    private String password="";

    private String email="";

    private Context context;



    public Login(String username, String password, String email, Context context) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Checks if the field is empty
    public String isEmptyField(String s){
        if (s==null || s.isEmpty()){
            return context.getString(R.string.emptyField);
        }

        else {
            return null;
        }
    }

    //Checks if the username exists
    public boolean isMatch(ArrayList<String> list, String user){
        for(String s: list) {
            if (user.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    //Checks if the password is correct
    public boolean isPasswordMatch(String passoword, String enteredPassword){
        if (password.equals(enteredPassword)){
            return true;
        }
        else{
            return false;
        }

    }


}
