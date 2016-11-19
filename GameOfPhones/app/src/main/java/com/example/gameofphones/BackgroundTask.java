package com.example.gameofphones;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundTask extends AsyncTask<String, Void, String> {
    private URL url;
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private StringBuilder stringBuilder;
    private String data_string;
    private String JSON_STRING;
    private String CHECK;
    private Context context;

    private String create_id_url;
    private String check_id_url;

    private String nickname;
    private String teacherID;
    private String result_json_string;


    public BackgroundTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        create_id_url = "http://mcs.drury.edu/amerritt/createDeviceID.php";
        check_id_url = "http://mcs.drury.edu/amerritt/isTeacherIDSet.php";
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];

         if (method.equals("addDevice")) {

            nickname = params[1];


            try {
                bufferedWriter = getBufferedWriter(create_id_url);
                data_string = URLEncoder.encode("nickname", "UTF-8") + "=" + URLEncoder.encode(nickname, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //check response
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                result_json_string = readJSONToString(bufferedReader);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                return result_json_string;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (method.equals("checkID")) {

            teacherID = params[1];


            try {
                bufferedWriter = getBufferedWriter(check_id_url);
                data_string = URLEncoder.encode("deviceID", "UTF-8") + "=" + URLEncoder.encode(teacherID, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //check response
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                result_json_string = readJSONToString(bufferedReader);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                return result_json_string;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

    }

    public BufferedWriter getBufferedWriter(String php_url) {
        BufferedWriter writer = null;
        try {
            url = new URL(php_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            outputStream = httpURLConnection.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer;
    }

    public BufferedReader getBufferedReader(String php_url) {
        BufferedReader reader = null;

        try {
            url = new URL(php_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    public String readJSONToString(BufferedReader reader) {
        String result = "";

        stringBuilder = new StringBuilder();
        try {
            while ((JSON_STRING = reader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }
            result = stringBuilder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
