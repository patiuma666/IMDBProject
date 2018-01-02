package com.example.iis5.imdb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.iis5.imdb.Adapters.UpcomingMoviesAdapter;
import com.example.iis5.imdb.Models.MyWatchlistModel;
import com.example.iis5.imdb.Models.UpcomingMovieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//here i have created main class
//public keyword is used in the declaration of a class,method or field;public classes,method and fields can be accessed by the members of any class.
//extends is for extending a class. implements is for implementing an interface
//AppCompatActivity is a parent class

    ListView listView;
    UpcomingMoviesAdapter myAdapter;
    static List<UpcomingMovieModel> upcomingMovieModels=new ArrayList<>();
    static UpcomingMovieModel movieModel;
    ViewGroup viewGroup;
    Context context;
    int pos=0;
    public static List<UpcomingMovieModel> getList() {
        return upcomingMovieModels;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //onCreate Called when the activity is first created. This is where you should do all of your normal static set up: create views,
        // bind data to lists, etc.
        //Bundle is most often used for passing data through various Activities.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView =(ListView)findViewById(R.id.lv);
          //declaring id"s for listview
        viewGroup = (ViewGroup)findViewById(R.id.transitioncontainer);
        //upcomingMovieModels.clear();
        //creating an object for UpcomingMoviesadapter whcih takes the context ,layout id ,list
        myAdapter = new UpcomingMoviesAdapter(MainActivity.this,R.layout.upcoming_movies_layout,upcomingMovieModels);
        // Here we set the  adapter to our ListView.
        listView.setAdapter(myAdapter);
        //upcomingMovieModels.clear();
        //set onItemClickListener by clicking on the listview item is should displays the movie details of that particular movie
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                //the onclicking the item it displays the title name as toast to get that we are taking the title from the the list item
                String title= upcomingMovieModels.get(i).getTitle();
                Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                //using a string variable passing to represents a Uniform Resource Locator, a pointer to a "resource" a query to a database or to a search engine
                //the protocol,which is the http part ,used to access that file,then that is known as url
                // WebServer Request URL with and api key
                String mdurl = "http://api.themoviedb.org/3/movie/"+upcomingMovieModels.get(i).getId()+"?api_key=8496be0b2149805afa458ab8ec27560c";
                Log.d("mdurl",mdurl);

                // Creating the JsonObjectRequest class called request2, passing required parameters:
                //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                final JsonObjectRequest request2=new JsonObjectRequest(Request.Method.GET,mdurl , null, new Response.Listener<JSONObject>() {
                    @Override
                    // Takes the response from the JSON request
                    public void onResponse(JSONObject response) {
                        try {
                            // Retrieves the string labeled "tagline" and "status" from
                            //the response JSON Object
                            //and converts them into javascript objects
                         final String decrp = response.getString("tagline");
                            String status =response.getString("status");
                            double revenue =response.getDouble("revenue");
                            double budget = response.getDouble("budget");
                            // Adds strings from object to the  data called moviemodel  which takes the parameters in it
                            movieModel =new UpcomingMovieModel(decrp,status,budget,revenue);
                            // Adds the data string to the model list
                            upcomingMovieModels.add(movieModel);
                            // creating intent obj passing  bundle using putExtra to the
                            Intent intent=new Intent(MainActivity.this,MovieDetails.class);
                            intent.putExtra("desc",decrp);
                            intent.putExtra("status",status);
                            intent.putExtra("rev",revenue);
                            intent.putExtra("bud",budget);
                            // Try and catch are included to handle any errors due to JSON
                        } catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                    // The final parameter overrides the method onErrorResponse() and passes VolleyError
                    //as a parameter
                }, new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                // Adds the JSON object request "request2" to the request queue
                AppController.getInstance().addToRequestQueue(request2);

                Intent intent=new Intent(MainActivity.this,MovieDetails.class);
                //fetching the pos it starts the activty using startActivty()
                intent.putExtra("pos",i);
                startActivity(intent);

            }
        });
        //this is a class called ProgressDialog that allows you to create progress bar
       // Progress bars are used to show progress of a task. to show the to show the data is loading from the internet
        final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setTitle("loading....");
        dialog.show();
        // Creating the JsonObjRequest class called jsonObjectRequest, passing the required parameters
        //JsonURL is the URL to be fetched from
        // URL of object to be parsed
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/upcoming?api_key=8496be0b2149805afa458ab8ec27560c", null, new Response.Listener<JSONObject>() {
            @Override
            // Takes the response from the JSON request
            public void onResponse(JSONObject response) {
                try {
                    dialog.dismiss();
                    //here its giving the JsonArray which is showed with the closed or square braces which gets the JsonArray of string 'results'
                    JSONArray results =response.getJSONArray("results");
                    // Iterates through the JSON Array getting objects and adding them
                    //to the list view until there are no more objects in resultsArray
                    for (int i=0;i<results.length();i++){
                       //gets each JSON object within the JSON array
                        JSONObject object = results.getJSONObject(i);
                        String title =object.getString("title");
                        String releasedate=object.getString("release_date");
                        String rdt ="Release Date: "+releasedate;
                        double voteavg =object.getDouble("vote_average");
                        double avgvote =voteavg/2;
                        int id = object.getInt("id");
                        double pop=object.getDouble("popularity");
                        String popularity=pop+" users voted";
                        int votecount =object.getInt("vote_count");
                        String image =object.getString("poster_path");
                        String image2 = "http://image.tmdb.org/t/p/w500"+image;
                        movieModel = new UpcomingMovieModel(id,title,rdt,votecount,image2,avgvote,popularity);
                        upcomingMovieModels.add(movieModel);
                        Log.d("List size",String.valueOf(upcomingMovieModels.size()));
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
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        //myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        myAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //we are inflating the menu by calling the inflate() method of MenuInflater class. To perform event handling on menu items,
        // you need to override onOptionsItemSelected() method of Activity class.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection.The system will automatically call the "onOptionsItemSelected" method when
        // the user chooses any of the options menu items.
        //switch statement executes one statement from multiple conditions
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // getItemId() method queries the ID for the selected menu item, which you should assign to each menu item
        // in XML using the android:id attribute
        //noinspection SimplifiableIfStatement
        switch (id){
            //Add a case statement for each item in your menu.
            //this refers to the id which is given in layout xml file

            case R.id.mostpopular:
                //intentis a message object which is used to communicate from one activity to another
                //we are using Explicit Intent by specifying its class name
              //  Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                Intent intent =new Intent(MainActivity.this, MostPopular.class);
                //here starts the target activity
                startActivity(intent);
              //  finish() Call this when your activity is done and should be closed, when starting an activity calling finish() will close the current activity and it will not be available in the Activity Stack
                finish();
                break;

            case R.id.lastmovies:
                Intent intent1 =new Intent(MainActivity.this, LatestMovies.class);
                startActivity(intent1);
                finish();

                break;
            case R.id.nowplaying:
                Intent intent2 =new Intent(MainActivity.this, NowPlaying.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.toprated:
                Intent intent3 =new Intent(MainActivity.this, TopRated.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.favorites:
                Intent intent4=new Intent(MainActivity.this,MyFavorites.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.watchlist:
                Intent intent5 =new Intent(MainActivity.this,MyWatchlist.class);
                startActivity(intent5);
                finish();
                break;
            case R.id.refresh:
                Intent intent6 = getIntent();
                finish();
                intent6.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent6);
        }

        return true;
    }
}
