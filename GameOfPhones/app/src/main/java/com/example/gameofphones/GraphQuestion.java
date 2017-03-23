package com.example.gameofphones;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GraphQuestion extends AppCompatActivity {

    private Toolbar mToolbar_bottom;
    private static final String LOG_CAT = GraphQuestion.class.getSimpleName();

    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    private void handleDrawingIconTouched(int itemId){
        switch(itemId){
            case R.id.action_undo:
                mCustomView.onClickUndo();
                break;
            case R.id.action_redo:
                mCustomView.onClickRedo();
                break;
            case R.id.action_save:
                System.out.println("Save Drawing");
                saveDrawingDialog();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

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
        File dir = new File(path);
        mCustomView.setDrawingCacheEnabled(true);

        String imTitle = "Drawing" + "_" + System.currentTimeMillis()+".png";
        String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(),
                mCustomView.getDrawingCache(), imTitle, "a drawing");

        try {
            if (!dir.isDirectory() || !dir.exists()){
                dir.mkdirs();
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

}