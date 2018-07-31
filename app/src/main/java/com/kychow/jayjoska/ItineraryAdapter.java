package com.kychow.jayjoska;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kychow.jayjoska.models.Place;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Karena Chow on 7/20/18.
 */
public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ViewHolder> {

    public static final DecimalFormat df = new DecimalFormat( "#.00" );
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

        String roundedDistance = df.format(place.getDistance());
        holder.mDistance.setText(String.format("%s miles", roundedDistance));
        holder.mTime.setText(String.format("%s minutes", String.valueOf(place.getTime())));
        holder.mCost.setText(String.format("$%s", String.valueOf(place.getCost())));

        RequestOptions options = new RequestOptions();
        Glide.with(context)
                .load(place.getImgURL())
                .apply(options.centerCrop())
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mItinerary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mDistance;
        private TextView mName;
        private TextView mTime;
        private TextView mCost;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.ivItineraryPicture);
            mDistance = itemView.findViewById(R.id.tvItineraryDistance);
            mName = itemView.findViewById(R.id.tvItineraryTitle);
            mTime = itemView.findViewById(R.id.tvItineraryTime);
            mCost = itemView.findViewById(R.id.tvItineraryCost);
        }
    }
}

