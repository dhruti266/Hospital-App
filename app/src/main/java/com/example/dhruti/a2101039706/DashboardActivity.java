package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    String userType;
    Button enterTest, enterPatient, viewTest, viewPatient;
    View enterPatientView, viewPatientView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // get the selected cuisine type from previous screen
        Intent intent = getIntent();
        userType = intent.getStringExtra("checked");

        enterTest = (Button) findViewById(R.id.btnEnterTest);
        viewTest = (Button) findViewById(R.id.btnDisplayTest);

        enterPatient = (Button) findViewById(R.id.btnEnterPatient);
        viewPatient = (Button) findViewById(R.id.btnDisplayPatient);

        enterPatientView = findViewById(R.id.btnEnterPatient);
        viewPatientView = findViewById(R.id.btnDisplayPatient);

        if(userType.equals("Doctor")){
            enterPatientView.setVisibility(View.GONE);
            viewPatientView.setVisibility(View.GONE);
        }
        else {
            enterPatientView.setVisibility(View.VISIBLE);
            viewPatientView.setVisibility(View.VISIBLE);
        }


    }

    // method will called when user pressed the enter test button
    public void goToTestInfoScreen(View view){

        // control moves to Registration screen
        Intent intent = new Intent(this, TestInfoActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }

    // method will called when user pressed the display test button
    public void goToDisplayTestInfoScreen(View view){

        // control moves to Registration screen
        Intent intent = new Intent(this, DisplayTestInfoActivity.class);
        startActivity(intent);
    }

    // method will called when user pressed the enter patient button
    public void goToPatientInfoScreen(View view){

        // control moves to Registration screen
        Intent intent = new Intent(this, PatientInfoActivity.class);
        startActivity(intent);
    }

    // method will called when user pressed the display patient button
    public void goToDisplayPatientInfoScreen(View view){

        // control moves to Registration screen
        Intent intent = new Intent(this, DisplayPatientInfoActivity.class);
        startActivity(intent);
    }
}
