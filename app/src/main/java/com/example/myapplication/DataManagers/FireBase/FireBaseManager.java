package com.example.myapplication.DataManagers.FireBase;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseExceptionMapper;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseManager{
    //Result codes
    public static String EMAIL

    private final FirebaseAuth auth;
    private FirebaseUser user;
    public FireBaseManager(){
        auth = FirebaseAuth.getInstance();
    }

    public String signInWithEmailAndPassword(String email, String password){
        //TODO сделать запись в локальную бд(чтоб можно было офлайнн)
        final String[] result = new String[1];

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                user = auth.getCurrentUser();
                result[0] = null;
            }else {
                Log.e("MYLOG_E", "createUserWithEmail:failure", task.getException());
                result[0] = ((FirebaseAuthException) task.getException()).getErrorCode();
            }
        });
        return result[0];
    }

    public String signUpWithEmailAndPassword(String email, String password){
        if (email == null) return "EMAIL_";
        if (password == null) return ;
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                user = auth.getCurrentUser();
                result[0] = FBActionResult.SUCCESS;
            }else {
                result[0] = getError(task);
            }
        });
        AU
        return result[0];
    }
}

e