package com.example.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static final String Tag = "RecyclerViewAdapter";
    private ArrayList<DayList> mButtons = new ArrayList<>();
    private Context mContext;
    private ItemClickListner mItemListner;

    public Adapter(ArrayList<DayList> mButtons,Context mContext,ItemClickListner itemClickListner) {
        this.mButtons = mButtons;
        this.mContext = mContext;

        this.mItemListner=itemClickListner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(Tag, "onCreateViewHolder: called.");

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Log.d(Tag,"onCreateViewHolder: called.");
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Tag,"onClicked on"+mButtons.get(position));
                mItemListner.onItemClick(mButtons.get(position));
                Intent intent = new Intent(mContext,Menu2.class);
                intent.putExtra("username",mButtons.get(position).getJour());
                mContext.startActivity(intent);
            }
        });
        holder.name.setText(mButtons.get(position).getJour());

    }

    @Override
    public int getItemCount() {
        return mButtons.size();
    }
    public interface ItemClickListner{
        void onItemClick(DayList dayList);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button name;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }

    }
}
