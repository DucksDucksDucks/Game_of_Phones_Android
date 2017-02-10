package com.example.gameofphones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubmittedAnswer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_answer);
    }

    public void sendMessage(View view) {
        Toast.makeText(this, "This doesn't do anything yet :)" , Toast.LENGTH_LONG).show();
    }


}
