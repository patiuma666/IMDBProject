package com.example.iis5.imdb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iis5.imdb.Adapters.CastandCrewAdapter;
import com.example.iis5.imdb.Adapters.PosterAdapter;
import com.example.iis5.imdb.Adapters.WatchlistAdapter;
import com.example.iis5.imdb.Models.CastAndCrewModel;
import com.example.iis5.imdb.Models.MyWatchlistModel;
import com.example.iis5.imdb.Models.PosterModel;
import com.example.iis5.imdb.Models.UpcomingMovieModel;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static com.example.iis5.imdb.MainActivity.upcomingMovieModels;

public class MovieDetails extends AppCompatActivity {
    TextView textView;
    UpcomingMovieModel movieModel;
    List<UpcomingMovieModel> movieModelList;
    List<CastAndCrewModel> castAndCrewModelList2 = new ArrayList<>();
    List<CastAndCrewModel> castAndCrewModelList = new ArrayList<>();
    List<PosterModel> posterslist =new ArrayList<>();
    static List<MyWatchlistModel>watchlistModels =new ArrayList<>();
    WatchlistAdapter watchlistAdapter;

    public static List<MyWatchlistModel> getWatchlistModels() {
        return watchlistModels;
    }
    CastandCrewAdapter castandCrewAdapter;
    CastandCrewAdapter castandCrewAdapter2;
    PosterAdapter posAdapter;
    RecyclerView castRV;
    RecyclerView crewRV;
    RecyclerView posterRV;
    static String key;
    Context context;
    public static String getKey() {
        return key;
    }

    CastAndCrewModel model;
    List<String> trailernames = new ArrayList<>();
    ArrayAdapter<String> traileradapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        movieModel = new UpcomingMovieModel();
        movieModelList = MainActivity.getList();
        castAndCrewModelList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //creating an id for recyclerview
        castRV = (RecyclerView) findViewById(R.id.castrv);
        castRV.setLayoutManager(new LinearLayoutManager(MovieDetails.this, LinearLayoutManager.HORIZONTAL, false));
        //ItemDecoration Responsible for drawing decorations around or on top of the view container of an entry
        castRV.addItemDecoration(new DividerItemDecoration(MovieDetails.this, DividerItemDecoration.HORIZONTAL));
        castandCrewAdapter = new CastandCrewAdapter(MovieDetails.this, castAndCrewModelList);
        castRV.setAdapter(castandCrewAdapter);

        crewRV = (RecyclerView) findViewById(R.id.crewrv);
        crewRV.setLayoutManager(new LinearLayoutManager(MovieDetails.this, LinearLayoutManager.HORIZONTAL, false));
        crewRV.addItemDecoration(new DividerItemDecoration(MovieDetails.this, DividerItemDecoration.HORIZONTAL));
        castandCrewAdapter2 = new CastandCrewAdapter(MovieDetails.this, castAndCrewModelList2);
        crewRV.setAdapter(castandCrewAdapter2);

        posterRV = (RecyclerView) findViewById(R.id.postrv);
        posterRV.setLayoutManager(new LinearLayoutManager(MovieDetails.this, LinearLayoutManager.HORIZONTAL, false));
        posterRV.addItemDecoration(new DividerItemDecoration(MovieDetails.this, DividerItemDecoration.HORIZONTAL));

        ImageView imageView = (ImageView) findViewById(R.id.movieImageView2);
        textView = (TextView) findViewById(R.id.MDtitle);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        final TextView textView1 = (TextView) findViewById(R.id.smallDescriptionTextView);
        final TextView textView2 = (TextView) findViewById(R.id.releaseDateTextView2);

        final TextView textView6 = (TextView) findViewById(R.id.budgetTextView);
        final TextView textView7 = (TextView) findViewById(R.id.revenue);
        final TextView textView8 = (TextView) findViewById(R.id.statusTextView);
        final TextView textView9 = (TextView) findViewById(R.id.voteCountTextView2);
        final TextView textView10 = (TextView) findViewById(R.id.descriptionTextView);
        ImageView imageView2 = (ImageView) findViewById(R.id.favoritesImageView);
        ImageView imageView3 = (ImageView) findViewById(R.id.watchlistImageView);
        TextView textView11 = (TextView) findViewById(R.id.myfavView);
        TextView textView12 = (TextView) findViewById(R.id.mywatchView);
        TextView textView13 = (TextView) findViewById(R.id.poster);
        TextView textView14 = (TextView) findViewById(R.id.trailer);
        final TextView textView15 = (TextView) findViewById(R.id.cast);
        TextView textView16 = (TextView) findViewById(R.id.crew);
        ListView trailer = (ListView) findViewById(R.id.trailerlist);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        final int pos = getIntent().getIntExtra("pos", -1);
        textView.setText(movieModelList.get(pos).getTitle());
        new PosterTask().execute();

        new CastImagesTask().execute();
        ratingBar.setRating((float) movieModelList.get(pos).getVoteAvg());
        Picasso.with(MovieDetails.this).load(movieModelList.get(pos).getImage()).into(imageView);
        movieModel = upcomingMovieModels.get(pos);
        SqLiteDB sqLiteDB = new SqLiteDB(MovieDetails.this);
        if (sqLiteDB.isMovFav(upcomingMovieModels.get(pos).getTitle())) {
            imageView2.setImageResource(R.drawable.myfav_enable);
            imageView2.setEnabled(false);
        } else{
            imageView2.setImageResource(R.drawable.myfav_disable);
        imageView2.setEnabled(true);
    }
       // movieModel = upcomingMovieModels.get(pos);
       // SqLiteDB sqLiteDB = new SqLiteDB(MovieDetails.this);
        if (sqLiteDB.isMovWatchlist(MovieDetails.this,upcomingMovieModels.get(pos).getTitle())){
            imageView3.setImageResource(R.drawable.watchlist_enable);
            imageView3.setEnabled(false);
        }else
            imageView3.setImageResource(R.drawable.watchlist_disable);
           imageView3.setEnabled(true);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new  AlertDialog.Builder(MovieDetails.this);
                builder.setTitle("Do you want to add to watchlist?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String image =movieModelList.get(pos).getImage();
                        String title=movieModelList.get(pos).getTitle();
                        Toast.makeText(MovieDetails.this, "Successfully Added to MyWatchlist", Toast.LENGTH_SHORT).show();
                        SqLiteDB sqLiteDB =new SqLiteDB(MovieDetails.this);
                        sqLiteDB.addMovWatchlist(title,image);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        String mdurl = "http://api.themoviedb.org/3/movie/" + movieModelList.get(pos).getId() + "?api_key=8496be0b2149805afa458ab8ec27560c";
        final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, mdurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String tagl = response.getString("tagline");
                    Log.d("Description", String.valueOf(tagl));
                    String status = response.getString("status");
                    String rd = response.getString("release_date");
                    double revenue = response.getDouble("revenue");
                    Double rev = Double.valueOf(revenue);
                    int votec = response.getInt("vote_count");
                    double budget = response.getDouble("budget");
                    Double bud = Double.valueOf(budget);
                    Log.d("budget", String.valueOf(budget));

                    String dscrp = response.getString("overview");

                    textView2.setText(String.valueOf(rd));
                    textView8.setText("Status:"+String.valueOf(status));
                    textView9.setText(String.valueOf(votec));
                    textView1.setText(String.valueOf(tagl));
                    textView6.setText("Budget:"+String.valueOf(bud.longValue()));
                    textView7.setText("Revenue"+String.valueOf(rev.longValue()));
                    textView10.setText(String.valueOf(dscrp));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(request2);

//        new CastImagesTask().execute();

        String trailerurl = "http://api.themoviedb.org/3/movie/" + movieModelList.get(pos).getId() + "/videos?api_key=8496be0b2149805afa458ab8ec27560c";
        Log.d("trailerurl", trailerurl);
        final JsonObjectRequest objectReq = new JsonObjectRequest(Request.Method.GET, trailerurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        String name = object.getString("name");
                        key = object.getString("key");
                        trailernames.add(name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(objectReq);


        traileradapter = new ArrayAdapter<String>(MovieDetails.this, android.R.layout.simple_list_item_1, trailernames);
        trailer.setAdapter(traileradapter);
        trailer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("key", getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + getKey()));
                startActivity(intent);
            }

            ;
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        castandCrewAdapter = new CastandCrewAdapter(MovieDetails.this, castAndCrewModelList);
        int pos = getIntent().getIntExtra("pos", -1);
        String casturl = "http://api.themoviedb.org/3/movie/" + movieModelList.get(pos).getId() + "/credits?api_key=8496be0b2149805afa458ab8ec27560c";
        Log.d("CastUrL==>", casturl);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, casturl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("crew");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject crew = jsonArray.getJSONObject(i);
                        String name = crew.getString("name");
                        String job = crew.getString("job");
                        String image = crew.getString("profile_path");
                        if (!name.isEmpty()) {
                            CastAndCrewModel castAndCrewModel2 = new CastAndCrewModel(name, job, "http://image.tmdb.org/t/p/w500" + image);
                            castAndCrewModelList2.add(castAndCrewModel2);
                        } else {
                            name = "no name";
                            job = "no job";
                            CastAndCrewModel castAndCrewModel2 = new CastAndCrewModel(name, job, "http://image.tmdb.org/t/p/w500" + image);
                            castAndCrewModelList2.add(castAndCrewModel2);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(request);
        castandCrewAdapter2 = new CastandCrewAdapter(MovieDetails.this, castAndCrewModelList2);
        traileradapter.notifyDataSetChanged();
        castandCrewAdapter.notifyDataSetChanged();
        castandCrewAdapter2.notifyDataSetChanged();
    }

    public class CastImagesTask extends AsyncTask<String, Void, List<CastAndCrewModel>> {

        @Override
        protected List<CastAndCrewModel> doInBackground(String... strings) {
            int pos = getIntent().getIntExtra("pos", -1);
            String casturl = "http://api.themoviedb.org/3/movie/" + movieModelList.get(pos).getId() + "/credits?api_key=8496be0b2149805afa458ab8ec27560c";
            Log.d("CastUrL==>", casturl);
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, casturl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray cast = response.getJSONArray("cast");
                        for (int i = 0; i < cast.length(); i++) {
                            JSONObject jsonObject = cast.getJSONObject(i);
                            String character = jsonObject.getString("character");
                            String name = jsonObject.getString("name");
                            String image = jsonObject.getString("profile_path");
                            String fullimageurl = "http://image.tmdb.org/t/p/w500" + image;
                            Log.d("PROFILEURL==>", fullimageurl);
                            CastAndCrewModel castAndCrewModel = new CastAndCrewModel(name, character, fullimageurl);
                            castAndCrewModelList.add(castAndCrewModel);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            AppController.getInstance().addToRequestQueue(request);

            return castAndCrewModelList;
        }


        @Override
        protected void onPostExecute(List<CastAndCrewModel> castAndCrewModels) {
            castandCrewAdapter = new CastandCrewAdapter(MovieDetails.this, castAndCrewModels);
            castRV.setAdapter(castandCrewAdapter);
        }

    }
    public class PosterTask extends AsyncTask<String,Void,List<PosterModel>>{


        @Override
        protected List<PosterModel> doInBackground(String... strings) {
            int pos = getIntent().getIntExtra("pos", -1);
            String posterurl = "http://api.themoviedb.org/3/movie/" + movieModelList.get(pos).getId() + "/images?api_key=8496be0b2149805afa458ab8ec27560c";
            Log.d("PosterUrL==>",posterurl);
            final JsonObjectRequest requestobj = new JsonObjectRequest(Request.Method.GET, posterurl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("posters");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String pos = jsonObject.getString("file_path");
                            String fullimageurl = "http://image.tmdb.org/t/p/w500" + pos;
                            Log.d("PROFILEURL==>", fullimageurl);
                            PosterModel posterModel = new PosterModel(fullimageurl);
                            posterslist.add(posterModel);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            AppController.getInstance().addToRequestQueue(requestobj);
            return posterslist;
        }

        @Override
        protected void onPostExecute(List<PosterModel> strings) {
            super.onPostExecute(strings);
            posAdapter = new PosterAdapter(MovieDetails.this,strings);
            posterRV.setAdapter(posAdapter);

        }
    }
}

