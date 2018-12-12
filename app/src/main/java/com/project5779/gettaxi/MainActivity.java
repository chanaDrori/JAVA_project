package com.project5779.gettaxi;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText EmailEditText;
    private EditText PhoneEditText;
    private EditText StartPointEditText;
    private EditText EndPointEditText;
    private EditText StartTimeEditText;
   // private EditText EndTimeEditText;
    //private Spinner StateSpinner;
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
       // EndTimeEditText = (EditText) findViewById(R.id.endTime);
        //StateSpinner = (Spinner) findViewById(R.id.state);
        AddButton = (Button) findViewById(R.id.button2);

       // StateSpinner.setSelection(0);
       // StateSpinner.setEnabled(false);
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
                       // else  {EndTimeEditText.setText(selectedHour + ":" + selectedMinute);}
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
       // EndTimeEditText.setOnClickListener(timeP);

        AddButton.setOnClickListener(new OnClickListener() {
            /**
             * the function add new drive when the user click on the button
             *
             * @param v View Button
             */
            @Override
            public void onClick(View v) {
                if (v == AddButton) {
                    if (PhoneEditText.getText().toString().trim().length() < 9)
                        Toast.makeText(getBaseContext(), R.string.error_phone_9_digits, Toast.LENGTH_LONG).show();
                    else
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
                if (NameEditText.getText().toString().trim().length() == 0 ||
                        PhoneEditText.getText().toString().trim().length() == 0 ||
                        StartPointEditText.getText().toString().trim().length() == 0)
                    AddButton.setEnabled(false);
                else
                    AddButton.setEnabled(true);

                boolean isValidEmail = true;
                String email = EmailEditText.getText().toString();
                if (isEmpty(email) || email.length() < 5)
                    isValidEmail = false;
                ///add valid string
                int atSign = email.indexOf('@');
                if (atSign == -1 || atSign != email.lastIndexOf('@') ||
                        atSign == 0 || atSign == email.length() - 1 || email.contains("\""))
                    isValidEmail = false;

                int dotSign = email.indexOf('.', atSign);
                if (dotSign == -1 || dotSign == 0 || dotSign == email.length() - 1
                        || dotSign - atSign < 2)
                    isValidEmail = false;

                if (isValidEmail == false) {
                    AddButton.setEnabled(false);
                    EmailEditText.setTextColor(getResources().getColor(R.color.red));
                }
                else {
                    EmailEditText.setTextColor(getResources().getColor(R.color.black));
                }

            }
        };
        NameEditText.addTextChangedListener(TW);
        PhoneEditText.addTextChangedListener(TW);
        StartPointEditText.addTextChangedListener(TW);
        EmailEditText.addTextChangedListener(TW);

       // StateSpinner.setAdapter(new ArrayAdapter<StateOfDrive>(this, android.R.layout.simple_spinner_item, StateOfDrive.values()));
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
    private void addDrive() {
        //create a new instance of contentValues
        try {
            Drive drive = new Drive();
            drive.setNameClient(this.NameEditText.getText().toString());
            drive.setPhoneClient(this.PhoneEditText.getText().toString());
            drive.setEmailClient(this.EmailEditText.getText().toString());
            drive.setStartTime(this.StartTimeEditText.getText().toString());
            drive.setEndTime("");
            //  drive.setState(StateOfDrive.valueOf(this.StateSpinner.getSelectedItem().toString()));

            Geocoder gc = new Geocoder(getBaseContext());
            if (gc.isPresent()) {
                List<Address> list = gc.getFromLocationName(this.StartPointEditText.getText().toString(), 1);
                Address address = list.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();

                Location locationStart = new Location(this.StartPointEditText.getText().toString());
                locationStart.setLatitude(lat);
                locationStart.setLongitude(lng);

                drive.setStartPoint(locationStart);
            }
            Geocoder gc2 = new Geocoder(getBaseContext());
            if (gc2.isPresent()) {
                List<Address> list = gc2.getFromLocationName(this.EndPointEditText.getText().toString(), 1);
                Address address = list.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();

                Location locationEnd = new Location(this.EndPointEditText.getText().toString());
                locationEnd.setLatitude(lat);
                locationEnd.setLongitude(lng);

                drive.setEndPoint(locationEnd);
            }
            // Getting an instance of the backend using the Function Factory adds a new drive
            //BackendFactory.getInstance(this).addNewDrive(drive);

            BackendFactory.getInstance(this).addNewDrive(drive, new DatabaseFirebase.Action<String>() {
                @Override
                public void onSuccess(String obj) {
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

            });

            // Initialize all the fields
            this.NameEditText.setText("");
            this.PhoneEditText.setText("");
            this.EmailEditText.setText("");
            this.StartTimeEditText.setText("");
            //this.EndTimeEditText.setText("");
            this.StartPointEditText.setText("");
            this.EndPointEditText.setText("");
            //StateSpinner.setSelection(0);
            this.AddButton.setEnabled(false);
            Toast.makeText(getApplication(), R.string.successAddToFirebase, Toast.LENGTH_SHORT).show();
        }catch (Exception exp) {
            Toast.makeText(getBaseContext(), R.string.ErrorAddToFireBase, Toast.LENGTH_LONG).show();
        }
    }
}
