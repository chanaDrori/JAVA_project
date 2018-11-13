package com.project5779.gettaxi;

import android.content.ContentValues;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project5779.gettaxi.model.backend.BackendFactory;
import com.project5779.gettaxi.model.backend.DBmanager;
import com.project5779.gettaxi.model.backend.TaxiConst;
import com.project5779.gettaxi.model.entities.Drive;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    @Override
    public void onClick(View v) {
        if (v == AddButton)
            addDrive();
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

        if(s.toString().trim().length()==0){
            AddButton.setEnabled(false);
        } else {
            AddButton.setEnabled(true);
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


    private void addDrive()
    {
        final ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(TaxiConst.DriveConst.NAME, this.NameEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.PHONE , this.PhoneEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.EMAIL , this.EmailEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.START_POINT , this.StartPointEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.END_POINT , this.EndPointEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.START_TIME , this.StartTimeEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.END_TIME , this.EndTimeEditText.getText().toString());
            contentValues.put(TaxiConst.DriveConst.STATE , this.StateSpinner.getSelectedItem().toString());

           // dBmanager.addNewDrive();
            BackendFactory.getInstance(this).addNewDrive(contentValues);

        }
        catch (Exception exp)
        {

        }
    }
}
