package com.example.iis5.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.iis5.imdb.Adapters.MyFavoritesAdapter;
import com.example.iis5.imdb.Models.MyFavrotiesModel;

import java.util.ArrayList;
import java.util.List;

public class MyFavorites extends AppCompatActivity {

    private List<MyFavrotiesModel>favrotiesModelList ;
    private RecyclerView recyclerView;
    MyFavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyFavorites.this,MainActivity.class);
                startActivity(i);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        favrotiesModelList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MyFavorites.this,LinearLayoutManager.VERTICAL,false));
        SqLiteDB sqLiteDB =new SqLiteDB(MyFavorites.this);

        favrotiesModelList = sqLiteDB.getMovFav();
          if (favrotiesModelList.size()!=0){
            favoritesAdapter =new MyFavoritesAdapter(MyFavorites.this,favrotiesModelList);
            recyclerView.setAdapter(favoritesAdapter);
          }
           else{
            Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
          }



    }


}


