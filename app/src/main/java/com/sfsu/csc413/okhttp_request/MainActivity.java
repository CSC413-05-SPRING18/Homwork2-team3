package com.sfsu.csc413.okhttp_request;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;

import android.text.util.Linkify;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends AppCompatActivity {
    private TextView Result;
    private Button button;
    private OkHttpClient client;




    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Result = findViewById(R.id.text_view_result);
        button = findViewById(R.id.getbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton();
            }
        });

//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setOAuthConsumerKey("vO5tWY5lpKWcTQlR27MkU19En");
//        cb.setOAuthConsumerSecret("EMjsi6OkAebrNhqVMdjF5dAwrslcBdwt7ckaoskSGnOeFa84md");
//        cb.setOAuthAccessToken("783044167744180225-7K1zh2gwqYercTFr8hv3tJC7ByQYWFk");
//        cb.setOAuthAccessTokenSecret("HggtAcqqhDNyBk1ba8Pe9VIKzI6NIYgxF4Qd304mq5Egz");

//        Result.setText("searching");
//        Log.d("THIS APP","searching");
//        try
//        {
//            TwitterFactory tfactory = new TwitterFactory(cb.build());
//            Twitter twitter = tfactory.getInstance();
//            Log.d("THIS APP","searching");
//
//            Query query = new Query("source:twitter4j yusukey");
//            QueryResult result = twitter.search(query);
//
//            for(Status status : result.getTweets())
//            {
//                Log.d("THIS APP",status.getUser().getScreenName());
//            }
//
//            //for(Location location : locations){
//            //    Result.setText(Result.getText()+"\n "+location.getName());
//            //    System.out.println("Got here");
//            //}
//
//            //Query query = new Query("source:twitter4j yusukey");
//            //QueryResult result = twitter.search(query);
//            //for (Status status : result.getTweets()) {
//            //    Result.setText("@" + status.getUser().getScreenName() + ":" + status.getText());
//            //}
//        } catch(Exception ex){
//            Log.d("THIS APP",ex.getMessage());
//        }
    }

    private void getButton(){
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.stackexchange.com/2.2/posts?page=1&pagesize=100&order=desc&sort=votes&site=stackoverflow";



        //http://reqres.in/api/users?page=2//

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //gets table layout
                            TableLayout table = (TableLayout)findViewById(R.id.results);
                            TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                            TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

                            //Displays the headers
                            TableRow headers = new TableRow(getApplicationContext());
                            TextView header1 = new TextView(getApplicationContext());
                            TextView header2 = new TextView(getApplicationContext());
                            TextView header3 = new TextView(getApplicationContext());

                            header1.setText("Username");
                            header2.setText("Score");
                            header3.setText("Link");

                            header1.setPadding(10,10,10,10);
                            header2.setPadding(10,10,10,10);
                            header3.setPadding(10,10,10,10);

                            header1.setLayoutParams(params2);
                            header2.setLayoutParams(params2);
                            header3.setLayoutParams(params2);

                            headers.addView(header1);
                            headers.addView(header2);
                            headers.addView(header3);

                            headers.setLayoutParams(params2);
                            table.addView(headers);


                            try {
                                JSONObject json = new JSONObject(myResponse);
                                JSONArray posts = json.getJSONArray("items");

                                String text = "";
                                for (int x = 0; x < posts.length(); x++) {
                                    JSONObject obj = posts.getJSONObject(x);

                                    JSONObject user = obj.getJSONObject("owner");

                                    //String username = obj.toString();
                                    String username = user.getString("display_name");
                                    //Log.d("TAG", username);

                                    //String username = obj.getString("display_name");
                                    //String username = "";
                                    String score = obj.getString("score");
                                    String link = obj.getString("link");

                                    text += username + " | " + score +" | " + link+ "\n";
                                    //Result.setText(text);

                                    TableRow row = new TableRow(getApplicationContext());

                                    TextView col1 = new TextView(getApplicationContext());
                                    TextView col2 = new TextView(getApplicationContext());
                                    TextView col3 = new TextView(getApplicationContext());

                                    col1.setText(username);
                                    col2.setText(score);
                                    col3.setText(link);

                                    col1.setPadding(10,10,10,10);
                                    col1.setTextColor(Color.BLUE);
                                    col2.setPadding(10,10,10,10);
                                    col3.setPadding(10,10,10,10);
                                    col3.setTextColor(Color.GRAY);

                                    col1.setLayoutParams(params2);
                                    col2.setLayoutParams(params2);
                                    col3.setLayoutParams(params2);

                                    row.addView(col1);
                                    row.addView(col2);
                                    row.addView(col3);

                                    Linkify.addLinks(col3, Linkify.WEB_URLS);

                                    row.setLayoutParams(params2);
                                    table.addView(row);
                                }


                            } catch (Exception ex) {
                                //Result.setText(Result.getText() + "\n" +ex.toString());
                            }
                        }





                    });
                }
            }
        });

    }
}
