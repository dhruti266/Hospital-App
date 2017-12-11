package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayTestInfoActivity extends AppCompatActivity implements OnItemSelectedListener{

    List<String> testList, patientList;
    ArrayAdapter<String> testAdapter, patientAdapter;
    Spinner test, patient;
    String selectedTest, selectedPatient;
    DatabaseHelper myDatabase;
    TextView testInfo;
    int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_test_info);

        myDatabase = new DatabaseHelper(this);

        test = (Spinner) findViewById(R.id.spinnerTestNames);
        patient = (Spinner) findViewById(R.id.spinnerPatientNames);

        testInfo = (TextView) findViewById(R.id.txtTestInfo);

        // import test table data to the test name spinner
        testList = new ArrayList<String>();

        Cursor result = myDatabase.getAllData("Test");

        if(result.getCount() != 0){
            // moves row by row of the table data
            while (result.moveToNext()){
                testList.add(result.getString(0));
            }
        }

        testAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, testList);
        testAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testAdapter.notifyDataSetChanged();
        test.setAdapter(testAdapter);

        test.setOnItemSelectedListener(this);

        patient.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                patientId = (int)id + 1;
                Log.d("hello", String.valueOf(patientId));
                showTestInfo(patientId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        selectedTest = String.valueOf(test.getSelectedItem());

        // import patient table data to the patient spinner
        patientList = new ArrayList<String>();

        // take Id of patient according to selected test from test table
        Cursor testResult = myDatabase.getPatientOrTestInfo("Test", selectedTest);
        Toast.makeText(this, selectedTest, Toast.LENGTH_SHORT).show();

        if(testResult.getCount() != 0){
            // moves row by row of the table data
            while (testResult.moveToNext()){
                // get patient name according to patient id of test table
                patientId = Integer.parseInt(testResult.getString(2));
                Cursor patientResult = myDatabase.getDoctorOrPatientName("Patient", patientId);

                while (patientResult.moveToNext()){
                    patientList.add(patientResult.getString(0) + " " + patientResult.getString(1));
                }
            }
        }

        patientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientList);
        patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientAdapter.notifyDataSetChanged();
        patient.setAdapter(patientAdapter);

        // get the selected patient name from spinner
        selectedPatient = String.valueOf(patient.getSelectedItem());
        Toast.makeText(this, selectedPatient, Toast.LENGTH_SHORT).show();
        //showTestInfo(patientId);
    }

    public void showTestInfo(int patientID){

        // get test table info based on selected patient and test name
        Cursor testResult = myDatabase.getTestInfo(selectedTest, patientID);

        if(testResult.getCount() != 0){
            testResult.moveToFirst();

            testInfo.setText(" BPL: " + testResult.getString(3) + "\n\n" +
                    " BPH: " + testResult.getString(4) + "\n\n" +
                    " Temperature: " + testResult.getString(5) + "\n\n" +
                    " Other Deatils: " + testResult.getString(6));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
