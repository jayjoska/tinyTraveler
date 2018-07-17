package com.kychow.jayjoska;

import android.content.Context;
import android.graphics.Color;
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
    private ArrayList<String> selection;

    public CategoryAdapter(ArrayList<String> categories) {
        mCategories = categories;
        selection = new ArrayList<>();
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mIcon;
        private TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.ivIcon);
            mName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                String category = mCategories.get(pos);
                if (selection.contains(category) || selection.size() > 4) {
                    selection.remove(category);
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    selection.add(category);
                    itemView.setBackgroundColor(Color.GREEN);
                }
            }
            // Log.i("Adapter", selection.toString());
        }
    }

}
