package com.example.etickett;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.etickett.Model.User;
import com.example.etickett.Sql.Helper;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Helper helper;
    EditText NameforRegister;
    EditText EmailforRegister;
    EditText phoneForRegister;
    EditText passwordForLogin;
    Button SButtonRegister;
    TextView StoLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        helper = new Helper(this);
        NameforRegister =(EditText) findViewById(R.id.NameforRegister);
        EmailforRegister= (EditText)findViewById(R.id.EmailforRegister);
        phoneForRegister=(EditText) findViewById(R.id.phoneForRegister);
        passwordForLogin= (EditText) findViewById(R.id.passwordForLogin);
        SButtonRegister = (Button) findViewById(R.id.buttonForRegister);
        StoLogs = (TextView) findViewById(R.id.toLogin);

        StoLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        SButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String emailTry = EmailforRegister.getText().toString();
             String nameTry = NameforRegister.getText().toString();
             String Passtry = phoneForRegister.getText().toString();
             String phonetry ="";
                if(emailTry.isEmpty() || emailTry.length() > 50 || !Patterns.EMAIL_ADDRESS.matcher(emailTry.toString()).matches())
                {
                    EmailforRegister.setError("Please enter a valid Email");
                    EmailforRegister.requestFocus();
                    return;
                }
                else if (nameTry.isEmpty() || nameTry.length() > 20)
                {
                   NameforRegister.setError("Please enter a your Name");
                }
                else if (Passtry.isEmpty())
                {
                    passwordForLogin.setError("Please enter your password");
                }

                else {
                    User users = new User();
                    users.setName(nameTry);
                    users.setEmail(emailTry);
                    users.setPhoneNumber(phonetry);
                    users.setPassword(Passtry);

                    User results = new User();
                    results = helper.add_new_user(users);
                    if(results.getId() != -1)
                    {
                        Toast.makeText(getApplicationContext(),"Registered successfully..", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Registration failed", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
    }
}

