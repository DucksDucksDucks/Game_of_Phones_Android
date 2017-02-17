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

public class SubmittedAnswer extends AppCompatActivity {

    private String displayedMessage;
    private int questionID;
    private int deviceID;
    private int displayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_answer);

        deviceID = MainActivity.student.getDeviceID();
        questionID = DisplayQuestion.question.getQID();
    }

    public void sendMessage(View view) {

        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            displayedMessage = backgroundTask.execute("displayedAnswer", Integer.toString(deviceID), Integer.toString(questionID)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try{
            JSONObject jsonObject = new JSONObject(displayedMessage);
            JSONArray jsonArray = jsonObject.getJSONArray("answer_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            displayed = JO.getInt("displayed");
        }
        catch (JSONException e){e.printStackTrace();}

        if(displayed==0){
        View b = findViewById(R.id.yesDisplayed);
        b.setVisibility(View.GONE);
        View a = findViewById(R.id.noDisplayed);
        a.setVisibility(View.VISIBLE);
        }


        else if(displayed==1){
            View b = findViewById(R.id.yesDisplayed);
            b.setVisibility(View.VISIBLE);
            View a = findViewById(R.id.noDisplayed);
            a.setVisibility(View.GONE);
        }

    }

    public void refreshQuestion(View view){
        Intent intent = new Intent(this, DisplayQuestion.class);
        startActivity(intent);
    }


}
