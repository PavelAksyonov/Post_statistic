package com.aksyonov.post_statistic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aksyonov.post_statistic.models.Liker;
import com.aksyonov.post_statistic.models.Likers;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;

import static com.aksyonov.post_statistic.Utils.Network.ALL_LIKERS;
import static com.aksyonov.post_statistic.Utils.Network.generateURL;
import static com.aksyonov.post_statistic.Utils.Network.getResponceFromURL;

public class Main extends AppCompatActivity {

    private TextView tv_responce;
    private RecyclerView likers_list;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_responce = (TextView) findViewById(R.id.tv_responce);
        likers_list = (RecyclerView) findViewById(R.id.rv_likers);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        likers_list.setLayoutManager(LayoutManager);
        likers_list.setHasFixedSize(true);



        URL generatedURL = generateURL(ALL_LIKERS);
        new QueryTask().execute(generatedURL);




    }

    public class QueryTask extends AsyncTask<URL, Void, Likers>{

        private Likers getLikers(String responce) {

//            Gson gson = new Gson();

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Likers likers = gson.fromJson(responce, Likers.class);

           // String likersCount =String.valueOf(likers.meta.total);
            return likers;




        }

        @Override
        protected Likers doInBackground(URL... urls) {
            try {
                String responceFromURL = getResponceFromURL(urls[0]);
                return getLikers(responceFromURL);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute (Likers likers){
         // tv_responce.setText(getlikers_count(responce));
          //tv_responce.setText(responce);


            adapter = new Adapter(likers);
            likers_list.setAdapter(adapter);


        }

    }


}
