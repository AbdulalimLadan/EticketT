package com.example.etickett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.etickett.Model.User;
import com.example.etickett.Sql.Helper;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = (EditText)findViewById(R.id.Emailforlogin);
        lPassword = (EditText)findViewById(R.id.passwordForLogin);
        lButton = (Button)findViewById(R.id.ButtonLogin);
        tSignup = (TextView)findViewById(R.id.toSignup);
        lRemember = (CheckBox) findViewById(R.id.lRemember);
        lForgot = (TextView)findViewById(R.id.toForgot);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        db = new Helper(this);

        //SharedPreference for remember me
        editor=sp.edit();
        String mail=sp.getString("email"," ");
        String passwords=sp.getString("password","");
        lEmail.setText(mail);
        lPassword.setText(passwords);

        //keep the user logged in
        if(sp.getBoolean("logged",false)){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        // to Register Activity
        tSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        // to Forgot Activity
        lForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(forgotIntent);
            }
        });

        // to Login
        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  llPassword =  lPassword.getText().toString();
                String llEmail = lEmail.getText().toString();
                //keeps the user logged in
                sp.edit().putBoolean("logged",true).apply();

                //Remember me
                if (lRemember.isChecked()) {
                    editor.putString("email", lEmail.getText().toString());
                    editor.putString("password", lPassword.getText().toString());
                    editor.commit();
                }
                    //Validation on fields
                if(llEmail.isEmpty() || llEmail.length() > 50 || !Patterns.EMAIL_ADDRESS.matcher(llEmail.toString()).matches())
                {
                    lEmail.setError("Please enter a valid Email");
                    lEmail.requestFocus();
                    return;
                }
                else if(llPassword.isEmpty()){
                    lPassword.setError("Please enter a valid password");
                    lPassword.requestFocus();
                    return;
                }
                else {
                    User user_request = new User();
                    user_request.setPassword(llPassword);
                    user_request.setEmail(llEmail);

                    User result = new User();
                    result = db.getUser(user_request);
                    //Log.i("DBMessage", result.getMessage());
                    if (result != null && result.getMessage() == "Success") {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.putExtra("Email", llEmail);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed..", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }
}



