package com.example.demo;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterTicket extends RecyclerView.Adapter< RecyclerViewAdapterTicket.TicketViewHolder> {

    ArrayList<TicketItem> ticketItemsList;
    Context context;
   private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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
        TicketViewHolder viewHolder = new TicketViewHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.date1.setText(ticketItemsList.get(position).getDate1());
        holder.date2.setText(ticketItemsList.get(position).getdate2());
        holder.statut.setText(ticketItemsList.get(position).getStatus());
        holder.mealType.setText(ticketItemsList.get(position).getMealType());

        // supression du ticket

       /*holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date2,mealType;
                date2 = ticketItemsList.get(position).getdate2();
                mealType = ticketItemsList.get(position).getMealType();
                Intent intent = new Intent(context, DeleteTicketActivity.class);
                intent.putExtra("date2", date2);
                intent.putExtra("mealType", mealType);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return ticketItemsList.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView date1, date2, statut, mealType, delete;

        public TicketViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            date1 = itemView.findViewById(R.id.date1);
            date2 = itemView.findViewById(R.id.date2);
            statut = itemView.findViewById(R.id.statut);
            mealType = itemView.findViewById(R.id.meal);
            delete = itemView.findViewById(R.id.delete);

            // suppression du ticket
           delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            String date2,mealType;
                            date2 = ticketItemsList.get(position).getdate2();
                            mealType = ticketItemsList.get(position).getMealType();
                            Intent intent = new Intent(context, DeleteTicketActivity.class);
                            intent.putExtra("date2", date2);
                            intent.putExtra("mealType", mealType);
                            intent.putExtra("position", position);
                            context.startActivity(intent);

                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }


        }
    }

