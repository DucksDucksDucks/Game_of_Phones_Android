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

public class MainActivity extends AppCompatActivity {

    static public boolean DEBUG = false;
    public static Student student;
    static public boolean VERBOSE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(DEBUG){
            // go straight to screen to debug
            Intent intent = new Intent(this, DisplayQuestion.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student = new Student();
    }

    // Called when the user clicks the "submit" button
    public void sendMessage(View view) {
        EditText myNickname = (EditText) findViewById(R.id.nickname);
        String nickname = myNickname.getText().toString();


        if(nickname.length() == 0){
            Toast.makeText(this, "Please enter a nickname", Toast.LENGTH_LONG).show();
        }
        else if(nickname.length() > 20){
            Toast.makeText(this, "Nickname too long", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, EnterTeacherID.class);
            student.registerDevice(nickname, this);
            int deviceID = student.getDeviceID();
            if (VERBOSE) {
                Toast.makeText(this, "your ID is" + deviceID, Toast.LENGTH_LONG).show();
            }
            startActivity(intent);
        }
    }
}
