package com.kychow.jayjoska;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<String> mCategories;
    private Context context;

    public CategoryAdapter(ArrayList<String> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View categoryView = inflater.inflate(R.layout.item_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = mCategories.get(position);

        holder.mName.setText(category);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon;
        private TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.ivIcon);
            mName = itemView.findViewById(R.id.tvName);
        }
    }

}
