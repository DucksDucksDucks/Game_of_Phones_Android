package com.example.gameofphones;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GraphQuestion extends AppCompatActivity {

    private boolean VERBOSE = MainActivity.VERBOSE;
    private boolean DEBUG = MainActivity.DEBUG;
    public static boolean GRAPH = false;
    private String nickname = MainActivity.nickname;

    private Toolbar mToolbar_bottom;
    private static final String LOG_CAT = GraphQuestion.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;

    private CustomView mCustomView;
    private String submitMessage;
    private String photo;
    private String filename;
    private int teacherID;
    private int deviceID;

    private int questionID;
    private String questionString;
    private String questionType;
    private int correctID;
    private String imageName;

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public static Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * permissions for saving drawing to device gallery. In order to use this,
         * the min API needs to be 23

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
         **/

        GRAPH = true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_question);

        mCustomView = (CustomView)findViewById(R.id.custom_view);
        mToolbar_bottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        mToolbar_bottom.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_dots_vertical_white_24dp));

        mToolbar_bottom.inflateMenu(R.menu.menu_drawing);
        mToolbar_bottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
           @Override
            public boolean onMenuItemClick(MenuItem item){
               handleDrawingIconTouched(item.getItemId());
               return false;
           }
        });

        if(DEBUG) {
            // skips entering ID screens, manually set
            teacherID = 14;
            deviceID = 1;
        }
        else{
            teacherID = EnterTeacherID.teacher.getTeacherID();
            deviceID = MainActivity.student.getDeviceID();
        }

        question = new Question();
        question.setMessage(teacherID, this);

        questionID = question.getQID();
        questionString = question.getQuestionText();
        imageName = question.getImageName();

        if(VERBOSE) {
            System.out.println("ID = " + questionID);
            System.out.println("Text = " + questionString);
            System.out.println("Image = " + imageName);
        }
        TextView textField = (TextView) findViewById(R.id.textView);
        textField.setText(questionString);
    }

    private void handleDrawingIconTouched(int itemId){
        switch(itemId){
            case R.id.action_undo:
                mCustomView.onClickUndo();
                break;
            case R.id.action_redo:
                mCustomView.onClickRedo();
                break;
            //case R.id.action_save:
            //    System.out.println("Save Drawing");
            //    saveDrawingDialog();
            //    break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    public void submitGraphDialogue(View view){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Submit graph");
        saveDialog.setMessage("Submit answer?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                submitGraph();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    public void submitGraph() {
        if (DEBUG) {
            nickname = "nickname";
        }

        filename = nickname + "_" + System.currentTimeMillis();
        mCustomView.setDrawingCacheEnabled(true);
        Bitmap bm = mCustomView.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 30, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
        photo = Base64.encodeToString(byte_arr, 0);

        View b = findViewById(R.id.SubmitButton);
        b.setEnabled(false);

        System.out.println("Submitting photo");

        BackgroundTask backgroundTask = new BackgroundTask(this);
        try {
            submitMessage = backgroundTask.execute("uploadImage", photo, filename, Integer.toString(questionID), Integer.toString(deviceID), (filename + ".png")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        int status = -1;

        if (submitMessage.equals("Image upload fail")) {
            System.out.println("Nope");
        } else {
            try {
                jsonObject = new JSONObject(submitMessage);
                jsonArray = jsonObject.getJSONArray("errstatus");
                JSONObject JO = jsonArray.getJSONObject(0);
                status = JO.getInt("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status == 1) {
                Intent intent = new Intent(this, SubmittedAnswer.class);
                startActivity(intent);
            }
            if (status == 0) {
                Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show();
            }
            if (status == -1) {
                Toast.makeText(this, "Something went wrong. Have you already submitted an answer to this question?", Toast.LENGTH_LONG).show();
            }
            System.out.println(submitMessage);
        }
    }





/**
 * Code for saving the drawing to the phone's gallery

    public void saveDrawingDialog(){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                saveThisDrawing();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }


    public void saveThisDrawing(){
        String path = Environment.getExternalStorageDirectory().toString();
        path = path +"/"+ getString(R.string.app_name);
        System.out.println("Path is " + path.toString());
        File dir = new File(path);
        mCustomView.setDrawingCacheEnabled(true);

        String imTitle = "Drawing" + "_" + System.currentTimeMillis()+".png";
        String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(),
                mCustomView.getDrawingCache(), imTitle, "a drawing");

        System.out.println("imTitle is " + imTitle);

        try {
            if (!dir.isDirectory() || !dir.exists()){
                boolean res = dir.mkdirs();
                System.out.println("mkdirs " + res);
            }
            mCustomView.setDrawingCacheEnabled(true);
            File file = new File(dir, imTitle);
            FileOutputStream fOut = new FileOutputStream(file);
            Bitmap bm = mCustomView.getDrawingCache();
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Uh oh!");
            alert.setMessage("Image could not be saved");
            alert.setPositiveButton("OK", null);
            alert.show();
        } catch (IOException e){
            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                    "Image could not be saved", Toast.LENGTH_SHORT);
            unsavedToast.show();
            e.printStackTrace();
        }

        if (imgSaved!=null){
            Toast savedToast = Toast.makeText(getApplicationContext(),
                    "Drawing saved to gallery", Toast.LENGTH_SHORT);
            savedToast.show();
        }

        mCustomView.destroyDrawingCache();
    }
 **/

}