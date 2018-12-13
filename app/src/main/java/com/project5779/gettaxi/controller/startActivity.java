/**
 * Project in Java- Android.
 * Writers - Tirtza Raaya Rubinstain && Chana Drori
 * 12/2018
 * the class startActivity is Stam.
 */

package com.project5779.gettaxi.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project5779.gettaxi.MainActivity;
import com.project5779.gettaxi.R;

public class startActivity extends Activity {

    /**
     * the function create a new start activity.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            /**
             * the function listener to click on the button.
             * @param v View the start button
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
