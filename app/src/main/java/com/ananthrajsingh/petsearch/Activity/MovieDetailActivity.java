package com.ananthrajsingh.petsearch.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ananthrajsingh.petsearch.Model.Movie;
import com.ananthrajsingh.petsearch.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    public TextView mDescriptionTv;
    public TextView mRuntimeTv;
    public TextView mReleaseDateTv;
    public TextView mRating;
    public TextView mGenresTv;
    public TextView mLanguageTv;
    public TextView mBudgetTv;
    public TextView mRevenueTv;
    private static int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initializeViews();

        Intent intent = getIntent();
        Glide.with(this)
                .load(Uri.parse(intent.getStringExtra("BACKDROP-URL-EXTRA")))
                .into(mImageView);
        getSupportActionBar().setTitle(intent.getStringExtra("TITLE-EXTRA"));
        mId = intent.getIntExtra("ID", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        new GetDetails().execute();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    // I know this is bad, to make this non-static
    private class GetDetails extends AsyncTask<Void, Void, Void>{

        String description;
        String backdropPath;
        String runtime;
        String releaseDate;
        String rating;
        String genres;
        String language;
        String budget;
        String revenue;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/" + mId + "?api_key=9610e24872b8b9b8aa0fe214baa00bb1&language=en-US");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                String response = builder.toString();

                JSONObject responseObject = new JSONObject(response);
                backdropPath = "http://image.tmdb.org/t/p/w342" + responseObject.getString("backdrop_path");
                description = responseObject.getString("overview");
                runtime = Integer.toString(responseObject.getInt("runtime")) + " minutes";
                releaseDate = responseObject.getString("release_date");
                rating = Double.toString(responseObject.getDouble("vote_average"));
                JSONArray genresArray = responseObject.getJSONArray("genres");
                genres = "";
                for(int i = 0 ; i < genresArray.length() ; i++){
                    JSONObject genre = genresArray.getJSONObject(i);
                    String currentGenre = genre.getString("name");
                    if (i == 0){
                        genres = currentGenre;
                    }
                    else{
                        genres = genres + ", " + currentGenre;
                    }
                }
                language = responseObject.getString("original_language");
                int budgetInt = responseObject.getInt("budget");
                budgetInt = (int) (budgetInt / Math.pow(10, 6));
                budget = "$" + Integer.toString(budgetInt) + " Million";
                int revenueInt = responseObject.getInt("revenue");
                revenueInt = (int) (revenueInt / Math.pow(10, 6));
                revenue = "$" + Integer.toString(revenueInt) + " Million";

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
            mDescriptionTv.setText(description);
            mRuntimeTv.setText(runtime);
            mReleaseDateTv.setText(releaseDate);
            mRating.setText(rating);
            mGenresTv.setText(genres);
            mLanguageTv.setText(language);
            mBudgetTv.setText(budget);
            mRevenueTv.setText(revenue);
        }
    }

    private void initializeViews(){
        mImageView = findViewById(R.id.detail_backdrop_iv);
        mDescriptionTv = findViewById(R.id.detail_description_tv);
        mRuntimeTv = findViewById(R.id.detail_runtime_tv);
        mReleaseDateTv = findViewById(R.id.detail_release_date_tv);
        mRating = findViewById(R.id.detail_rating_tv);
        mGenresTv = findViewById(R.id.detail_genres_tv);
        mLanguageTv = findViewById(R.id.detail_language_tv);
        mBudgetTv = findViewById(R.id.detail_budget_tv);
        mRevenueTv = findViewById(R.id.detail_revenue_tv);
    }
}
