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

public class DisplayQuestion extends AppCompatActivity {

    private String questionMessage;
    private String answerMessage;
    private String submitMessage;

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private int questionID;
    private String questionString;
    private String questionType;
    private int correctID;
    private int answerIDs[] = new int[4];
    private int selectedID;
    private int deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_question);

        int teacherID = EnterTeacherID.getTeacherID();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {

            questionMessage = backgroundTask.execute("getQuestion", Integer.toString(teacherID)).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        questionID = getQID(questionMessage);
        System.out.println("ID = " + questionID);
        questionString = getQuestionText(questionMessage);
        System.out.println("Text = " + questionString);
        questionType = getQuestionType(questionMessage);
        System.out.println("Type = " + questionType);
        correctID = getCorrectID(questionMessage);
        System.out.println("Correct = " + correctID);

        TextView textField = (TextView) findViewById(R.id.questionBox);
        textField.setText(questionString);


        BackgroundTask backgroundTask2 = new BackgroundTask(this);
        try {
            answerMessage = backgroundTask2.execute("getAnswers", Integer.toString(questionID)).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        setAnswers(answerMessage);

    }


    public void firstAnswer(View view){

        selectedID = answerIDs[0];

        Toast.makeText(this, "you selected " + selectedID, Toast.LENGTH_LONG).show();
    }

    public void secondAnswer(View view){

        selectedID = answerIDs[1];

        Toast.makeText(this, "you selected " + selectedID, Toast.LENGTH_LONG).show();
    }

    public void thirdAnswer(View view){

        selectedID = answerIDs[2];

        Toast.makeText(this, "you selected " + selectedID, Toast.LENGTH_LONG).show();
    }

    public void fourthAnswer(View view){

        selectedID = answerIDs[3];

        Toast.makeText(this, "you selected " + selectedID, Toast.LENGTH_LONG).show();
    }


    public void sendMessage(View view){
        Intent intent = new Intent(this, SubmittedAnswer.class);
        System.out.println("Submitting ID " + selectedID);

        deviceID = MainActivity.getDeviceID();
        int status = -1;

        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            submitMessage = backgroundTask.execute("submitAnswer", Integer.toString(questionID),Integer.toString(deviceID),Integer.toString(selectedID)).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(submitMessage);

        try{

            jsonObject = new JSONObject(submitMessage);
            jsonArray = jsonObject.getJSONArray("errstatus");
            JSONObject JO = jsonArray.getJSONObject(0);
            status = JO.getInt("status");

        }
        catch (JSONException e){e.printStackTrace();}

        if(status == 1){
            startActivity(intent);
        }

        if(status == 0){
            Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show();
        }

        if(status == -1){
            Toast.makeText(this, "Oops", Toast.LENGTH_LONG).show();
        }

    }


    public void setAnswers(String message){

        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_answers");
            JSONObject JO = jsonArray.getJSONObject(0);
        }
        catch (JSONException e){e.printStackTrace();}

        String answerText = "";
        int answerID;

        for(int i = 0; i < jsonArray.length(); i++) {

            try{
                JSONObject JO = jsonArray.getJSONObject(i);
                answerText = JO.getString("a_text");
            }
            catch (JSONException e){e.printStackTrace();}

            if(i==0){TextView textField = (TextView) findViewById(R.id.answer1);
                textField.setText(answerText);}
            if(i==1){TextView textField = (TextView) findViewById(R.id.answer2);
                textField.setText(answerText);}
            if(i==2){TextView textField = (TextView) findViewById(R.id.answer3);
                textField.setText(answerText);}
            if(i==3){TextView textField = (TextView) findViewById(R.id.answer4);
                textField.setText(answerText);}

            // put the a_id in the answer array

            try{
                JSONObject JO = jsonArray.getJSONObject(i);
                answerID = JO.getInt("a_id");
                answerIDs[i] = answerID;
            }
            catch (JSONException e){e.printStackTrace();}

        }

    }

    private int getQID(String message){

        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            return JO.getInt("q_id");
        }
        catch (JSONException e){e.printStackTrace();}
        return -1;

    }


    private String getQuestionText(String message){

        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            return JO.getString("q_text");
        }
        catch (JSONException e){e.printStackTrace();}
        return "";


    }

    private String getQuestionType(String message){
        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            return JO.getString("q_type");
        }
        catch (JSONException e){e.printStackTrace();}
        return "";

    }

    private int getCorrectID(String message){
        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("question_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            return JO.getInt("q_correct_id");
        }
        catch (JSONException e){e.printStackTrace();}
        return -1;

    }



}
