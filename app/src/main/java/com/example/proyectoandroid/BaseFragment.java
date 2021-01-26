package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class BaseFragment extends Fragment {

    public NavController navController;
    public AppViewModel appViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
    }
}
