package com.example.etickett;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.etickett.Model.Ticket;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TicketAdapter extends FirestoreRecyclerAdapter<Ticket, TicketAdapter.TicketHolder> {

    public TicketAdapter(@NonNull FirestoreRecyclerOptions<Ticket> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TicketHolder ticketHolder, int i, @NonNull Ticket ticket) {
        ticketHolder.theclassdb.setText(ticket.getdClasstype());
        ticketHolder.prices.setText(ticket.getPrice());
        ticketHolder.TimeDeparture.setText(ticket.getDepartureTime());
        ticketHolder.TimeArrival.setText(ticket.getArrivalTime());
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler,parent,false);
        return new TicketHolder(v);
    }

    class TicketHolder extends RecyclerView.ViewHolder{
        TextView theclassdb;
        TextView prices;
        TextView TimeDeparture;
        TextView TimeArrival;
        public TicketHolder(View itemView)
        {
            super(itemView);
            theclassdb = itemView.findViewById(R.id.theclassdb);
            prices = itemView.findViewById(R.id.prices);
            TimeDeparture = itemView.findViewById(R.id.TimeDeparture);
            TimeArrival = itemView.findViewById(R.id.TimeArrival);

        }
    }



}
