package com.example.gameofphones;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import java.util.concurrent.ExecutionException;

public class Student {

    static private int deviceID;
    private JSONArray jsonArray;
    private String message;

    public Student(){

    }

    public void registerDevice(String nickname, Context context){

        BackgroundTask backgroundTask = new BackgroundTask(context);
        try {
            System.out.println(nickname);
            message = backgroundTask.execute("addDevice", nickname).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        deviceID = getID(message);
    }

    // Gets the deviceID from the JSON message returned in "add device"
    private int getID(String message){
        try{
            JSONObject jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("deviceID");
            JSONObject JO = jsonArray.getJSONObject(0);
            return JO.getInt("device_id");
        }
        catch (JSONException e){e.printStackTrace();}
        return -1;
    }

    // returns the device id to other activities
    static public int getDeviceID(){
        return deviceID;
    }
}
