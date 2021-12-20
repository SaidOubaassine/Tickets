package com.example.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterTicket extends RecyclerView.Adapter< RecyclerViewAdapterTicket.TicketViewHolder> {

    ArrayList<TicketItem> ticketItemsList;
    ArrayList<String> day;
    Context context;

    public RecyclerViewAdapterTicket(ArrayList<TicketItem> ticketItemsList) {
        this.ticketItemsList = ticketItemsList;
    }


    public void setTickets(ArrayList<TicketItem> ticketItemsList) {
        this.ticketItemsList = ticketItemsList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        TicketViewHolder viewHolder = new TicketViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.date1.setText(ticketItemsList.get(position).getDate1());
        holder.date2.setText(ticketItemsList.get(position).getdate2());
        holder.statut.setText(ticketItemsList.get(position).getStatus());
        holder.mealType.setText(ticketItemsList.get(position).getMealType());
    }

    @Override
    public int getItemCount() {
        return ticketItemsList.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView date1, date2, statut, mealType;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            date1 = itemView.findViewById(R.id.date1);
            date2 = itemView.findViewById(R.id.date2);
            statut = itemView.findViewById(R.id.statut);
            mealType = itemView.findViewById(R.id.meal);
        }
    }
}



