package com.example.gameofphones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.concurrent.ExecutionException;

public class ClassSession {

    private JSONArray jsonArray;
    private String currentMessage;

    public ClassSession(){}


    // gets the JSON message that connects teacher ID with current question
    public void setMessage(Context context, int teacherID){
        BackgroundTask backgroundTask = new BackgroundTask(context);
        try {
            currentMessage = backgroundTask.execute("checkID", Integer.toString(teacherID)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // checks if there's a question set for the current question
    public boolean isSet(){
        try{
            JSONObject jsonObject = new JSONObject(currentMessage);
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



