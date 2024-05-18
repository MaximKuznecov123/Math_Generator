package com.example.myapplication.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DataManagers.FireBaseManager;
import com.example.myapplication.databinding.AuthActivityBinding;

public class AuthActivity extends AppCompatActivity {
    AuthActivityBinding binding;
    private short mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = AuthActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.auth.setOnClickListener(v -> signUp());
        binding.changeAuthOption.setOnClickListener(v -> {
            if (mode == 1) {
                mode = 2;
            } else {
                mode = 1;
            }
            changeActivityMode();
        });
        binding.forgotPassword.setOnClickListener(v -> {
            mode = 3;
            changeActivityMode();
        });
    }

    //TODO закончить этот метод
    private void changeActivityMode() {
        switch (mode) {
            case 1://регистрация

                break;
            case 2://вход

                break;
            case 3://сброс пароля

                break;
        }
    }

    private void signIn() {
        String email = String.valueOf(binding.email.getText());
        String password = String.valueOf(binding.password.getText());
        FireBaseManager fireBaseManager = new FireBaseManager();
        fireBaseManager.signInWithEmailAndPassword(email, password);
    }

    private void signUp() {
        //TODO сделать ввод имени
        String email = String.valueOf(binding.email.getText());
        String password = String.valueOf(binding.password.getText());
        FireBaseManager fireBaseManager = new FireBaseManager();
        fireBaseManager.signUpWithEmailAndPassword(email, password);
    }

}
