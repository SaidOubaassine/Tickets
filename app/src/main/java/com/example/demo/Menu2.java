package com.example.demo;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Menu2 extends AppCompatActivity {
    int dayOfWeek;
    Map<Integer,LocalDate> week;
    LocalDate localDate;
    TextView nameMenu;
    TextView date;
    TextView lunchPlatEntree;
    TextView lunchPlatPrincipal;
    TextView lunchDissert;
    TextView dinnerPlatEntree;
    TextView dinnerPlatPrincipal;
    TextView dinnerDissert;
    TextView lunchMeal;
    TextView dinnerMeal;
    Button buy_lunch, buy_dinner;
    FirebaseFirestore db;
    Map<String,String> contenu;
    Menu menu;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        menu=new Menu();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        nameMenu =findViewById(R.id.jour);
        String username ="Username not set";
        Bundle extras =getIntent().getExtras();
        if (extras != null){
            username=extras.getString("username");
        }
        //setting the date
        date=findViewById(R.id.date);
        localDate= LocalDate.now();
        week=getNumDate(localDate);
        dayOfWeek=getDayNum(username);
        date.setText(week.get(dayOfWeek).toString());
        //setting the elements of menu
        lunchDissert=findViewById(R.id.lunch_dissert);
        lunchPlatEntree=findViewById(R.id.lunch_plat_entree);
        lunchPlatPrincipal=findViewById(R.id.lunch_plat_principale);
        dinnerDissert=findViewById(R.id.dinner_dissert);
        dinnerPlatEntree=findViewById(R.id.dinner_plat_entree);
        dinnerPlatPrincipal=findViewById(R.id.dinner_plat_principale);
        lunchMeal = findViewById(R.id.lunchMeal);
        dinnerMeal = findViewById(R.id.dinnerMeal);
        buy_lunch = findViewById(R.id.buy_lunch);
        buy_dinner = findViewById(R.id.buy_dinner);



        db=FirebaseFirestore.getInstance();

        nameMenu.setText(username);

        db.collection("Menu").whereEqualTo("jour", username).whereEqualTo("repas", "lunch").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType() ==DocumentChange.Type.ADDED){
                        menu=dc.getDocument().toObject(Menu.class);
                        contenu=menu.getContenu();
                        lunchPlatEntree.setText(contenu.get("entree"));
                        lunchPlatPrincipal.setText(contenu.get("principal"));
                        lunchDissert.setText(contenu.get("dissert"));
                    }
                }
            }
        });
        db.collection("Menu").whereEqualTo("jour", username).whereEqualTo("repas", "diner").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType() ==DocumentChange.Type.ADDED){
                        menu=dc.getDocument().toObject(Menu.class);
                        contenu= menu.getContenu();
                        dinnerPlatEntree.setText(contenu.get("entree"));
                        dinnerPlatPrincipal.setText(contenu.get("principal"));
                        dinnerDissert.setText(contenu.get("dissert"));
                    }
                }
            }
        });

        // ajout du ticket lunch
        buy_lunch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String date1 = (LocalDate.now()).toString();
                String date2 = (String) date.getText();
                String mealType = (String) lunchMeal.getText();
                TicketItem ticket = new TicketItem(date1, date2, "Non consommé", mealType);

                db.collection("Tickets").document((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .collection("Ticket").whereEqualTo("date2", date2)
                        .whereEqualTo("mealType", mealType).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            if((task.getResult()).isEmpty()){
                                db.collection("Tickets").document((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .collection("Ticket").add(ticket).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(@NonNull DocumentReference documentReference) {
                                        Toast.makeText(Menu2.this,"Registration successfully completed",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Menu2.this,"Error adding " + e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }else{
                                Toast.makeText(Menu2.this,"Already ticket exist",Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });

        // ajout du ticket dinner
        buy_dinner.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String date1 = (LocalDate.now()).toString();
                String date2 = (String) date.getText();
                String mealType = (String) dinnerMeal.getText();
                TicketItem ticket = new TicketItem(date1, date2, "Non consommé", mealType);

                db.collection("Tickets").document((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .collection("Ticket").whereEqualTo("date2", date2)
                        .whereEqualTo("mealType", mealType).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            if((task.getResult()).isEmpty()){
                                db.collection("Tickets").document((FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .collection("Ticket").add(ticket).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(@NonNull DocumentReference documentReference) {
                                        Toast.makeText(Menu2.this,"Registration successfully completed",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Menu2.this,"Error adding " + e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }else{
                                Toast.makeText(Menu2.this,"Already ticket exist",Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });

            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    Map<Integer, LocalDate> getNumDate(LocalDate dt){

        int num = (dt.getDayOfWeek()).getValue();
        int day = dt.getDayOfMonth();

        Map<Integer, LocalDate> dayNumber = new HashMap<Integer, LocalDate>();

        for(int i = 1,j = 1; i < 7; i++){

            if(num == 7){
                LocalDate dt2 = dt.plusDays(j);
                dayNumber.put(i, dt2);
                j++;
                continue;
            }

            if(num == 6){
                LocalDate dt2 = dt.plusDays(j+1);
                dayNumber.put(i, dt2);
                j++;
                continue;
            }

            if(num > i){

                LocalDate dt2 = dt.minusDays(num - i);
                dayNumber.put(i, dt2);
            }
            else if(num == i){
                dayNumber.put(i,dt);
                continue;
            }
            else{
                LocalDate dt2 = dt.plusDays(j);
                dayNumber.put(i, dt2);
                j++;
                continue;
            }
        }

        return dayNumber;
    }
    public int getDayNum(String jour){
        if(jour.equals("lundi")){
            return 1;
        }else
        if(jour.equals("mardi")){
            return 2;
        }else
        if(jour.equals("mercredi")){
            return 3;
        }else
        if(jour.equals("jeudi")){
            return 4;
        }else
        if(jour.equals("vendredi")){
            return 5;
        }else
        if(jour.equals("samedi")){
            return 6;
        }
        else
        {
            return 7;
        }
    }
}