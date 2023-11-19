package com.example.myapplication;

public class LoginMethods {
    public static boolean checkpass(String user, String pass, String entereduser, String enteredpass){
        System.out.println(user + pass + entereduser + enteredpass);
        if ((entereduser == user)&&(enteredpass==pass)){

            return true;
        }
        else return false;
    }

}
