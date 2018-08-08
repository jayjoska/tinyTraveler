package com.kychow.jayjoska;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.kychow.jayjoska.helpers.GlideApp;
import com.kychow.jayjoska.models.Place;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RecsAdapter extends RecyclerView.Adapter<RecsAdapter.ViewHolder> {

    private ArrayList<Place> mRecs;
    private Context context;
    private RecsFragment.OnSelectedListener mListener;

    public RecsAdapter(ArrayList<Place> recs) {
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
        holder.mDistance.setText(String.format("%.2f miles", place.getDistance()));
        String alias = place.getCategory();
        if (Categories.getAliasAndTitle().containsKey(alias)) {
            holder.mCategory.setText(Categories.getAliasAndTitle().get(alias));
        }
        holder.mRating.setRating(place.getRating());

        GlideApp.with(context)
                .load(place.getImgURL())
                .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(25,0)))
                .placeholder(R.drawable.default_company)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mRecs.size();
    }

    public void remove(ArrayList<Place> toRemove) {
        if (toRemove == null || toRemove.size() == 0) {
            return;
        }
        for (Place place : toRemove) {
            mRecs.remove(place);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            mListener = (RecsFragment.OnSelectedListener) itemView.getContext();

            itemView.setOnClickListener(this);
        }

        //allows for user to click anywhere in view and be brought to details page
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Place place = mRecs.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("place", Parcels.wrap(place));
                mListener.inflateDetails(bundle);
            }
        }
    }
}
