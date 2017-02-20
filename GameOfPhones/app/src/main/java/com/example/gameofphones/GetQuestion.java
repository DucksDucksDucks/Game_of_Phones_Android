package com.example.gameofphones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class GetQuestion extends AppCompatActivity {

    private int teacherID;
    public static ClassSession session;

    // Puts a button to press if question isn't set yet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_question);

        session = new ClassSession();

        teacherID = EnterTeacherID.teacher.getTeacherID();

        session.setMessage(this, teacherID);

        if(session.isSet()){
            Intent intent = new Intent(this, DisplayQuestion.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Question Not Set Yet" , Toast.LENGTH_LONG).show();
        }
    }

    public void fetchQuestion(View view){

        session.setMessage(this, teacherID);

        if(session.isSet()){
            Intent intent = new Intent(this, DisplayQuestion.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Not Yet" , Toast.LENGTH_LONG).show();
        }

    }
}
