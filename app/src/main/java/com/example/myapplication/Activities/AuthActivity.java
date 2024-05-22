package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DataManagers.FireBase.FireBaseManager;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AuthActivityBinding;

public class AuthActivity extends AppCompatActivity {
    AuthActivityBinding binding;


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
            if (mode == 2) {
                changeActivityMode((short) 1);
            } else{
                changeActivityMode((short) 2);
            }

        });

        binding.forgotPassword.setOnClickListener(v -> {
            changeActivityMode((short) 3);
        });
    }

    private short mode = 1;
    private void changeActivityMode(short newMode) {
        switch (newMode) {
            case 1://регистрация
                binding.auth.setOnClickListener(v -> signUp());

                binding.auth.setText(R.string.signUp);
                binding.changeAuthOption.setText(R.string.signIn);
                binding.username.setVisibility(View.VISIBLE);

                if (mode == 3){
                    binding.password.setVisibility(View.VISIBLE);
                    binding.forgotPassword.setVisibility(View.VISIBLE);
                }
                break;
            case 2://вход
                binding.auth.setOnClickListener(v -> signIn());

                binding.auth.setText(R.string.signIn);
                binding.changeAuthOption.setText(R.string.signUp);
                binding.username.setVisibility(View.GONE);

                if (mode == 3){
                    binding.password.setVisibility(View.VISIBLE);
                    binding.forgotPassword.setVisibility(View.VISIBLE);
                }
                break;
            case 3://сброс пароля
                binding.auth.setOnClickListener(v -> resetPassword());
                binding.auth.setText(R.string.reset_password);

                binding.password.setVisibility(View.GONE);
                binding.forgotPassword.setVisibility(View.GONE);
                binding.username.setVisibility(View.GONE);
                binding.changeAuthOption.setText(R.string.signIn);
                break;
        }
        mode = newMode;
    }

    private void signIn() {
        String email = String.valueOf(binding.email.getText());
        String password = String.valueOf(binding.password.getText());

        FireBaseManager fireBaseManager = new FireBaseManager();
        fireBaseManager.signInWithEmailAndPassword(email, password);
    }

    private void signUp() {
        //TODO добавить сохранение имени
        String username = String.valueOf(binding.username.getText());
        String email = String.valueOf(binding.email.getText());
        String password = String.valueOf(binding.password.getText());

        FireBaseManager fireBaseManager = new FireBaseManager();
        fireBaseManager.signUpWithEmailAndPassword(email, password);
    }

    private void resetPassword() {
        //TODO сделать этот метод
    }
}