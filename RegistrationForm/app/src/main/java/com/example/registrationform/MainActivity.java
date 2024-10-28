// MainActivity.java
package com.example.registrationform;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword;
    private RadioGroup radioGroupGender;
    private Spinner spinnerCountry;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Setup country spinner
        String[] countries = {"India", "USA", "UK", "Canada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                countries
        );
        spinnerCountry.setAdapter(adapter);

        // Setup submit button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    showRegistrationDetails();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Validate name
        String name = editTextName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Name is required");
            isValid = false;
        }

        // Validate email
        String email = editTextEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid email format");
            isValid = false;
        }

        // Validate password
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }

        // Validate gender selection
        if (radioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void showRegistrationDetails() {
        // Get form data
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String country = spinnerCountry.getSelectedItem().toString();
        String gender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioMale ? "Male" : "Female";

        // Create message
        String message = "Registration Details:\n\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Gender: " + gender + "\n" +
                "Country: " + country;

        // Show success toast
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

        // Show details in AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Registration Successful")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}