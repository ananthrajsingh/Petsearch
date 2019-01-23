package com.ananthrajsingh.petsearch.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ananthrajsingh.petsearch.R;
import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mImageView = findViewById(R.id.detail_backdrop_iv);
        mDescriptionTextView = findViewById(R.id.detail_description_tv);
        Intent intent = getIntent();
        Glide.with(this)
                .load(Uri.parse(intent.getStringExtra("BACKDROP-URL-EXTRA")))
                .into(mImageView);
        mDescriptionTextView.setText(intent.getStringExtra("DESCRIPTION-EXTRA"));
        getSupportActionBar().setTitle(intent.getStringExtra("TITLE-EXTRA"));
    }
}
