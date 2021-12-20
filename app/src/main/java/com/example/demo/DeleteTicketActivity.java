package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DeleteTicketActivity extends AppCompatActivity {

    Button valider;
    Button annuler;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ticket);

        valider = findViewById(R.id.valider);
        annuler = findViewById(R.id.annuler);
        db = FirebaseFirestore.getInstance();

        String date2 = getIntent().getStringExtra("date2");
        String mealType = getIntent().getStringExtra("mealType");

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] documentId  = new String[1];


                // recherche de l'id du document
                db.collection("Tickets").document((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .collection("Ticket").whereEqualTo("date2", date2)
                        .whereEqualTo("mealType", mealType).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<DocumentChange> documentChange  = task.getResult().getDocumentChanges();

                            documentId[0] = documentChange.get(0).getDocument().getId();

                            db.collection("Tickets").document((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .collection("Ticket").document(documentId[0])
                                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(DeleteTicketActivity.this,"Ticket sucessfully deleted",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DeleteTicketActivity.this,"Error deleting ticket",Toast.LENGTH_SHORT).show();
                                    Log.d("errorrr", "Error deleting tickect");

                                }
                            });
                        }
                    }
                });
                /*Intent intent = new Intent(v.getContext(), ShowTicket.class);
                v.getContext().startActivity(intent);*/
                finish();

            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}