package com.ananthrajsingh.petsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ananthrajsingh.petsearch.Model.Movie;

import java.util.ArrayList;

/**
 * Created by ananthrajsingh on 23/01/19
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private MovieClickListener mClickListener;
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<Movie> mMovieArrayList;


    public MovieAdapter(Context mContext, ArrayList<Movie> mMovieArrayList) {
        this.mContext = mContext;
        this.mMovieArrayList = mMovieArrayList;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int i) {
        Movie currentMovie = mMovieArrayList.get(i);
        holder.
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;
        TextView title;
        TextView description;
        TextView date;
        TextView language;
        TextView rating;

        public MovieViewHolder(View itemView){
            super(itemView);
            cover = itemView.findViewById(R.id.movie_cover_iv);
            title = itemView.findViewById(R.id.movie_title_tv);
            description = itemView.findViewById(R.id.movie_description_tv);
            date = itemView.findViewById(R.id.release_date_tv);
            language = itemView.findViewById(R.id.movie_language_tv);
            rating = itemView.findViewById(R.id.movie_rating_tv);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onMovieClick(view, getAdapterPosition());
        }
    }

    public void setMovieClickListener(MovieAdapter.MovieClickListener listener){
        mClickListener = listener;
    }

    /**
     * This interface will be implemented in the class from where we call the adapter
     */
    public interface MovieClickListener{
        void onMovieClick(View view, int position);
    }
}
