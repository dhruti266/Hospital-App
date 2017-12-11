package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayPatientInfoActivity extends AppCompatActivity implements OnItemSelectedListener {

    List<String> patientList;
    ArrayAdapter<String> patientAdapter;
    Spinner patient;
    String selectedPatient;
    TextView patientInfo;
    DatabaseHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient_info);

        myDatabase = new DatabaseHelper(this);

        patient = (Spinner) findViewById(R.id.spinnerPatientNames);
        patientInfo = (TextView) findViewById(R.id.txtPatientInfo);

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // get the selected patient name from spinner
        selectedPatient = String.valueOf(patient.getSelectedItem());
        showPatientInfo();
    }

    public void showPatientInfo(){
        Cursor patientResult = myDatabase.getPatientOrTestInfo("Patient", selectedPatient);
        Cursor doctorResult = null;

        if(patientResult.getCount() != 0){
            // moves row by row of the table data
            while (patientResult.moveToNext()){
                doctorResult = myDatabase.getDoctorOrPatientName("Doctor", Integer.parseInt(patientResult.getString(4)));


                while (doctorResult.moveToNext()) {
                    patientInfo.setText(" Name: " + patientResult.getString(1) + " " + patientResult.getString(2) + "\n\n" +
                            " Department: " + patientResult.getString(3) + "\n\n" +
                            " Under Doctor: " + doctorResult.getString(0) + " " + doctorResult.getString(1) + "\n\n" +
                            " Room: " + patientResult.getString(5));
                }

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
