package com.sfsu.csc413.okhttp_request;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        String url = "https://api.stackexchange.com/2.2/posts?page=1&order=desc&sort=votes&site=stackoverflow";



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
                                    Result.setText(text);
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
