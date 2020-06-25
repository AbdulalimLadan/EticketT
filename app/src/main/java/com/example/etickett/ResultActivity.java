package com.example.etickett;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private FirebaseFirestore firebaseFirestore;
    //private CollectionReference ticketRef = db.collection("Tickets");
    //private  TicketAdapter adapter;
    RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
ImageView back;
TextView fromResult, toResult,DateResult,theclass,personresult;
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

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firebaseFirestore.collection("Tickets");
        //RecyclerOptions
        FirestoreRecyclerOptions<Ticket> options = new FirestoreRecyclerOptions.Builder<Ticket>()
                .setQuery(query,Ticket.class)
                .build();

Log.I("TAG", "options count " + options.size());

         adapter = new FirestoreRecyclerAdapter<Ticket, TicketViewHolder>(options) {
            @NonNull
            @Override
            public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
                return new TicketViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TicketViewHolder holder, int position, @NonNull Ticket model) {
                Log.I("TAG", " 1 model price " + options.get(position).getPrice());
                Log.I("TAG", " 100 model price " + model.getPrice());
                holder.prices.setText(model.getPrice());
                holder.theclassdb.setText(model.getdClasstype());
                holder.TimeArrival.setText(model.getArrivalTime());
                holder.TimeDeparture.setText(model.getDepartureTime());
            }
        };

         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         //adapter.notifyDataSetChanged();
         recyclerView.setAdapter(adapter);


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

//        Query query = ticketRef.orderBy("Tickets");
//        FirestoreRecyclerOptions<Ticket> options = new FirestoreRecyclerOptions.Builder<Ticket>()
//               // .setQuery(query, Ticket.class)
//                .setQuery(query, Ticket.class)
//                .build();
//        adapter = new TicketAdapter(options);
//        recyclerView = (RecyclerView)findViewById(R.id.recycle);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);


        //go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,TicketActivity.class);
                startActivity(intent);
            }
        });
    }


    private class TicketViewHolder  extends RecyclerView.ViewHolder{
        private TextView prices;
        private TextView TimeDeparture;
        private TextView TimeArrival;
        private TextView theclassdb;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            prices = itemView.findViewById(R.id.prices);
            TimeDeparture = itemView.findViewById(R.id.TimeDeparture);
            TimeArrival = itemView.findViewById(R.id.TimeArrival);
            theclassdb = itemView.findViewById(R.id.theclassdb);

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}


