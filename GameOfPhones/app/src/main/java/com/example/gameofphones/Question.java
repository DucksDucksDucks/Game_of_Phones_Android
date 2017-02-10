package com.example.gameofphones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.concurrent.ExecutionException;

/**
 * Created by amerritt on 2/10/2017.
 */

public class Question {

    private JSONArray jsonArray;
    private String message;

    public Question(){}

    public void setMessage(Context context, int teacherID){

        BackgroundTask backgroundTask = new BackgroundTask(context);
        try {
            message = backgroundTask.execute("checkID", Integer.toString(teacherID)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public boolean isSet(){

        try{
            JSONObject jsonObject = new JSONObject(message);
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
