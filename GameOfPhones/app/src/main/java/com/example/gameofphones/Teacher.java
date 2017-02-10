package com.example.gameofphones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import java.util.concurrent.ExecutionException;

public class Teacher {

    static private int teacherID;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String message;

    public Teacher(){

    }

    // checks if teacher ID is currently in the database
    public boolean isSet(){
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

    public void setID(Context context, int myID) {

        teacherID = myID;

        BackgroundTask backgroundTask = new BackgroundTask(context);
        try {
            message = backgroundTask.execute("checkID", Integer.toString(myID)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static public int getTeacherID(){
        return teacherID;
    }

}
