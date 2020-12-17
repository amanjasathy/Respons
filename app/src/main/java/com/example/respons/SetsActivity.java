package com.example.respons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.respons.SplashActivity.catList;
import static com.example.respons.SplashActivity.selected_cat_index;

public class SetsActivity extends AppCompatActivity {

    private GridView setsGrid;
    private FirebaseFirestore firestore;
  public static List<String> setsIDs=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar=findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);

       // String title=getIntent().getStringExtra("CATEGORY");
       //  category_id=getIntent().getIntExtra("CATEGORY_ID",1);
        getSupportActionBar().setTitle(catList.get(selected_cat_index).getName());

        setsGrid=findViewById(R.id.sets_gridView);
        firestore=FirebaseFirestore.getInstance();
        loadSets();





    }
    public void loadSets(){
       setsIDs.clear();
        firestore.collection("QUIZ").document(catList.get(selected_cat_index).getId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                long noOfSets = (long)documentSnapshot.get("SETS");

                for(int i=1; i <= noOfSets; i++)
                {
                    setsIDs.add(documentSnapshot.getString("SET" + String.valueOf(i) + "_ID"));
                }


                SetsAdapter adapter=new SetsAdapter(setsIDs.size());
                setsGrid.setAdapter(adapter);




            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SetsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });






    }
}