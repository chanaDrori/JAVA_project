package com.project5779.gettaxi;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.backend.BackendFactory;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
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
   // private TimePickerDialog
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

        OnClickListener timeP = new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
             * @param v View Button
             */
            @Override
            public void onClick(View v) {
                if (v == AddButton)
          /*  new AsyncTask<Void, Void, Long>() {
            @Override
               protected void onPostExecute(Long idResult)
                {
              super.onPostExecute(idResult);
                  if (idResult > 0)
                            Toast.makeText(getBaseContext(), "insert id: " + idResult, Toast.LENGTH_LONG).show();
                               }
                                @Override
                                  protected Long doInBackground(Void... params)
                  {
                return DBManagerFactory.getManager().addLecturer(contentValues);
                 } }.execute();*/

                    addDrive();// add new drive to the database
            }
        }
); //add this activity to the Listeners of Click on AddButton.

        TextWatcher TW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if there are strings on the fields: name & phone & start-point , set the button.enable to true
                //else- set Button.enable to false
                if(NameEditText.toString().trim().length() !=0 && PhoneEditText.toString().trim().length() !=0
                        &&StartPointEditText.toString().trim().length() !=0 && NameEditText.toString() != null
                        &&PhoneEditText.toString() != null &&StartPointEditText.toString() != null){
                    AddButton.setEnabled(true);
                } else {
                    AddButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        NameEditText.addTextChangedListener(TW);
        PhoneEditText.addTextChangedListener(TW);
        StartPointEditText.addTextChangedListener(TW);

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

            // Getting an instance of the backend using the Function Factory adds a new drive
            BackendFactory.getInstance(this).addNewDrive(contentValues);

            // Initialize all the fields
            this.NameEditText.setText(null);
            this.PhoneEditText.setText(null);
            this.EmailEditText.setText(null);
            this.StartTimeEditText.setText(null);
            this.EndTimeEditText.setText(null);
            this.StartPointEditText.setText(null);
            this.EndPointEditText.setText(null);
            this.AddButton.setEnabled(false);


        }
        catch (Exception exp)
        {

        }
    }
}
