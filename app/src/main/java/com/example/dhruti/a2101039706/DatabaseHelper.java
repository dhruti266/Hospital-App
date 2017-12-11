package com.example.dhruti.a2101039706;

/*  Dhruti Parekh - 101039706
    COMP3074 -  Assignment 2
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.StringBuilderPrinter;

/**
 * Created by dhruti on 2017-11-06.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "hospital.db";

    // Columns of Doctor table
    public static final String TABLE_DOCTOR = "doctor";
    public static final String COLUMN_DOCTOR_ID = "_id";
    public static final String COLUMN_DOCTOR_FIRST_NAME = "first_name";
    public static final String COLUMN_DOCTOR_LAST_NAME = "last_name";
    public static final String COLUMN_DOCTOR_USERNAME = "username";
    public static final String COLUMN_DOCTOR_PASSWORD = "password";
    public static final String COLUMN_DOCTOR_DEPARTMENT = "department";

    // Columns of Nurse table
    public static final String TABLE_NURSE = "nurse";
    public static final String COLUMN_NURSE_ID = "_id";
    public static final String COLUMN_NURSE_FIRST_NAME = "first_name";
    public static final String COLUMN_NURSE_LAST_NAME = "last_name";
    public static final String COLUMN_NURSE_USERNAME = "username";
    public static final String COLUMN_NURSE_PASSWORD = "password";
    public static final String COLUMN_NURSE_DEPARTMENT = "department";

    // Columns of Patient table
    public static final String TABLE_PATIENT = "patient";
    public static final String COLUMN_PATIENT_ID = "_id";
    public static final String COLUMN_PATIENT_FIRST_NAME = "first_name";
    public static final String COLUMN_PATIENT_LAST_NAME = "last_name";
    public static final String COLUMN_PATIENT_DEPARTMENT = "department";
    public static final String COLUMN_PATIENT_DOCTOR_ID = "doctor_id";
    public static final String COLUMN_PATIENT_ROOM = "room";

    // Columns of Test table
    public static final String TABLE_TEST = "test";
    public static final String COLUMN_TEST_ID = "_id";
    public static final String COLUMN_TEST_NAME = "test_name";
    public static final String COLUMN_TEST_PATIENT_ID = "patient_id";
    public static final String COLUMN_TEST_BPL= "bpl";
    public static final String COLUMN_TEST_BPH = "bph";
    public static final String COLUMN_TEST_TEMPERATURE = "temperature";
    public static final String COLUMN_TEST_OTHER_DETAILS = "other_details";

    // Statement of doctor table creation
    private static final String SQL_CREATE_TABLE_DOCTOR =
            "CREATE TABLE " + TABLE_DOCTOR + " (" +
                    COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DOCTOR_FIRST_NAME + " TEXT NOT NULL," +
                    COLUMN_DOCTOR_LAST_NAME + " TEXT NOT NULL," +
                    COLUMN_DOCTOR_USERNAME + " TEXT NOT NULL," +
                    COLUMN_DOCTOR_PASSWORD + " TEXT NOT NULL," +
                    COLUMN_DOCTOR_DEPARTMENT + " TEXT NOT NULL)";

    // Statement of nurse table creation
    private static final String SQL_CREATE_TABLE_NURSE =
            "CREATE TABLE " + TABLE_NURSE + " (" +
                    COLUMN_NURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NURSE_FIRST_NAME + " TEXT NOT NULL," +
                    COLUMN_NURSE_LAST_NAME + " TEXT NOT NULL," +
                    COLUMN_NURSE_USERNAME + " TEXT NOT NULL," +
                    COLUMN_NURSE_PASSWORD + " TEXT NOT NULL," +
                    COLUMN_NURSE_DEPARTMENT + " TEXT NOT NULL)";

    // Statement of patient table creation
    private static final String SQL_CREATE_TABLE_PATIENT =
            "CREATE TABLE " + TABLE_PATIENT + " (" +
                    COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PATIENT_FIRST_NAME + " TEXT NOT NULL," +
                    COLUMN_PATIENT_LAST_NAME + " TEXT NOT NULL," +
                    COLUMN_PATIENT_DEPARTMENT + " TEXT NOT NULL," +
                    COLUMN_PATIENT_DOCTOR_ID + " INTEGER NOT NULL," +
                    COLUMN_PATIENT_ROOM + " INTEGER NOT NULL)";

    // Statement of test table creation
    private static final String SQL_CREATE_TABLE_TEST =
            "CREATE TABLE " + TABLE_TEST + " (" +
                    COLUMN_TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TEST_NAME + " TEXT NOT NULL," +
                    COLUMN_TEST_PATIENT_ID + " INTEGER NOT NULL," +
                    COLUMN_TEST_BPL + " TEXT NOT NULL," +
                    COLUMN_TEST_BPH + " TEXT NOT NULL," +
                    COLUMN_TEST_TEMPERATURE + " TEXT NOT NULL," +
                    COLUMN_TEST_OTHER_DETAILS + " TEXT)";


    // when this constructor will be called, database hospital.db will be created
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 3);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_DOCTOR);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_NURSE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TEST);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PATIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       // sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TEST);
    }

    // insert data into doctor table
    public boolean insertDoctorData(String fName, String lName, String username, String password, String dept){
        SQLiteDatabase doctor = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DOCTOR_FIRST_NAME, fName);
        contentValues.put(COLUMN_DOCTOR_LAST_NAME, lName);
        contentValues.put(COLUMN_DOCTOR_USERNAME, username);
        contentValues.put(COLUMN_DOCTOR_PASSWORD, password);
        contentValues.put(COLUMN_DOCTOR_DEPARTMENT, dept);

        long result = doctor.insert(TABLE_DOCTOR, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            return true;

    }

    // insert data into nurse table
    public boolean insertNurseData(String fName, String lName, String username, String password, String dept){
        SQLiteDatabase nurse = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NURSE_FIRST_NAME, fName);
        contentValues.put(COLUMN_NURSE_LAST_NAME, lName);
        contentValues.put(COLUMN_NURSE_USERNAME, username);
        contentValues.put(COLUMN_NURSE_PASSWORD, password);
        contentValues.put(COLUMN_NURSE_DEPARTMENT, dept);

        long result = nurse.insert(TABLE_NURSE, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            return true;

    }

    // insert data into patient table
    public boolean insertPatientData(String fName, String lName, String dept, Integer doctor_id, Integer room){
        SQLiteDatabase patient = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_FIRST_NAME, fName);
        contentValues.put(COLUMN_PATIENT_LAST_NAME, lName);
        contentValues.put(COLUMN_PATIENT_DEPARTMENT, dept);
        contentValues.put(COLUMN_PATIENT_DOCTOR_ID, doctor_id);
        contentValues.put(COLUMN_PATIENT_ROOM, room);

        long result = patient.insert(TABLE_PATIENT, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            return true;

    }

    // insert data into TEST table
    public boolean insertTestData(String tName, Integer id, String bpl, String bph, String temp, String details){
        SQLiteDatabase test = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEST_NAME, tName);
        contentValues.put(COLUMN_TEST_PATIENT_ID, id);
        contentValues.put(COLUMN_TEST_BPL, bpl);
        contentValues.put(COLUMN_TEST_BPH, bph);
        contentValues.put(COLUMN_TEST_TEMPERATURE, temp);
        contentValues.put(COLUMN_TEST_OTHER_DETAILS, details);

        long result = test.insert(TABLE_TEST, null, contentValues);
        if(result == -1){
            return false;
        }
        else
            return true;

    }

    public Cursor getAllData(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = null ;
        if(tableName.equals("Doctor"))
            result = db.rawQuery("SELECT * FROM " + TABLE_DOCTOR, null);
        else if(tableName.equals("Nurse"))
            result = db.rawQuery("SELECT * FROM " + TABLE_NURSE, null);
        else if(tableName.equals("Patient"))
            result = db.rawQuery("SELECT * FROM " + TABLE_PATIENT, null);
        else if(tableName.equals("Test"))
            result = db.rawQuery("SELECT DISTINCT " + COLUMN_TEST_NAME + " FROM " + TABLE_TEST, null);

        return  result;
    }

    public Cursor getDoctorOrPatientId(String tableName, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = null;

        if(tableName.equals("Doctor"))
            result = db.rawQuery("SELECT " + COLUMN_DOCTOR_ID +  " FROM " + TABLE_DOCTOR + " WHERE " + COLUMN_DOCTOR_FIRST_NAME + "='" + name + "'", null);
        else if(tableName.equals("Patient"))
            result = db.rawQuery("SELECT " + COLUMN_PATIENT_ID +  " FROM " + TABLE_PATIENT + " WHERE " + COLUMN_PATIENT_FIRST_NAME + "='" + name + "'", null);

        return  result;
    }

    public Cursor getDoctorOrPatientName(String tableName, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = null;

        if(tableName.equals("Doctor"))
            result = db.rawQuery("SELECT " + COLUMN_DOCTOR_FIRST_NAME + ", " + COLUMN_DOCTOR_LAST_NAME + " FROM " + TABLE_DOCTOR + " WHERE " + COLUMN_DOCTOR_ID + "='" + id + "'", null);
        else if(tableName.equals("Patient"))
            result = db.rawQuery("SELECT " + COLUMN_PATIENT_FIRST_NAME + ", " + COLUMN_PATIENT_LAST_NAME + " FROM " + TABLE_PATIENT + " WHERE " + COLUMN_PATIENT_ID + "='" + id + "'", null);

        return  result;

    }

    public  Cursor getPatientOrTestInfo(String tableName, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = null;

        if(tableName.equals("Patient"))
            result = db.rawQuery("SELECT * FROM " + TABLE_PATIENT + " WHERE " + COLUMN_PATIENT_FIRST_NAME + "='" + name + "'", null);
        else if(tableName.equals("Test"))
            result = db.rawQuery("SELECT * FROM " + TABLE_TEST + " WHERE " + COLUMN_TEST_NAME + "='" + name + "'", null);

        return  result;
    }

    public  Cursor getTestInfo(String tName, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_TEST + " WHERE " + COLUMN_TEST_NAME + "='" + tName + "' AND " + COLUMN_TEST_PATIENT_ID + "=" + id , null);

        return  result;
    }
}
