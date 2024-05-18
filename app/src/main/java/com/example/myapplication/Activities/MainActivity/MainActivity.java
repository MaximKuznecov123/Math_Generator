package com.example.myapplication.Activities.MainActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentContainerView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //TODO Вызывается из фрагментов, поэтому желательно сделать другой способ их смены  ̶и̶л̶и̶ ̶п̶р̶о̶с̶т̶о̶ ̶з̶а̶б̶и̶т̶ь̶.
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainerView.getId(), fragment).commit();
    }
}