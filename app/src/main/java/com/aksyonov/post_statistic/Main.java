package com.aksyonov.post_statistic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aksyonov.post_statistic.Adapter.Adapter_Commenter;
import com.aksyonov.post_statistic.Adapter.Adapter_Liker;
import com.aksyonov.post_statistic.Adapter.Adapter_Mention;
import com.aksyonov.post_statistic.Adapter.Adapter_Reposter;
import com.aksyonov.post_statistic.Bookmarks.Bookmarks;
import com.aksyonov.post_statistic.Commentators.Commentators;
import com.aksyonov.post_statistic.Likers.Likers;
import com.aksyonov.post_statistic.Mentions.Mentions;
import com.aksyonov.post_statistic.Reposters.Reposters;
import com.aksyonov.post_statistic.Views.Views;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;

import static com.aksyonov.post_statistic.Utils.Network.COMMENTATORS;
import static com.aksyonov.post_statistic.Utils.Network.LIKERS;
import static com.aksyonov.post_statistic.Utils.Network.MENTIONS;
import static com.aksyonov.post_statistic.Utils.Network.POST;
import static com.aksyonov.post_statistic.Utils.Network.REPOSTERS;
import static com.aksyonov.post_statistic.Utils.Network.generateURL;
import static com.aksyonov.post_statistic.Utils.Network.getResponceFromURL;

public class Main extends AppCompatActivity {

    private TextView tv_likes_count;
    private TextView tv_commenters_count;
    private TextView tv_mentions_count;
    private TextView tv_reposters_count;
    private TextView tv_bookmarks_count;
    private TextView tv_view_count;

    private RecyclerView likers_list;
    private RecyclerView commenters_list;
    private RecyclerView mention_list;
    private RecyclerView reposters_list;

    private Adapter_Liker adapterLiker;
    private Adapter_Commenter adapterСommenter;
    private Adapter_Mention adapterMention;
    private Adapter_Reposter adapterReposter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_likes_count = (TextView) findViewById(R.id.tv_likes_count);
        tv_commenters_count = (TextView) findViewById(R.id.tv_commenters_count);
        tv_mentions_count = (TextView) findViewById(R.id.tv_mentions_count);
        tv_reposters_count = (TextView) findViewById(R.id.tv_reposters_count);
        tv_bookmarks_count = (TextView) findViewById(R.id.tv_bookmarks_count);
        tv_view_count = (TextView) findViewById(R.id.tv_view_count);

        likers_list = (RecyclerView) findViewById(R.id.rv_likers);
        commenters_list = (RecyclerView) findViewById(R.id.rv_commenters);
        mention_list = (RecyclerView) findViewById(R.id.rv_mention);
        reposters_list = (RecyclerView) findViewById(R.id.rv_reposters);


        LinearLayoutManager LayoutManagerLikers = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        likers_list.setLayoutManager(LayoutManagerLikers);
        likers_list.setHasFixedSize(true);

        LinearLayoutManager LayoutManagerCommenter = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        commenters_list.setLayoutManager(LayoutManagerCommenter);
        commenters_list.setHasFixedSize(true);

        LinearLayoutManager LayoutManagerMention = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mention_list.setLayoutManager(LayoutManagerMention);
        mention_list.setHasFixedSize(true);

        LinearLayoutManager LayoutManagerReposter = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        reposters_list.setLayoutManager(LayoutManagerReposter);
        reposters_list.setHasFixedSize(true);




        URL URL_LIKERS = generateURL(LIKERS);
        URL URL_COMMENTERS = generateURL(COMMENTATORS);
        URL URL_MENTION = generateURL(MENTIONS);
        URL URL_REPOSTERS = generateURL(REPOSTERS);
        URL URL_POST = generateURL(POST);

        new ViewsQueryTask().execute(URL_POST);
        new LikersQueryTask().execute(URL_LIKERS);
        new CommentatorsQueryTask().execute(URL_COMMENTERS);
        new MentionsQueryTask().execute(URL_MENTION);
        new RepostersQueryTask().execute(URL_REPOSTERS);
        new BookmarksQueryTask().execute(URL_POST);



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

            adapterСommenter = new Adapter_Commenter(commentators);
            commenters_list.setAdapter(adapterСommenter);
        }
    }

    public class MentionsQueryTask extends AsyncTask<URL, Void, Mentions>{

        private Mentions getMentions(String responce) {

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Mentions mentions = gson.fromJson(responce, Mentions.class);
            return mentions;
        }
        @Override
        protected Mentions doInBackground(URL... urls) {
            try {
                String responceFromURL = getResponceFromURL(urls[0]);
                return getMentions(responceFromURL);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        @Override
        protected void onPostExecute (Mentions mentions){
            tv_mentions_count.setText(String.valueOf(mentions.meta.total));

            adapterMention = new Adapter_Mention(mentions);
            mention_list.setAdapter(adapterMention);
        }
    }

    public class RepostersQueryTask extends AsyncTask<URL, Void, Reposters>{

        private Reposters getReposters(String responce) {

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Reposters reposters = gson.fromJson(responce, Reposters.class);
            return reposters;
        }
        @Override
        protected Reposters doInBackground(URL... urls) {
            try {
                String responceFromURL = getResponceFromURL(urls[0]);
                return getReposters(responceFromURL);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        @Override
        protected void onPostExecute (Reposters reposters){
            tv_reposters_count.setText(String.valueOf(reposters.meta.total));

            adapterReposter = new Adapter_Reposter(reposters);
            reposters_list.setAdapter(adapterReposter);
        }
    }

    public class BookmarksQueryTask extends AsyncTask<URL, Void, Bookmarks>{

        private Bookmarks getBookmarks(String responce) {

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Bookmarks bookmarks = gson.fromJson(responce, Bookmarks.class);
            return bookmarks;
        }
        @Override
        protected Bookmarks doInBackground(URL... urls) {
            try {
                String responceFromURL = getResponceFromURL(urls[0]);
                return getBookmarks(responceFromURL);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        @Override
        protected void onPostExecute (Bookmarks bookmarks){
            tv_bookmarks_count.setText(String.valueOf(bookmarks.bookmarks_count));

        }
    }

    public class ViewsQueryTask extends AsyncTask<URL, Void, Views>{

        private Views getViews(String responce) {

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Views views = gson.fromJson(responce, Views.class);
            return views;
        }
        @Override
        protected Views doInBackground(URL... urls) {
            try {
                String responceFromURL = getResponceFromURL(urls[0]);
                return getViews(responceFromURL);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        @Override
        protected void onPostExecute (Views views){
            tv_view_count.setText(String.valueOf(views.views_count));

        }
    }
}
