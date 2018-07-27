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

import com.kychow.jayjoska.models.Place;

import java.util.ArrayList;

/**
 * Created by Karena Chow on 7/20/18.
 */
public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ViewHolder> {

    private ArrayList<Place> mItinerary;
    private Context context;

    public ItineraryAdapter(ArrayList<Place> itinerary) {
        mItinerary = itinerary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recView = inflater.inflate(R.layout.item_itinerary, parent, false);
        ViewHolder viewHolder = new ViewHolder(recView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = mItinerary.get(position);
        holder.mName.setText(place.getName());
        holder.mDistance.setText(Double.toString(place.getDistance()));
        holder.mCategory.setText(place.getCategory());
        holder.mRating.setRating(place.getRating());
    }

    @Override
    public int getItemCount() {
        return mItinerary.size();
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

