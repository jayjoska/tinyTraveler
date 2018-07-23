package com.kychow.jayjoska;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kychow.jayjoska.models.Place;

import java.util.ArrayList;


public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.ViewHolder> {

    private ArrayList<Place> mRecs;
    private Context context;

    public RecommendationsAdapter(ArrayList<Place> recs) {
        mRecs = recs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recView = inflater.inflate(R.layout.item_recommendation, parent, false);
        ViewHolder viewHolder = new ViewHolder(recView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = mRecs.get(position);
        // Populate views
        holder.mName.setText(place.getName());
        holder.mDistance.setText(Long.toString(place.getDistance()));
        holder.mCategory.setText(place.getCategory());
        holder.mRating.setRating(place.getRating());

        Glide.with(context)
                .load(place.getImgURL())
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mRecs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mName;
        private RatingBar mRating;
        private TextView mDistance;
        private TextView mCategory;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.ivPicture);
            mName = itemView.findViewById(R.id.tvTitle);
            mRating = itemView.findViewById(R.id.ratingBar);
            mDistance = itemView.findViewById(R.id.tvDistance);
            mCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}
