package com.example.myapplication.Activities.MainActivity.AuthFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.MainActivity.MainActivity;
import com.example.myapplication.Activities.SelectModeActivity;
import com.example.myapplication.databinding.FragmentEnterBinding;

public class EnterFragment extends Fragment {
    FragmentEnterBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEnterBinding.inflate(inflater);
        binding.enter.setOnClickListener(v -> {
            Intent i;
            if(false){//TODO проверка вошел в кабинет или нет
                i = new Intent(getActivity(), SelectModeActivity.class);
                startActivity(i);
            }else{
                ((MainActivity)getActivity()).changeFragment(new LogInFragment());
            }
        });
        return binding.getRoot();
    }
}
