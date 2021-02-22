package com.example.proyectoandroid;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.proyectoandroid.model.Usuario;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BaseFragment extends Fragment {

    public NavController navController;
    public AppViewModel appViewModel;
    Usuario usuario;
    public int userId;
    Executor executor;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        executor = Executors.newSingleThreadExecutor();

    }
    void showToast() {

        Toast toast = new Toast(getContext());

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.custom_toast, null);

       TextView tvMessage = view.findViewById(R.id.tvMessage);
        toast.setGravity(Gravity.FILL_VERTICAL, 0,0);
        toast.setView(view);
        toast.show();

    }


    void showToastDireccion(String text) {

        Toast toast = new Toast(getContext());

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.toast_generico, null);

        TextView tvMessage = view.findViewById(R.id.tvMessage);
        tvMessage.setText(text);
        toast.setGravity(Gravity.FILL_VERTICAL, 0,0);
        toast.setView(view);
        toast.show();

    }
}
