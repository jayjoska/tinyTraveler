package com.kychow.jayjoska;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * @brief CategoryAdapter is the adapter for the RecyclerView in the opening screen
 *
 * Even if the RecyclerView is eventually moved to another activity/fragment, this class should not
 * need major modification.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<String> mCategories;
    private Context context;
    private ArrayList<String> selection;
    private HashMap<String, Object> drawables;
    private HashMap<String, String> alias;
    private static final int NUM_OF_CATEGORIES = 5;

    public CategoryAdapter(ArrayList<String> categories) {
        mCategories = categories;
        selection = new ArrayList<>();
        drawables = Categories.getDrawables();
        alias = Categories.getAliasAndTitle();
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
        if (drawables.containsKey(category)) {
            holder.mName.setText(alias.get(category));
        }

        RequestOptions options = new RequestOptions();
        Glide.with(context)
             .load(drawables.get(category))
             .into(holder.mIcon);

        if (holder.isSelected(position)) {
            holder.itemView.setBackgroundResource(R.color.lavender);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.changeVisibility();
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /*
     * @brief getSelection returns the selection field
     *
     * @input -
     * @output ArrayList<String> selection
     */
    public ArrayList<String> getSelection() {
        return selection;
    }

    public void setSelection(ArrayList<String> selection) {
        this.selection = selection;
    }

    public ArrayList<String> getmCategories() {
        return mCategories;
    }

    public void setmCategories(ArrayList<String> mCategories) {
        this.mCategories = mCategories;
    }

    /*
     * @brief ViewHolder this class holds the view corresponding to the individual category items.
     *
     * Each category includes a TextView and an ImageView
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mIcon;
        private TextView mName;
        private FloatingActionButton mBtn;


        public ViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.ivIcon);
            mName = itemView.findViewById(R.id.tvName);
            // Super shady syntax. Taken from https://stackoverflow.com/questions/11227591/how-to-reference-the-current-or-main-activity-from-another-class
            // Since the fragment refactor, not only is this shady, but it also makes no sense, but guess that's just how programming works
            mBtn = ((MainActivity)context).findViewById(R.id.next_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                String category = mCategories.get(pos);
                if (selection.contains(category)) {
                    selection.remove(category);
                    itemView.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    if (selection.size() < NUM_OF_CATEGORIES) {
                        selection.add(category);
                        itemView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.lavender));
                    } else {
                        Toast.makeText(context, context.getString(R.string.categories_message), Toast.LENGTH_SHORT).show();
                    }
                }
                changeVisibility();
            }
            Log.i("Adapter", selection.toString());
        }

        public boolean isSelected(int pos) {
            return selection.contains(mCategories.get(pos));
        }

        public void changeVisibility() {
            if (selection.size() == NUM_OF_CATEGORIES) {
                mBtn.setVisibility(View.VISIBLE);
            } else {
                mBtn.setVisibility(View.INVISIBLE);
            }
        }
    }
}
