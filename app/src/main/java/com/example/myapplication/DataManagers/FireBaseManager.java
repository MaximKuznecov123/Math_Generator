package com.example.myapplication.DataManagers;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseManager{
    private final FirebaseAuth auth;
    private FirebaseUser user;
    public FireBaseManager(){
        auth = FirebaseAuth.getInstance();
    }

    public boolean signInWithEmailAndPassword(String email, String password){
        final boolean[] result = {false};
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                user = auth.getCurrentUser();
                result[0] = true;
            }else {
                Log.e("MYLOG_E", "createUserWithEmail:failure", task.getException());
                result[0] = false;
            }
        });
        return result[0];
    }

    public boolean signUpWithEmailAndPassword(String email, String password){
        final boolean[] result = {false};
        //TODO проверка почты и пароля. Проверка на существование пользователя
        //TODO отправка писем на почту(https://support.google.com/firebase/answer/7000714#)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                user = auth.getCurrentUser();
                result[0] = true;
            }else {
                Log.e("MYLOG_E", "createUserWithEmail:failure", task.getException());
                result[0] = false;
            }
        });
        return result[0];
    }

    public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

}