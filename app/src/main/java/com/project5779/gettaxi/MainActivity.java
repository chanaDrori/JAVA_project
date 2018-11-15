package com.project5779.gettaxi;

import android.content.ContentValues;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.backend.BackendFactory;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.entities.Drive;
import com.project5779.gettaxi.model.entities.StateOfDrive;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

   // DBmanager dBmanager = BackendFactory.getInstance(this);
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
     *
     * 
     */
    private void findViews()
    {
        NameEditText = (EditText)findViewById(R.id.name);
        EmailEditText = (EditText)findViewById(R.id.email);
        PhoneEditText = (EditText)findViewById(R.id.phone);
        StartPointEditText = (EditText)findViewById(R.id.startPoint);
        EndPointEditText = (EditText)findViewById(R.id.endPoint);
        StartTimeEditText = (EditText)findViewById(R.id.startTime);
        EndTimeEditText = (EditText)findViewById(R.id.endTime);
        StateSpinner = (Spinner)findViewById(R.id.state);
        AddButton =(Button) findViewById(R.id.button2);

        AddButton.setOnClickListener(this);

        NameEditText.addTextChangedListener(this);
        PhoneEditText.addTextChangedListener(this);
        StartPointEditText.addTextChangedListener(this);

        StateSpinner.setAdapter(new ArrayAdapter<StateOfDrive>(this, android.R.layout.simple_spinner_item, StateOfDrive.values()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    /**
     * the function add new drive whene the user click on the button
     * @param v View Button
     */
    @Override
    public void onClick(View v) {
        if (v == AddButton)
            addDrive();// add new drive to the database
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        //if there are strings on the fields: name & phone & start-point , set the button.enable to true
        //else- set Button.enable to false
        if(NameEditText.toString().trim().length() !=0 && PhoneEditText.toString().trim().length() !=0
                &&StartPointEditText.toString().trim().length() !=0){
            AddButton.setEnabled(true);
        } else {
            AddButton.setEnabled(false);
        }
    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use  in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {

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
