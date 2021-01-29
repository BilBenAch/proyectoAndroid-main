package com.example.proyectoandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoandroid.databinding.FragmentMiCuentaEmailBinding;
import com.example.proyectoandroid.databinding.FragmentSplashScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SplashScreenFragment extends BaseFragment {
    FragmentSplashScreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentSplashScreenBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);

        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
               navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
            }
        });

        // esto deberia estar en el Model y llamarlo a traves del ViewModel
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // simular la carga de recursos
                    Thread.sleep(2400);
                    finishedLoading.postValue(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}