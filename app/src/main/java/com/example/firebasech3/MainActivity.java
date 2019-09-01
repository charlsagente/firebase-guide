package com.example.firebasech3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView viewText;
    Button writeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("portfolios");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<StockPortfolio> list = new ArrayList<StockPortfolio>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    list.add(child.getValue(StockPortfolio.class));
                }
                String pName = "";
                if (list.size() > 0) {
                    pName = list.get(0).portfolioName;
                }
                viewText.setText(pName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Fail;ed to read the value
                Log.w("Ch3", "Failed to read the value.", databaseError.toException());
            }
        });


        writeButton = (Button) findViewById(R.id.writeButton);
        viewText = (TextView) findViewById(R.id.textView);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //myRef.setValue("Hello World!");
                ArrayList<StockPortfolio> myFolios = new ArrayList<StockPortfolio>();
                StockPortfolio myFolio = new StockPortfolio("demoFolio", "lmonredy", "lm@hotmail.com");

                ArrayList<Stock> myFolioHoldings = new ArrayList<Stock>();
                myFolioHoldings.add(new Stock("GOO", "google", 100));
                myFolioHoldings.add(new Stock("APPL", "manzaa", 1000));
                myFolioHoldings.add(new Stock("MTS", "Mocosoft", 100));
                myFolio.portfolioHoldings = myFolioHoldings;
                myRef.child("1stChild").setValue(myFolio);

                StockPortfolio myOtherFolio = new StockPortfolio("realFolio", "lmonrer", "lmwork@htomail.com");

                ArrayList<Stock> myOtherFolioHoldings = new ArrayList<Stock>();
                myOtherFolioHoldings.add(new Stock("IBM", "IB<", 20));
                myOtherFolioHoldings.add(new Stock("OPc", "ddd<", 200));
                myOtherFolio.portfolioHoldings = myOtherFolioHoldings;
                myRef.child("2ndChild").setValue(myOtherFolio);


            }
        });


    }
}
