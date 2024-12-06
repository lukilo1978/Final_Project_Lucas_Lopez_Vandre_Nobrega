package com.example.assignment2lucaslopez;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView titlePage1, textPage1, error;
    EditText emailInput, passwordInput;
    Button registerbtn, loginbtn;
    FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerbtn = findViewById(R.id.registerbtn);
        loginbtn = findViewById(R.id.loginbtn);
        error = findViewById(R.id.error);

        mAuth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please, review your information carefully before sumbiting");

                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface popUp, int which) {
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(getApplicationContext(),
                                                    "User Registered Successfully: " + user.getEmail(),
                                                    Toast.LENGTH_LONG).show();

                                            // Pass user info to the next activity
                                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                            intent.putExtra("USER_EMAIL", user.getEmail());
                                            intent.putExtra("USER_UID", user.getUid());
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Registration Failed: " + task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface popUp, int which) {
                            popUp.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    error.setVisibility(View.VISIBLE);
                }
            }

        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(),
                                        "User Signed In Successfully: " + user.getEmail(),
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                intent.putExtra("USER_EMAIL", user.getEmail());
                                intent.putExtra("USER_UID", user.getUid());
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign-In Failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}