package com.kychow.jayjoska;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.kychow.jayjoska.helpers.GlideApp;
import com.kychow.jayjoska.helpers.ItemTouchHelperAdapter;
import com.kychow.jayjoska.models.Place;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Karena Chow on 7/20/18.
 */
public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    public static final DecimalFormat df = new DecimalFormat( "#.00" );
    private ArrayList<Place> mItinerary;
    private Context context;
    private ItineraryAdapterCommunication mListener;

    private ItineraryFragment.OnItemViewClickedListener onItemViewClickedListener;

    public ItineraryAdapter(ArrayList<Place> itinerary) {
        mItinerary = itinerary;
        try {
            mListener = (ItineraryAdapterCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ItineraryAdapterCommunication");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recView = inflater.inflate(R.layout.item_itinerary, parent, false);
        ViewHolder viewHolder = new ViewHolder(recView, new CustomEditTextListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = mItinerary.get(position);
        holder.mName.setText(place.getName());
        holder.mCustomEditTextListener.updatePosition(position);

        String roundedDistance = df.format(place.getDistance());
        holder.mDistance.setText(String.format("%s miles", roundedDistance));
        holder.mTime.setText(String.valueOf(place.getTime()));
        holder.mCost.setText(String.format("$%s", String.valueOf(place.getCost())));
        holder.mColor.setImageResource(Categories.getColorPins().get(place.getCategory()));

        GlideApp.with(context)
                .load(place.getImgURL())
                .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(25,0)))
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mItinerary.size();
    }

    public int grabTime() {
        int time = 0;
        for (Place place : mItinerary) {
            time += place.getTime();
        }
        return time;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItinerary, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItinerary, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItinerary.remove(position);
        notifyItemRemoved(position);
        mListener.updateTime(grabTime());
        mListener.snackbarRemovedItem();
    }

    public interface ItineraryAdapterCommunication
    {
        void updateTime(int i);
        void snackbarRemovedItem();
    }


    public void clear() {
        mItinerary.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImage;
        private TextView mDistance;
        private TextView mName;
        private TextView mTime;
        private TextView mCost;
        private CircleImageView mColor;
        private CustomEditTextListener mCustomEditTextListener;

        public ViewHolder(View itemView, CustomEditTextListener customEditTextListener) {
            super(itemView);

            mImage = itemView.findViewById(R.id.ivItineraryPicture);
            mDistance = itemView.findViewById(R.id.tvItineraryDistance);
            mName = itemView.findViewById(R.id.tvItineraryTitle);
            mTime = itemView.findViewById(R.id.tvItineraryTime);
            mCost = itemView.findViewById(R.id.tvItineraryCost);
            mColor = itemView.findViewById(R.id.ivColorItin);
            mCustomEditTextListener = customEditTextListener;
            onItemViewClickedListener = (ItineraryFragment.OnItemViewClickedListener) itemView.getContext();

            mTime.addTextChangedListener(mCustomEditTextListener);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Place place = mItinerary.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("place", Parcels.wrap(place));
                onItemViewClickedListener.inflateDetails(bundle);
            }
        }
    }

    // Taken from https://stackoverflow.com/questions/31844373/saving-edittext-content-in-recyclerview
    public class CustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() != 0) {
                mItinerary.get(position).setTime(Integer.parseInt(s.toString()));
            } else {
                mItinerary.get(position).setTime(0);
            }

            if (mListener == null) {
                try {
                    mListener = (ItineraryAdapterCommunication) context;
                } catch (ClassCastException e) {
                    throw new ClassCastException(context.toString()
                            + " must implement ItineraryAdapterCommunication");
                }
            }
            mListener.updateTime(grabTime());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

