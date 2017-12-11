package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TestInfoActivity extends AppCompatActivity implements OnFocusChangeListener, OnItemSelectedListener{

    List<String> testList, patientList;
    ArrayAdapter<String> testAdapter, patientAdapter;
    Spinner test, patient;
    String selectedTest, selectedPatient, userType;
    EditText bpl, bph, temp, otherDetails;
    Button addTest;
    DatabaseHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info);

        myDatabase = new DatabaseHelper(this);

        // get the selected cuisine type from previous screen
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");

        test = (Spinner) findViewById(R.id.spinnerTestNames);
        patient = (Spinner) findViewById(R.id.spinnerPatientNames);

        bpl = (EditText) findViewById(R.id.editBPL);
        bph = (EditText) findViewById(R.id.editBPH);
        temp = (EditText) findViewById(R.id.editTemperature);
        otherDetails = (EditText) findViewById(R.id.editDetails);
        addTest = (Button) findViewById(R.id.btnAddTest);

        testList = new ArrayList<String>();
        testList.add("Blood Test");
        testList.add("Breath Test");
        testList.add("Biopsy Test");
        testList.add("MRI Test");
        testList.add("Cancer Gene Test");
        testList.add("Other Test");

        testAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, testList);
        testAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testAdapter.notifyDataSetChanged();
        test.setAdapter(testAdapter);

        test.setOnItemSelectedListener(this);

        // import patient table data to the patient spinner
        patientList = new ArrayList<String>();

        Cursor result = myDatabase.getAllData("Patient");

        if(result.getCount() != 0){
            // moves row by row of the table data
            while (result.moveToNext()){
                patientList.add(result.getString(1));
            }
        }

        patientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientList);
        patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientAdapter.notifyDataSetChanged();
        patient.setAdapter(patientAdapter);

        patient.setOnItemSelectedListener(this);

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        validateUserInfo();
    }

    public boolean validateUserInfo(){

        if( bpl.getText().toString().length() == 0 ){
            bpl.setError( "Valid BPL is required!" );
            return false;
        }
        else if( bph.getText().toString().length() == 0 ){
            bph.setError( "Valid BPH is required!" );
            return false;
        }
        else if( temp.getText().toString().length() == 0 ){
            temp.setError( "Valid temperature is required!" );
            return false;
        }

        return  true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // get the selected test name from spinner
        selectedTest = String.valueOf(test.getSelectedItem());
        selectedPatient = String.valueOf(patient.getSelectedItem());
    }

    public void addNewTest(View view){

        boolean isInserted = false;
        int patientId = 0;
        // get the doctor id from database according to selected doctor from spinner
        Cursor resId = myDatabase.getDoctorOrPatientId("Patient", selectedPatient);
        while (resId.moveToNext()) {
            patientId = Integer.parseInt(resId.getString(0));
        }

        if(validateUserInfo()) {

            isInserted = myDatabase.insertTestData(
                    selectedTest,
                    patientId,
                    bpl.getText().toString(),
                    bph.getText().toString(),
                    temp.getText().toString(),
                    otherDetails.getText().toString());

            if(isInserted == true){
                Toast.makeText(this, "Test details are successfully added", Toast.LENGTH_SHORT).show();
                // control moves to Dashboard screen
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.putExtra("checked", userType);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "Something is wrong! Please try again", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
