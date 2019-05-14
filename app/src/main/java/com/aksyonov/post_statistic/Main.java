package com.aksyonov.post_statistic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aksyonov.post_statistic.Commentators.Commentators;
import com.aksyonov.post_statistic.Likers.Likers;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;

import static com.aksyonov.post_statistic.Utils.Network.COMMENTERS;
import static com.aksyonov.post_statistic.Utils.Network.LIKERS;
import static com.aksyonov.post_statistic.Utils.Network.generateURL;
import static com.aksyonov.post_statistic.Utils.Network.getResponceFromURL;

public class Main extends AppCompatActivity {

    private TextView tv_likes_count;
    private TextView tv_commenters_count;
    private RecyclerView likers_list;
    private RecyclerView commenters_list;
    private Adapter_Liker adapterLiker;
    private Adapter_Commenter adapter_commenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_likes_count = (TextView) findViewById(R.id.tv_likes_count);
        tv_commenters_count = (TextView) findViewById(R.id.tv_commenters_count);
        likers_list = (RecyclerView) findViewById(R.id.rv_likers);

        commenters_list = (RecyclerView) findViewById(R.id.rv_commenters);


        LinearLayoutManager LayoutManagerLikers = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        likers_list.setLayoutManager(LayoutManagerLikers);
        likers_list.setHasFixedSize(true);


        LinearLayoutManager LayoutManagerCommenters = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        commenters_list.setLayoutManager(LayoutManagerCommenters);
        commenters_list.setHasFixedSize(true);


        URL URL_LIKERS = generateURL(LIKERS);
        URL URL_COMMENTERS = generateURL(COMMENTERS);


        new LikersQueryTask().execute(URL_LIKERS);
        new CommentatorsQueryTask().execute(URL_COMMENTERS);


    }

    public class LikersQueryTask extends AsyncTask<URL, Void, Likers>{

        private Likers getLikers(String responce) {

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Likers likers = gson.fromJson(responce, Likers.class);
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
            tv_likes_count.setText(String.valueOf(likers.meta.total));

            adapterLiker = new Adapter_Liker(likers);
            likers_list.setAdapter(adapterLiker);
        }
    }


    public class CommentatorsQueryTask extends AsyncTask<URL, Void, Commentators>{

        private Commentators getCommentators(String responce) {

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Commentators commentators = gson.fromJson(responce, Commentators.class);
            return commentators;
        }
        @Override
        protected Commentators doInBackground(URL... urls) {
            try {
                String responceFromURL = getResponceFromURL(urls[0]);
                return getCommentators(responceFromURL);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        @Override
        protected void onPostExecute (Commentators commentators){
            tv_commenters_count.setText(String.valueOf(commentators.meta.total));

            adapter_commenter = new Adapter_Commenter(commentators);
            commenters_list.setAdapter(adapter_commenter);
        }
    }

}
