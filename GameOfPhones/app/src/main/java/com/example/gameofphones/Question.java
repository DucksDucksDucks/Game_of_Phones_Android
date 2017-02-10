package com.example.gameofphones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.concurrent.ExecutionException;


public class Question {

    private String questionMessage;
    private JSONObject JO;
    private int qID;
    private String qText;
    private String qType;
    private int correctID;
    private String imgName;

    public Question(){}

    public void setMessage(int teacherID, Context context){

        BackgroundTask backgroundTask = new BackgroundTask(context);
        try {
            questionMessage = backgroundTask.execute("getQuestion", Integer.toString(teacherID)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try{
            JSONObject jsonObject = new JSONObject(questionMessage);
            JSONArray jsonArray = jsonObject.getJSONArray("question_info");
            JSONObject JO = jsonArray.getJSONObject(0);
            qID = JO.getInt("q_id");
            qText = JO.getString("q_text");
            qType = JO.getString("q_type");
            correctID = JO.getInt("q_correct_id");
            imgName = JO.getString("p_filename");
        }
        catch (JSONException e){e.printStackTrace();}
    }

    public int getQID(){
        return qID;
    }
    public String getQuestionText(){
        return qText;
    }
    public String getQuestionType(){
        return qType;
    }
    public int getCorrectID(){
        return correctID;
    }
    public String getImageName(){
        return imgName;
    }

}
