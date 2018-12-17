/**
 * Project in Java- Android.
 * Writers - Tirtza Raaya Rubinstain && Chana Drori
 * 12/2018
 *Main Activity code. this file adding a new drive to the BackEnd.
 */

package com.project5779.gettaxi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project5779.gettaxi.model.backend.BackendFactory;
import com.project5779.gettaxi.model.datasource.DatabaseFirebase;
import com.project5779.gettaxi.model.entities.Drive;

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
     * The function finds all the objects "View" from this Activity
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


        //Creator listener to controls - EndTimeEditText, StartTimeEditText
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
                     * @param timePicker TimePicker.
                     * @param selectedHour int. the hour now.
                     * @param selectedMinute int. the minute now.
                     */
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (v == StartTimeEditText) {
                            StartTimeEditText.setText(selectedHour + ":" + selectedMinute);
                        }
                        // else  {EndTimeEditText.setText(selectedHour + ":" + selectedMinute);}
                    }
                }, hour, minute, true);
                if (v == StartTimeEditText) {
                    mTimePicker.setTitle(getString(R.string.setStartTime));
                } else {
                    mTimePicker.setTitle(getString(R.string.SetEndTime));
                }
                mTimePicker.show();
            }
        };
        StartTimeEditText.setOnClickListener(timeP);
        // EndTimeEditText.setOnClickListener(timeP);

        //add this activity to the Listeners of Click on AddButton.
        AddButton.setOnClickListener(new OnClickListener() {
            /**
             * the function add new drive when the user click on the button
             * @param v View Button
             */
            @Override
            public void onClick(View v) {
                if (v == AddButton) {
                    addDrive();// add new drive to the database
                }
            }
        });

        //the function watcher on text changed.
        TextWatcher TW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * the function watcher on text changed.
             * if there are strings on the fields: name & phone & start-point , set the button.enable to true
             * else- set Button.enable to false
             * @param s Editable
             */
            @Override
            public void afterTextChanged(Editable s) {
                if (NameEditText.getText().toString().trim().length() == 0 ||
                        PhoneEditText.getText().toString().trim().length() == 0 ||
                        StartPointEditText.getText().toString().trim().length() == 0||
                        StartTimeEditText.getText().toString().trim().length() == 0)
                    AddButton.setEnabled(false);
                else
                    AddButton.setEnabled(true);

                //check if string of the phone is valid.
                boolean isValidPhone = true;
                String phone = PhoneEditText.getText().toString();
                if (isEmpty(phone) || phone.length() < 9)
                    isValidPhone = false;
                if (phone.contains("."))
                    isValidPhone = false;

                //check if string of the Email is valid.
                boolean isValidEmail = true;
                String email = EmailEditText.getText().toString();
                if (isEmpty(email) || email.length() < 5)
                    isValidEmail = false;
                int atSign = email.indexOf('@');
                if (atSign == -1 || atSign != email.lastIndexOf('@') ||
                        atSign == 0 || atSign == email.length() - 1 || email.contains("\""))
                    isValidEmail = false;
                int dotSign = email.indexOf('.', atSign);
                if (dotSign == -1 || dotSign == 0 || dotSign == email.length() - 1
                        || dotSign - atSign < 2)
                    isValidEmail = false;

                validationEnable(isValidPhone, PhoneEditText);
                validationEnable(isValidEmail, EmailEditText);
                validationEnable(isValidAddress(StartPointEditText.getText().toString()), StartPointEditText);
                validationEnable(isValidAddress(EndPointEditText.getText().toString()), EndPointEditText);
            }
        };
        NameEditText.addTextChangedListener(TW);
        PhoneEditText.addTextChangedListener(TW);
        StartPointEditText.addTextChangedListener(TW);
        EmailEditText.addTextChangedListener(TW);
        EndPointEditText.addTextChangedListener(TW);
        StartTimeEditText.addTextChangedListener(TW);

        // StateSpinner.setAdapter(new ArrayAdapter<StateOfDrive>(this, android.R.layout.simple_spinner_item, StateOfDrive.values()));
    }

    /**
     * check if string of the Address is valid.
     * @param add String. the address.
     * @return isValidAddress. true if the address is valid.
     */
    public Boolean isValidAddress(String add)
    {
        boolean isValidAddress = true;
        int comma1 = add.indexOf(',');
        int comma2 = add.lastIndexOf(',');
        if (comma1 == -1 || comma1 == 0 || comma2 == -1 ||
                comma2 - comma1 < 2 || comma2 == add.length() - 1)
            isValidAddress = false;
        return isValidAddress;
    }

    /**
     * the function show to the user if the input is valid and enabled the add button
     * @param valid Boolean. describe if the input is valid.
     * @param editText EditText. the source of the input.
     */
    public void validationEnable(Boolean valid, EditText editText) {
        if (!valid) {
            AddButton.setEnabled(false);
            editText.setTextColor(getResources().getColor(R.color.red));
        } else {
            editText.setTextColor(getResources().getColor(R.color.black));
        }
    }

    /**
     * the function create a new main activity.
     * @param savedInstanceState Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    /**
     * the function convert string to location
     *
     * @param str string. address.
     * @return Location. the location of the string that accepted.
     * @throws Exception .
     */
    public Location StringToLocation(String str) throws Exception {
        Geocoder gc = new Geocoder(this);
        //if (gc.isPresent()) {
            List<Address> list = gc.getFromLocationName("155 Park Theater, Palo Alto, CA", 1);
            Address address = list.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();

            Location locationStart = new Location(str);
            locationStart.setLatitude(lat);
            locationStart.setLongitude(lng);
            return locationStart;
      //  } else throw new Exception();
    }

    /**
     * the function add a new drive from the UI to the database according to the BackendFactory
     */
    private void addDrive() {
        //create a new instance of drive
        try {
            Drive drive = new Drive();
            drive.setNameClient(this.NameEditText.getText().toString());
            drive.setPhoneClient(this.PhoneEditText.getText().toString());
            drive.setEmailClient(this.EmailEditText.getText().toString());
            drive.setStartTime(this.StartTimeEditText.getText().toString());
            //drive.setEndTime("");
            drive.setStartPointString(this.StartPointEditText.getText().toString());
            drive.setEndPointString(this.EndPointEditText.getText().toString());
            // drive.setState(StateOfDrive.valueOf(this.StateSpinner.getSelectedItem().toString()));

            //drive.setStartPoint(StringToLocation(this.StartPointEditText.getText().toString()));
            //drive.setEndPoint(StringToLocation(this.EndPointEditText.getText().toString()));

            // Getting an instance of the backend using the Function Factory adds a new drive
            //implement the interface from the file DatabaseFirebase
            BackendFactory.getInstance(this).addNewDrive(drive, new DatabaseFirebase.Action<String>() {
                @Override
                public void onSuccess(String obj) {
                    //successAddDrive();
                    Toast.makeText(getBaseContext(), R.string.successAddToFirebase, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getBaseContext(), R.string.ErrorAddToFireBase, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProgress(String status, double percent) {
                    if (percent != 100)
                        AddButton.setEnabled(false);
                }

            });
            init();
        } catch (Exception exp) { //if not succeeded add to the BackEnd.
            Toast.makeText(getBaseContext(), R.string.ErrorAddToFireBase, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initialize all the fields
     */
    public void init() {
        this.NameEditText.setText("");
        this.PhoneEditText.setText("");
        this.EmailEditText.setText("");
        this.StartTimeEditText.setText("");
        //this.EndTimeEditText.setText("");
        this.StartPointEditText.setText("");
        this.EndPointEditText.setText("");
        //StateSpinner.setSelection(0);
        this.AddButton.setEnabled(false);
    }
}
