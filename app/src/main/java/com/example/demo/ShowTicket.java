package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowTicket extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerViewAdapterTicket adapter;
    private FirebaseFirestore db;
    private ArrayList<TicketItem> ticketItems;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private String userId;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket);


        rv = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.progressbar);
        db = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        relativeLayout = findViewById(R.id.relativeLayout);

        ticketItems = new ArrayList<>();

        adapter = new RecyclerViewAdapterTicket(ticketItems);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rv.setLayoutManager(lm);
        EventChangeListener();
        rv.setAdapter(adapter);



    }

    private void EventChangeListener(){

        db.collection("Tickets").document(userId).collection("Ticket").orderBy("date2", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            Log.d("Firestore error ", error.getMessage());
                            return ;

                        }

                        progressBar.setVisibility(View.GONE);

                        for(DocumentChange dc : value.getDocumentChanges()){

                            if((dc.getType() == DocumentChange.Type.ADDED)){

                                ticketItems.add(dc.getDocument().toObject(TicketItem.class));
                            }if((dc.getType() == DocumentChange.Type.REMOVED));

                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}