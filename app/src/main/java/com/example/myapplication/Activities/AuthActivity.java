package com.example.myapplication.Activities;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DataManagers.FireBaseConstants.DataFields;
import com.example.myapplication.DataManagers.FireBaseConstants.ErrorCodes;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AuthActivityBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
            } else {
                changeActivityMode((short) 2);
            }

        });

        binding.forgotPassword.setOnClickListener(v -> {
            changeActivityMode((short) 3);
        });
    }

    private short mode = 1;

    private void changeActivityMode(short newMode) {
        binding.errorMessage.setText("");
        switch (newMode) {
            case 1://регистрация
                binding.auth.setOnClickListener(v -> signUp());

                binding.auth.setText(R.string.signUp);
                binding.changeAuthOption.setText(R.string.signIn);
                binding.username.setVisibility(View.VISIBLE);

                if (mode == 3) {
                    binding.password.setVisibility(View.VISIBLE);
                    binding.forgotPassword.setVisibility(View.VISIBLE);
                }
                break;
            case 2://вход
                binding.auth.setOnClickListener(v -> signIn());

                binding.auth.setText(R.string.signIn);
                binding.changeAuthOption.setText(R.string.signUp);
                binding.username.setVisibility(View.GONE);

                if (mode == 3) {
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

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.errorMessage.setText(R.string.email_not_correct_error);
            return;
        } else if (password.isEmpty() || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$")) {
            binding.errorMessage.setText(R.string.password_not_correct_error);
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //TODO запись в локальную дб
            } else {
                setErrorMessageWithException(task);
            }
        });
        binding.errorMessage.setText("");

    }

    private void signUp() {
        String username = String.valueOf(binding.username.getText());
        String email = String.valueOf(binding.email.getText());
        String password = String.valueOf(binding.password.getText());

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.errorMessage.setText(R.string.email_not_correct_error);
            return;
        } else if (password.isEmpty() || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$")) {
            binding.errorMessage.setText(R.string.password_not_correct_error);
            return;
        } else if (username.isEmpty()) {
            binding.errorMessage.setText(R.string.name_must_be_filled_error);
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                //Вот этот кусок кода возможно нужно переделать отдельный класс с одним методом. Будет вызываться как анонимный
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(auth.getCurrentUser().getUid())
                        .setValue(new HashMap<String, String>() {{
                            put(DataFields.USERNAME, username);
                        }})
                        .addOnCompleteListener((taskDatabase) -> {
                            if (taskDatabase.isSuccessful()) {
                                //TODO запись в локальную базу
                            } else {
                                //TODO обработка ошибки(если надо будет)
                            }
                        });
            } else {
                setErrorMessageWithException(task);
            }

        });
    }

    private void resetPassword() {
        String email = String.valueOf(binding.email.getText());
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.errorMessage.setText(R.string.email_not_correct_error);
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.useAppLanguage();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, R.string.password_reset_meil_sent, Toast.LENGTH_LONG).show();
                    } else {
                        setErrorMessageWithException(task);
                    }
                });
    }

    private void setErrorMessageWithException(Task task){
        Integer stringResourceId = ErrorCodes.errorMap.get(((FirebaseAuthException) task.getException()).getErrorCode());
        if (stringResourceId == null) {
            Log.e("MYLOG_E", task.getException().getMessage());
            binding.errorMessage.setText(R.string.unknown_error);
        } else {
            binding.errorMessage.setText(stringResourceId);
        }
    }
}