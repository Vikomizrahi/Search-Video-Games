package com.example.searchvideogames.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchvideogames.models.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import com.example.searchvideogames.R;
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private ArrayList<Game> games;

    public GameAdapter(ArrayList<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = games.get(position);
        holder.textViewName.setText(game.getName());
        holder.textViewReleaseDate.setText("Release Date: " +game.getReleaseDate());
        holder.textViewRating.setText("Rating: " + String.valueOf(game.getRating()));
        String platformString = "Platform: " + TextUtils.join(",", game.getPlatforms());
        holder.textViewPlatforms.setText(platformString);


        // Use Picasso or Glide to load the image from URL into the ImageView
        Picasso.get().load(game.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewReleaseDate;

        TextView textViewRating;

        TextView textViewPlatforms;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewReleaseDate = itemView.findViewById(R.id.textViewReleaseDate);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPlatforms = itemView.findViewById(R.id.textViewPlatforms);
        }
    }
}
