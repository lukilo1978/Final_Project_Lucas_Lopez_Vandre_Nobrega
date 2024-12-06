package com.example.assignment2lucaslopez;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    TextView titlePage2, textPage2, userTextView, UIDTextView;
    EditText foodedit, gramsedit;
    Button searchbtn;
    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String email = getIntent().getStringExtra("USER_EMAIL");
        String uid = getIntent().getStringExtra("USER_UID");


        userTextView = findViewById(R.id.foodTextView);
        UIDTextView = findViewById(R.id.gramsTextView);
        foodedit = findViewById(R.id.food);
        gramsedit = findViewById(R.id.grams);
        searchbtn = findViewById(R.id.searchbtn);


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String food = foodedit.getText().toString();
                String gramsUnparsed = gramsedit.getText().toString();
                Number grams = Integer.parseInt(gramsUnparsed);

                if (!food.isEmpty() && !grams.equals(0)){
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("FOOD", food);
                    intent.putExtra("GRAMS", grams);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Registration Failed: ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}