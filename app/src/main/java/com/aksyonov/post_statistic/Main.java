package com.aksyonov.post_statistic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aksyonov.post_statistic.models.Likers;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        likers_list.setLayoutManager(LayoutManager);
        likers_list.setHasFixedSize(true);

        adapter = new Adapter(3);
        likers_list.setAdapter(adapter);



        URL generatedURL = generateURL(ALL_LIKERS);
        new QueryTask().execute(generatedURL);




    }

    public class QueryTask extends AsyncTask <URL, Void, String>{

        private String getlikersCount(String responce) {

            Gson gson = new Gson();

            Likers likers = gson.fromJson(responce, Likers.class);

            String likersCount =String.valueOf(likers.meta.total);
            return likersCount;



           /* String likers_count="01";
            try {
                JSONObject jsonObject = new JSONObject(responce);
                JSONArray jsonArray =jsonObject.getJSONArray("data");
                JSONArray jsonArray2 =jsonObject.getJSONArray("meta");

                JSONObject JScount = jsonArray2.getJSONObject(0);

               // likers_count= jsonArray.getString("meta");
               likers_count = String.valueOf(JScount.getInt("to"));

                return likers_count;

            } catch (JSONException e ){
                e.printStackTrace();
                return likers_count;
            }*/


        }

        @Override
        protected String doInBackground(URL... urls) {
            String responce =null;

            try {
                responce = getResponceFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            responce = getlikersCount(responce);
            return responce;

        }
        @Override
        protected void onPostExecute (String responce){
         // tv_responce.setText(getlikers_count(responce));
          tv_responce.setText(responce);

        }

    }


}
