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
    private String get_question_url;
    private String get_answers_url;
    private String submit_answer_url;
    private String answer_displayed_url;

    private String nickname;
    private String teacherID;
    private String deviceID;
    private String questionID;
    private String result_json_string;
    private String qID, phoneID, answerID;


    public BackgroundTask(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        create_id_url = "http://mcs.drury.edu/gameofphones/mobilefiles/webservice/createdeviceid.php";
        check_id_url = "http://mcs.drury.edu/gameofphones/mobilefiles/webservice/isteacheridset.php";
        get_question_url = "http://mcs.drury.edu/gameofphones/mobilefiles/webservice/getquestion.php";
        get_answers_url = "http://mcs.drury.edu/gameofphones/mobilefiles/webservice/getquestionanswers.php";
        submit_answer_url = "http://mcs.drury.edu/gameofphones/mobilefiles/webservice/sendanswer.php";
        answer_displayed_url = "http://mcs.drury.edu/gameofphones/mobilefiles/webservice/isanswerdisplayed.php";
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

         else if (method.equals("getQuestion")) {

             deviceID = params[1];


             try {
                 bufferedWriter = getBufferedWriter(get_question_url);
                 data_string = URLEncoder.encode("deviceID", "UTF-8") + "=" + URLEncoder.encode(deviceID, "UTF-8");
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

         else if (method.equals("getAnswers")) {

             questionID = params[1];



             try {
                 bufferedWriter = getBufferedWriter(get_answers_url);
                 data_string = URLEncoder.encode("questionID", "UTF-8") + "=" + URLEncoder.encode(questionID, "UTF-8");
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

         else if (method.equals("submitAnswer")) {

             qID = params[1];
             phoneID = params[2];
             answerID = params[3];

             try {
                 bufferedWriter = getBufferedWriter(submit_answer_url);
                 data_string = URLEncoder.encode("currentQID", "UTF-8") + "=" + URLEncoder.encode(qID, "UTF-8") + "&" +
                         URLEncoder.encode("deviceID", "UTF-8") + "=" + URLEncoder.encode(phoneID, "UTF-8") + "&" +
                         URLEncoder.encode("answer", "UTF-8") + "=" + URLEncoder.encode(answerID, "UTF-8");
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

         else if (method.equals("displayedAnswer")) {

             phoneID = params[1];
             qID = params[2];

             try {
                 bufferedWriter = getBufferedWriter(answer_displayed_url);
                 data_string = URLEncoder.encode("deviceID", "UTF-8") + "=" + URLEncoder.encode(phoneID, "UTF-8") + "&" +
                         URLEncoder.encode("questionID", "UTF-8") + "=" + URLEncoder.encode(qID, "UTF-8");
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
