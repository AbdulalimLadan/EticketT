package com.example.etickett;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.etickett.Model.User;
import com.example.etickett.Sql.Helper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Helper helper;
    EditText NameforRegister;
    EditText EmailforRegister;
    EditText phoneForRegister;
    EditText passwords;
    Button SButtonRegister;
    ProgressBar progressBar;
    TextView StoLogs;
    String userID;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //firebase instance
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        helper = new Helper(this);
        NameforRegister =(EditText) findViewById(R.id.NameforRegister);
        EmailforRegister= (EditText)findViewById(R.id.EmailforRegister);
        phoneForRegister=(EditText) findViewById(R.id.phoneForRegister);
        passwords = (EditText) findViewById(R.id.passwords);
        SButtonRegister = (Button) findViewById(R.id.buttonForRegister);
        StoLogs = (TextView) findViewById(R.id.toLogin);

        StoLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //keep user logged in
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
        SButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailTry = EmailforRegister.getText().toString().trim();
                final String nameTry = NameforRegister.getText().toString().trim();
                final String phonetry = phoneForRegister.getText().toString().trim();
                String password = passwords.getText().toString().trim();

                if (nameTry.isEmpty() || nameTry.length() > 40) {
                    NameforRegister.setError("Please enter a your Name");
                    NameforRegister.requestFocus();
                    return;
                }
                if (emailTry.isEmpty() || emailTry.length() > 50 || !Patterns.EMAIL_ADDRESS.matcher(emailTry.toString()).matches()) {
                    EmailforRegister.setError("Please enter a valid Email");
                    EmailforRegister.requestFocus();
                    return;
                }

                if (password.isEmpty())
                {
                    passwords.setError("Please enter a password");
                    passwords.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase
                firebaseAuth.createUserWithEmailAndPassword(emailTry, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account Created.", Toast.LENGTH_LONG).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",nameTry);
                            user.put("email",emailTry);
                            user.put("phone",phonetry);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   Log.d("tag", "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                   Log.d("tag", "onFailure: " + e.toString());

                                }
                            });
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });
    }
}


