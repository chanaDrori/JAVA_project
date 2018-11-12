package com.project5779.gettaxi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.backend.BackendFactory;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.entities.Drive;

public class MainActivity extends AppCompatActivity {

    DBmanager dBmanager = BackendFactory.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button b2 =(Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dBmanager.addNewDrive();
                }
            });

    }
}
