package com.example.artbook.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.example.artbook.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    SQLiteDatabase db;

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createDatabase();
        handleSignUpButtonClick();
        showDataFromDatabase();
    }

    private void createDatabase() {
        try {
            db = requireContext().openOrCreateDatabase("Login", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS person(id INTEGER PRIMARY KEY, userName VARCHAR, emailAdress VARCHAR, password VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSignUpButtonClick() {
        binding.buttonSignUp.setOnClickListener(view -> {
            String userName = binding.editTextUserName.getText().toString();
            String emailAdress = binding.editTextEmailAdress.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();

            ContentValues values = new ContentValues();
            values.put("userName", userName);
            values.put("emailAdress", emailAdress);
            values.put("password", password);
            db.insert("person", null, values);

            binding.editTextUserName.setText("");
            binding.editTextEmailAdress.setText("");
            binding.editTextPassword.setText("");

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_signUpFragment_to_loginFragment);
        });
    }

    private void showDataFromDatabase() {
        Cursor cursor = db.rawQuery("SELECT * FROM person", null);
        int idIndex = cursor.getColumnIndex("id");
        int userNameIndex = cursor.getColumnIndex("userName");
        int emailAdressIndex = cursor.getColumnIndex("emailAdress");
        int passwordIndex = cursor.getColumnIndex("password");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String userName = cursor.getString(userNameIndex);
            String emailAdress = cursor.getString(emailAdressIndex);
            String password = cursor.getString(passwordIndex);
            Log.d("SignUpFragment", "id: " + id + ", userName: " + userName + ", emailAdress: " + emailAdress + ", password: " + password);
        }
        cursor.close();
    }


}