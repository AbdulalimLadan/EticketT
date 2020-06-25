package com.example.etickett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.etickett.Model.Ticket;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

//displays results based on the search.
public class ResultActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ticketRef = db.collection("Tickets");
    private  TicketAdapter adapter;
    private TicketAdapter.TicketHolder tk;
    RecyclerView recyclerView;
ImageView back;
TextView fromResult, toResult,DateResult,prices,TimeDeparture,TimeArrival,theclass,personresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        back = (ImageView) findViewById(R.id.bacckk);
        fromResult = (TextView) findViewById(R.id.fromResult);
        toResult = (TextView) findViewById(R.id.toResult);
        DateResult = (TextView) findViewById(R.id.DateResult);
        //prices = (TextView) findViewById(R.id.prices);
        //TimeDeparture = (TextView) findViewById(R.id.TimeDeparture);
        //TimeArrival = (TextView) findViewById(R.id.TimeArrival);
        theclass = (TextView) findViewById(R.id.theclass);
        personresult = (TextView) findViewById(R.id.personresult);

        Bundle bundle = getIntent().getExtras();
        String dep = bundle.get("Departure").toString();
        String arrvs = bundle.get("Arrival").toString();
        String cls = bundle.get("classs").toString();
        String ppl = bundle.get("people").toString();
        String strdata = bundle.getString("passdate");

        fromResult.setText(dep);
        theclass.setText(cls);
        toResult.setText(arrvs);
        personresult.setText(ppl);
        DateResult.setText(strdata);

        // for recycler view using the classs type to search

        Query query = ticketRef.whereEqualTo("dClasstype", cls);
        FirestoreRecyclerOptions<Ticket> options = new FirestoreRecyclerOptions.Builder<Ticket>()
               // .setQuery(query, Ticket.class)
                .setQuery(query, Ticket.class)
                .build();
        adapter = new TicketAdapter(options);
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        //go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,TicketActivity.class);
                startActivity(intent);
            }
        });
    }
        @Override
        protected  void onStart()
        {
            super.onStart();
            adapter.startListening();
        }

    @Override
    protected  void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }




    }


