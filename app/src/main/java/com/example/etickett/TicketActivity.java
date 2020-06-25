package com.example.etickett;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener {
ImageView back;
ImageButton fordate;
Spinner departure, arrival, persons,classs;
EditText date;
Button  searchTkt;
private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        back = (ImageView) findViewById(R.id.back);
        date = (EditText) findViewById(R.id.dateSelect);
        fordate = (ImageButton) findViewById(R.id.fordate);
        searchTkt = (Button) findViewById(R.id.searchTkt);
        final Spinner spinner = (Spinner) findViewById(R.id.departure);
        final Spinner spins = (Spinner) findViewById(R.id.arrival);
        final Spinner s = (Spinner) findViewById(R.id.classs);
        final Spinner sd = (Spinner) findViewById(R.id.people);

        //back to home page
        back.setOnClickListener(this);
        //display date
        fordate.setOnClickListener(this);
        //for search
        searchTkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String dates = date.getText().toString();
               if(dates.isEmpty())
               {
                   date.setError("Date is required for search");
                   date.requestFocus();
                   return;
               }
                Intent it = new Intent(TicketActivity.this,ResultActivity.class);
                it.putExtra("Departure",String.valueOf(spinner.getSelectedItem()));
                it.putExtra("Arrival",String.valueOf(spins.getSelectedItem()));
                it.putExtra("classs",String.valueOf(s.getSelectedItem()));
                it.putExtra("people",String.valueOf(sd.getSelectedItem()));
                it.putExtra("passdate",String.valueOf(date.getText()));
                startActivity(it);
            }
        });


        // for departure
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("Tickets");

        final List<String> Tickets = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Tickets);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("Departure");
                        Tickets.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        //for Arrival
        FirebaseFirestore rootRefs = FirebaseFirestore.getInstance();
        CollectionReference subjectsRf = rootRefs.collection("Tickets");

        final List<String> Ticke = new ArrayList<>();
        final ArrayAdapter<String> adapters = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Ticke);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spins.setAdapter(adapters);
        subjectsRf.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subjects = document.getString("Arrival");
                        Ticke.add(subjects);
                    }
                    adapters.notifyDataSetChanged();
                }
            }
        });

        //for class
        String[] arraySpinner = new String[]{
                "Economy class", "First class",
        };

        ArrayAdapter<String> adapted = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapted);

        // for persons
        String[] arraySpinners = new String[]{
                "1", "2", "3", "4"
        };

        ArrayAdapter<String> adapterss = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinners);
        adapterss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sd.setAdapter(adapterss);
    }
// for date
    @Override
    public void onClick(View v) {
    if (v == fordate)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date.setText(  dayOfMonth + "-" +(monthOfYear + 1) + "-" + year);
                    }
                },  mDay,mMonth, mYear);
        datePickerDialog.show();

    }
    if (v == back)
    {
        Intent intback  = new Intent(TicketActivity.this,HomeActivity.class);
        startActivity(intback);
    }



    }
}
