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

    public int deviceID;
    private String message;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, EnterTeacherID.class);
        EditText myNickname = (EditText) findViewById(R.id.nickname);
        String nickname = myNickname.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            System.out.println(nickname);
            message = backgroundTask.execute("addDevice", nickname).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        deviceID = getID(message);
        Toast.makeText(this, "your ID is" + deviceID, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    private int getID(String message){

        try{
            jsonObject = new JSONObject(message);
            jsonArray = jsonObject.getJSONArray("deviceID");
            JSONObject JO = jsonArray.getJSONObject(0);
            return JO.getInt("device_id");
        }
        catch (JSONException e){e.printStackTrace();}
        return -1;
    }
}
