package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements OnFocusChangeListener, OnItemSelectedListener {

    List<String> departmentList;
    ArrayAdapter<String> adapter;
    Spinner department;
    String selectedDept;
    EditText fName, lName, username, password;
    Button signUp;
    RadioGroup userType;
    RadioButton rbCommon;
    String strType;
    DatabaseHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        myDatabase = new DatabaseHelper(this);

        department = (Spinner) findViewById(R.id.spinnerDepartment);

        fName = (EditText) findViewById(R.id.editFirstName);
        lName = (EditText) findViewById(R.id.editLastName);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);

        userType = (RadioGroup) findViewById(R.id.radioGroupUserType);
        signUp = (Button) findViewById(R.id.btnSignUp);

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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departmentList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        department.setAdapter(dataAdapter);

        department.setOnItemSelectedListener(this);
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
        else if( username.getText().toString().length() == 0 ){
            username.setError( "Valid username is required!" );
            return false;
        }
        else if( password.getText().toString().length() < 6){
            password.setError( "Password should have at least 6 characters" );
            return false;
        }

        //gets the selected radio button id
        int radioId = userType.getCheckedRadioButtonId();
        if(radioId <= 0){
            String error = "Please select user type";
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            rbCommon = (RadioButton) findViewById(radioId);
            strType = rbCommon.getText().toString();
        }
        return  true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // get the selected department name from spinner
        selectedDept = String.valueOf(department.getSelectedItem());
    }

    public void goToMainScreen(View view){

        boolean isInserted = false;
        if(validateUserInfo()) {
            // checks whether doctor or nurse is registering and insert the data according to that
            if(strType.equals("Doctor")){

                isInserted = myDatabase.insertDoctorData(
                        fName.getText().toString(),
                        lName.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString(),
                        selectedDept);
            }
            else if(strType.equals("Nurse"))
            {
                isInserted = myDatabase.insertNurseData(
                        fName.getText().toString(),
                        lName.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString(),
                        selectedDept);
            }


            if(isInserted == true){
                Toast.makeText(this, "You are successfully registered", Toast.LENGTH_SHORT).show();

                // control moves to Main login screen
                Intent intent = new Intent(this, MainActivity.class);
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
