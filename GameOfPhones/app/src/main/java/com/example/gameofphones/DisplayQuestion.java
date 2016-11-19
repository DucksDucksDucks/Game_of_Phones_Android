package com.example.gameofphones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_question);

        TextView device = (TextView) findViewById(R.id.deviceID);
        device.setText("Your device ID is " + MainActivity.getDeviceID());

        TextView teacher = (TextView) findViewById(R.id.teacherID);
        teacher.setText("Your teacher ID is " + EnterTeacherID.getTeacherID());

    }

}
