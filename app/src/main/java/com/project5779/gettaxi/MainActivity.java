package com.project5779.gettaxi;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.backend.BackendFactory;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.datasource.DatabaseFirebase;
import com.project5779.gettaxi.model.entities.Drive;
import com.project5779.gettaxi.model.entities.StateOfDrive;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText EmailEditText;
    private EditText PhoneEditText;
    private EditText StartPointEditText;
    private EditText EndPointEditText;
    private EditText StartTimeEditText;
    private EditText EndTimeEditText;
    private Spinner StateSpinner;
    private Button AddButton;

    /**
     *The function finds all the objects "View" from this Activity
     */
    private void findViews() {
        NameEditText = (EditText) findViewById(R.id.name);
        EmailEditText = (EditText) findViewById(R.id.email);
        PhoneEditText = (EditText) findViewById(R.id.phone);
        StartPointEditText = (EditText) findViewById(R.id.startPoint);
        EndPointEditText = (EditText) findViewById(R.id.endPoint);
        StartTimeEditText = (EditText) findViewById(R.id.startTime);
        EndTimeEditText = (EditText) findViewById(R.id.endTime);
        StateSpinner = (Spinner) findViewById(R.id.state);
        AddButton = (Button) findViewById(R.id.button2);

        StateSpinner.setSelection(0);
        /**
         *Creator listener to controls - EndTimeEditText, StartTimeEditText
         */
        OnClickListener timeP = new OnClickListener() {
            /**
             * The function triggers TimePickerDialog when the user click on the match EditText
             * @param v View - Edit Text that clicked.
             */
            @Override
            public void onClick(final View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    /**
                     * set the string of the time in the editText
                     * @param timePicker
                     * @param selectedHour int. the hour now.
                     * @param selectedMinute int. the minute now.
                     */
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (v == StartTimeEditText){ StartTimeEditText.setText(selectedHour + ":" + selectedMinute);}
                        else  {EndTimeEditText.setText(selectedHour + ":" + selectedMinute);}
                    }
                }, hour, minute, true);
                if (v == StartTimeEditText){
                    mTimePicker.setTitle("Set beginning time:");
                }
                else
                {
                    mTimePicker.setTitle("Set end time:");
                }
                mTimePicker.show();
            }
        };
        StartTimeEditText.setOnClickListener(timeP);
        EndTimeEditText.setOnClickListener(timeP);

        AddButton.setOnClickListener(new OnClickListener() {
            /**
             * the function add new drive when the user click on the button
             *
             * @param v View Button
             */
            @Override
            public void onClick(View v) {
                if (v == AddButton) {
                    addDrive();// add new drive to the database
                }
            }
        }); //add this activity to the Listeners of Click on AddButton.

        TextWatcher TW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                //if there are strings on the fields: name & phone & start-point , set the button.enable to true
                //else- set Button.enable to false
                if(NameEditText.getText().toString().trim().length() == 0||
                        PhoneEditText.getText().toString().trim().length() == 0||
                        StartPointEditText.getText().toString().trim().length() == 0 )
                    AddButton.setEnabled(false);
                else
                    AddButton.setEnabled(true);

               /* if (s == EmailEditText)
                {
                    String target = EmailEditText.getText().toString();
                    try{
                    if (TextUtils.isEmpty(target)) {

                    }
                    else if (! android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
                    {

                    }}
                    catch (Exception exp)
                    {

                    }
                }*/
            }
        };
        NameEditText.addTextChangedListener(TW);
        PhoneEditText.addTextChangedListener(TW);
        StartPointEditText.addTextChangedListener(TW);
        EmailEditText.addTextChangedListener(TW);

        StateSpinner.setAdapter(new ArrayAdapter<StateOfDrive>(this, android.R.layout.simple_spinner_item, StateOfDrive.values()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }


    /**
     * the function add a new drive from the UI to the database according to the BackendFactory
     */
    private void addDrive()
    {
        //create a new instance of contentValues
        final ContentValues contentValues = new ContentValues();

        try {
            //put the value from the UI to contentValues
            contentValues.put(TaxiConst.DriveConst.NAME, this.NameEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.PHONE , this.PhoneEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.EMAIL , this.EmailEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.START_POINT , this.StartPointEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.END_POINT , this.EndPointEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.START_TIME , this.StartTimeEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.END_TIME , this.EndTimeEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.STATE , this.StateSpinner.getSelectedItem().toString());

            try {
                // Getting an instance of the backend using the Function Factory adds a new drive
                   BackendFactory.getInstance(this).addNewDrive(contentValues);
               /* BackendFactory.getInstance(this).addNewDrive(contentValues, new DatabaseFirebase.Action<Long>() {
                    @Override
                    public void onSuccess(Long obj) {
                        Toast.makeText(getBaseContext(), "insert the drive", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(getBaseContext(), "error: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(String status, double percent) {
                        if (percent != 100)
                            AddButton.setEnabled(false);
                    }

                });*/

            }
            catch (Exception e)
            {
                Toast.makeText(getBaseContext(), "Error add to fireBase", Toast.LENGTH_LONG).show();
            }


            Toast.makeText(getApplication(), "Your drive has been added successfully", Toast.LENGTH_SHORT).show();
            // Initialize all the fields
            this.NameEditText.setText("");
            this.PhoneEditText.setText("");
            this.EmailEditText.setText("");
            this.StartTimeEditText.setText("");
            this.EndTimeEditText.setText("");
            this.StartPointEditText.setText("");
            this.EndPointEditText.setText("");
            StateSpinner.setSelection(0);
            this.AddButton.setEnabled(false);

        }
        catch (Exception exp)
        {

        }
    }
}
