package com.project5779.gettaxi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b2 =(Button) findViewById(R.id.button2);
        b2.setBackgroundColor(Color.YELLOW);

        Button b3 =(Button) findViewById(R.id.button3);
        b3.setBackgroundColor(Color.YELLOW);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new activity
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new activity
            }
        });

    }
}
