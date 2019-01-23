package com.ananthrajsingh.petsearch.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ananthrajsingh.petsearch.Model.Movie;
import com.ananthrajsingh.petsearch.MovieAdapter;
import com.ananthrajsingh.petsearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener{
    //Hardcoded url for now
    private String mUrl = "https://api.themoviedb.org/3/movie/popular?api_key=9610e24872b8b9b8aa0fe214baa00bb1&language=en-US&page=1";
    public ArrayList<Movie> mMovieList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.movies_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new GetMovies().execute();
    }

    @Override
    public void onMovieClick(View view, int position) {
        Toast.makeText(this, "Item clicked at " + position, Toast.LENGTH_LONG).show();
    }

    //TODO make static
    private class GetMovies extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(mUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                String response = buffer.toString();
                Log.e("MainActivity.java", response);

                JSONObject responseObject = new JSONObject(response);
                JSONArray resultArray = responseObject.getJSONArray("results");
                ArrayList<Movie> movieArrayList = new ArrayList<>();
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject currentMovieJsonObject = resultArray.getJSONObject(i);
                    String title = currentMovieJsonObject.getString("title");
                    String description = currentMovieJsonObject.getString("overview");
                    String releaseDate = currentMovieJsonObject.getString("release_date");
                    float rating = (float) currentMovieJsonObject.getDouble("vote_average");
                    String language = currentMovieJsonObject.getString("original_language");
                    String imageUrl = "http://image.tmdb.org/t/p/w185" + currentMovieJsonObject.getString("poster_path");
//                    Log.e("AsyncTask", imageUrl);
                    int id = currentMovieJsonObject.getInt("id");
                    movieArrayList.add(new Movie(title, description, releaseDate, rating, language, imageUrl, id));
                }

                mMovieList = movieArrayList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
            }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MovieAdapter movieAdapter = new MovieAdapter(getBaseContext(), mMovieList);
            movieAdapter.setMovieClickListener(MainActivity.this);
            mRecyclerView.setAdapter(movieAdapter);
        }
    }


}
