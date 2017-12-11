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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnFocusChangeListener {

    DatabaseHelper myDatabase;
    EditText username, password;
    Button signIn;
    RadioGroup userType;
    RadioButton rbCommon;
    String strType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new DatabaseHelper(this);

        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        userType = (RadioGroup) findViewById(R.id.radioGroupUserType);
        signIn = (Button) findViewById(R.id.btnSignIn);

        String msg = "Welcome to Apollo Hospitals";
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        validateUserInfo();
    }


    public boolean validateUserInfo(){

        if( username.getText().toString().length() == 0 ){
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

    // method will called when user pressed the Sign in button
    public void goToDashboardScreen(View view){

        Cursor result;
        if(validateUserInfo()) {
            // checks data from either doctor table or nurse table according to radio button selection
            if(strType.equals("Doctor")){
                result = myDatabase.getAllData("Doctor");
            }
            else
                result = myDatabase.getAllData("Nurse");


            // shows error msg if table data is empty
            if(result.getCount() == 0){
                Toast.makeText(this,"There is no such username",Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = false;
            // moves row by row of the table data
            while (result.moveToNext()){

                if(result.getString(3).equals(username.getText().toString()) && result.getString(4).equals(password.getText().toString())){
                    Toast.makeText(this,"Welcome " + result.getString(1) + " " + result.getString(2),Toast.LENGTH_SHORT).show();
                    isValid = true;
                }
            }

            if(isValid)
            {
                // control moves to Registration screen
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.putExtra("checked",strType);
                startActivity(intent);
            }
            else
                Toast.makeText(this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
        }
        else {
            return;
        }

    }

    // method will called when user pressed the Sign up button
    public void goToRegistrationScreen(View view){

        // control moves to Registration screen
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


}
