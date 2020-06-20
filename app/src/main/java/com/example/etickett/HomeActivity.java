package com.example.etickett;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {
 Button tkt, sch, wlt, hlp;
 ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tkt = (Button)findViewById(R.id.toTicket);
        sch = (Button)findViewById(R.id.toschedule);
        wlt = (Button)findViewById(R.id.towallet);
        hlp = (Button)findViewById(R.id.tohelp);
        img = (ImageView)findViewById(R.id.toprofile);

        //toTicket
        tkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tktIntent = new Intent(HomeActivity.this,TicketActivity.class);
                startActivity(tktIntent);
            }
        });
        // to schedule
        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // to wallet
        wlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // to help

        hlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgintent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(imgintent);
            }
        });
    }

}
