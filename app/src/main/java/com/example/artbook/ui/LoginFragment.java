package com.example.artbook.ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.artbook.R;
import com.example.artbook.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    SQLiteDatabase db;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createDatabase();
        handleLoginButtonClick();
        handleSignUpButtonClick();
    }

    private void createDatabase() {
        try {
            db = requireContext().openOrCreateDatabase("Login", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS person(id INTEGER PRIMARY KEY, userName VARCHAR, emailAdress VARCHAR, password VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLoginButtonClick() {
        binding.buttonLogin.setOnClickListener(view -> {
            String textViewEmailAdress = binding.emailAdress.getText().toString();
            String textViewPassword = binding.password.getText().toString();

            Cursor cursor = db.rawQuery("SELECT * FROM person WHERE emailAdress=? AND password=?", new String[]{textViewEmailAdress, textViewPassword});
            if (cursor.moveToFirst()) {
                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_homeFragment);
            } else {
                Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        });
    }

    private void handleSignUpButtonClick() {
        binding.btnSignUp.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_loginFragment_to_signUpFragment);
        });

    }


}