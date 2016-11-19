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

    static public int teacherID;
    private String message;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_teacher_id);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayQuestion.class);
        EditText myIDET = (EditText) findViewById(R.id.teacherID);
        String myID = myIDET.getText().toString();


        try {
            teacherID = Integer.parseInt(myID);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }

        Toast.makeText(this, "you entered " + teacherID, Toast.LENGTH_LONG).show();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            message = backgroundTask.execute("checkID", myID.toString()).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(isSet(message)){
            startActivity(intent);
        }

        else{
            Toast.makeText(this, "I don't recognize that ID. Try again." , Toast.LENGTH_LONG).show();
        }

    }

    private boolean isSet(String message){

        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_info");

            if (jsonArray.length() == 0 ){
                return false;
            }
            else {return true;}
        }
        catch (JSONException e){e.printStackTrace();}

        return false;
    }

    static public int getTeacherID(){
        return teacherID;
    }

}
