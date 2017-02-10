package com.example.gameofphones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class EnterTeacherID extends AppCompatActivity {

    private int teacherID;
    private String message;
    public static Teacher teacher;
    private boolean DEBUG = MainActivity.DEBUG;
    private boolean VERBOSE = MainActivity.VERBOSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_teacher_id);

        teacher = new Teacher();
    }

    // Sets the ID to entered ID. Checks if the ID currently exists in the database.
    public void sendMessage(View view) {
        Intent intent = new Intent(this, GetQuestion.class);
        EditText myIDET = (EditText) findViewById(R.id.teacherID);
        String myID = myIDET.getText().toString();

        try {
            teacherID = Integer.parseInt(myID);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }

        if(VERBOSE) {
            Toast.makeText(this, "you entered " + teacherID, Toast.LENGTH_LONG).show();
        }

        teacher.setID(this, teacherID);

        if(teacher.isSet()){
            startActivity(intent);
        }

        else{
            Toast.makeText(this, "I don't recognize that ID. Try again." , Toast.LENGTH_LONG).show();
        }
    }


}
