package com.example.iis5.imdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iis5.imdb.Adapters.TopRatedAdapter;
import com.example.iis5.imdb.Adapters.UpcomingMoviesAdapter;
import com.example.iis5.imdb.Models.TopratedModel;
import com.example.iis5.imdb.Models.UpcomingMovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopRated extends AppCompatActivity {

    ListView listView;
    TopRatedAdapter myAdapter;
    List<TopratedModel> topratedModels = new ArrayList<>();
    TopratedModel movieModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.lv);

        final ProgressDialog dialog = new ProgressDialog(TopRated.this);
        dialog.setTitle("loading....");
        dialog.show();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/top_rated?api_key=8496be0b2149805afa458ab8ec27560c", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    dialog.dismiss();
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        String title = object.getString("title");
                        String releasedate = object.getString("release_date");
                        String rdt = "Release Date " + releasedate;
                        double voteavg = object.getDouble("vote_average");
                        double avgvote = voteavg / 2;
                        int id = object.getInt("id");
                        double pop = object.getDouble("popularity");
                        String popularity=pop+" users voted";
                        int votecount = object.getInt("vote_count");
                        String image = object.getString("poster_path");
                        String image2 = "http://image.tmdb.org/t/p/w92" + image;
                        movieModel = new TopratedModel(id,title, rdt, votecount, image2, avgvote, popularity);
                        topratedModels.add(movieModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myAdapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        myAdapter = new TopRatedAdapter(TopRated.this, R.layout.upcoming_movies_layout, topratedModels);
        listView.setAdapter(myAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.mostpopular:
                Intent intent =new Intent(TopRated.this, MostPopular.class);
                startActivity(intent);
                finish();
                break;

            case R.id.lastmovies:
                Intent intent1 =new Intent(TopRated.this, LatestMovies.class);
                startActivity(intent1);
                finish();

                break;
            case R.id.nowplaying:
                Intent intent2 =new Intent(TopRated.this, NowPlaying.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.upcoming:
                Intent intent3 =new Intent(TopRated.this, MainActivity.class);
                startActivity(intent3);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}


