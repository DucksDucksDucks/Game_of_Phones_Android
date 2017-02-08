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

    private String message;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String myTeacherID;


    // Puts a button to press if question isn't set yet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_question);
        int teacherID = EnterTeacherID.getTeacherID();
        myTeacherID = Integer.toString(teacherID);

        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            message = backgroundTask.execute("checkID", myTeacherID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(isSet(message)){
            Intent intent = new Intent(this, DisplayQuestion.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Question Not Set Yet. Stare At This Button" , Toast.LENGTH_LONG).show();
        }
    }

    public void fetchQuestion(View view){
        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            message = backgroundTask.execute("checkID", myTeacherID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(isSet(message)){
            Intent intent = new Intent(this, DisplayQuestion.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Not Yet :(" , Toast.LENGTH_LONG).show();
        }

    }

    private boolean isSet(String message){
        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            Object theObject = JO.get("q_id");

            if (theObject.equals(null)){
                return false;
            }
            else {
                return true;}
        }
        catch (JSONException e){e.printStackTrace();}

        return false;
    }
}
