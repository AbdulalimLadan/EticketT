package com.example.etickett;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.etickett.Model.User;
import com.example.etickett.Sql.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    Helper db;
    private User user;
    EditText lEmail;
    TextView lForgot;
    EditText lPassword;
    Button lButton;
    TextView tSignup;
    CheckBox lRemember;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //firebase instance
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar1);

        lEmail = (EditText) findViewById(R.id.Emailforlogin);
        lPassword = (EditText) findViewById(R.id.passwordForLogin);
        lButton = (Button) findViewById(R.id.ButtonLogin);
        tSignup = (TextView) findViewById(R.id.toSignup);
        lRemember = (CheckBox) findViewById(R.id.lRemember);
        lForgot = (TextView) findViewById(R.id.toForgot);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        db = new Helper(this);

        //SharedPreference for remember me
        editor = sp.edit();
        String mail = sp.getString("email", " ");
        String passwords = sp.getString("password", "");
        lEmail.setText(mail);
        lPassword.setText(passwords);

        //keep the user logged in
//        if (sp.getBoolean("logged", false)) {
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(intent);
//        }

            // to Register Activity
            tSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });

            // to Forgot Activity
            lForgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText resetMail = new EditText(v.getContext());
                    final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                    passwordResetDialog.setTitle("Reset Password ?");
                    passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                    passwordResetDialog.setView(resetMail);

                    passwordResetDialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // extract the email and send reset link
                            String mail = resetMail.getText().toString();
                            firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

                    passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // close the dialog
                        }
                    });

                    passwordResetDialog.create().show();

                }
            });

            // to Login
            lButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String llPassword = lPassword.getText().toString();
                    String llEmail = lEmail.getText().toString();

                    //keeps the user logged in
                    //sp.edit().putBoolean("logged", true).apply();

                    //Remember me
                    if (lRemember.isChecked()) {
                        editor.putString("email", lEmail.getText().toString());
                        editor.putString("password", lPassword.getText().toString());
                        editor.commit();
                    }
                    //Validation on fields
                    if (llEmail.isEmpty() || llEmail.length() > 50 || !Patterns.EMAIL_ADDRESS.matcher(llEmail.toString()).matches()) {
                        lEmail.setError("Please enter a valid Email");
                        lEmail.requestFocus();
                        return;
                    }
                    if (llPassword.isEmpty()) {
                        lPassword.setError("Please enter a valid password");
                        lPassword.requestFocus();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    firebaseAuth.signInWithEmailAndPassword(llEmail, llPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            });
        }
    }




