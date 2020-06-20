package com.example.etickett;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    Button logout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView namedisplay, emaildisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = (Button) findViewById(R.id.logout);
        namedisplay = (TextView)findViewById(R.id.namedisplay);
        emaildisplay =(TextView)findViewById(R.id.emaildisplay);

//        Intent intent=getIntent();
//        String Email=intent.getStringExtra("Email");
//        emaildisplay.setText(Email);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intlog = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intlog);

            }
        });
    }
}
