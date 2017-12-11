package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PatientInfoActivity extends AppCompatActivity implements OnFocusChangeListener, OnItemSelectedListener {

    List<String> departmentList, doctorList;
    ArrayAdapter<String> deptAdapter, docAdapter;
    Spinner department, doctor;
    String selectedDept, selectedDoctor;
    EditText fName, lName, room;
    Button addPatient;
    DatabaseHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        myDatabase = new DatabaseHelper(this);

        department = (Spinner) findViewById(R.id.spinnerDepartment);
        doctor = (Spinner) findViewById(R.id.spinnerDoctorNames);

        fName = (EditText) findViewById(R.id.editFirstName);
        lName = (EditText) findViewById(R.id.editLastName);
        room = (EditText) findViewById(R.id.editRoom);
        addPatient = (Button) findViewById(R.id.btnAddPatient);

        departmentList = new ArrayList<String>();
        departmentList.add("Accident and emergency");
        departmentList.add("Anaesthetics");
        departmentList.add("Cardiology");
        departmentList.add("Diagnostic imaging");
        departmentList.add("Gynaecology");
        departmentList.add("Neurology");
        departmentList.add("Nutrition and dietetics");
        departmentList.add("Orthopaedics");
        departmentList.add("Pharmacy");
        departmentList.add("Physiotherapy");

        deptAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departmentList);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptAdapter.notifyDataSetChanged();
        department.setAdapter(deptAdapter);

        department.setOnItemSelectedListener(this);

        // import doctor table data to the doctor spinner
        doctorList = new ArrayList<String>();

        Cursor result = myDatabase.getAllData("Doctor");

        if(result.getCount() != 0){
            // moves row by row of the table data
            while (result.moveToNext()){
                doctorList.add(result.getString(1));
            }
        }

        docAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorList);
        docAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docAdapter.notifyDataSetChanged();
        doctor.setAdapter(docAdapter);

        doctor.setOnItemSelectedListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        validateUserInfo();
    }

    public boolean validateUserInfo(){

        if( fName.getText().toString().length() == 0 ){
            fName.setError( "Valid first name is required!" );
            return false;
        }
        else if( lName.getText().toString().length() == 0 ){
            lName.setError( "Valid last name is required!" );
            return false;
        }
        else if( room.getText().toString().length() < 3 ){
            room.setError( "Valid room number is required!" );
            return false;
        }

        return  true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // get the selected department name from spinner
        selectedDept = String.valueOf(department.getSelectedItem());
        selectedDoctor = String.valueOf(doctor.getSelectedItem());
    }

    public void addNewPatient(View view){

        boolean isInserted = false;
        int docId = 0;
        // get the doctor id from database according to selected doctor from spinner
        Cursor resId = myDatabase.getDoctorOrPatientId("Doctor", selectedDoctor);
        while (resId.moveToNext()) {
            docId = Integer.parseInt(resId.getString(0));
        }

        if(validateUserInfo()) {

            isInserted = myDatabase.insertPatientData(
                    fName.getText().toString(),
                    lName.getText().toString(),
                    selectedDept,
                    docId,
                    Integer.parseInt(room.getText().toString()));

            if(isInserted == true){
                Toast.makeText(this, "Patient is successfully added", Toast.LENGTH_SHORT).show();
                // control moves to Dashboard screen
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.putExtra("checked", "Nurse");
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
