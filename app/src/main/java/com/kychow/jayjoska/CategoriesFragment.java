package com.kychow.jayjoska;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Karena Chow on 7/18/18.
 * @brief Categories Fragment implements basic category functionality and displays
 * a category RecyclerView.
 */
public class CategoriesFragment extends Fragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    private OnNextButtonClicked mListener;

    private AsyncHttpClient client;
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private ArrayList<String> mCategories;
    @BindView(R.id.next_btn) FloatingActionButton nextBtn;
    private ArrayList<String> mSelections;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mCategories = new ArrayList<>();
        mAdapter = new CategoryAdapter(mCategories);
        String[] categoryAliases = MainActivity.getCategoryAliases();
        for (int i = 0; i < categoryAliases.length; i++)
        {
            mCategories.add(categoryAliases[i]);
            Log.d("CategoriesFragment", categoryAliases[i]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rvCategories);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        client = new AsyncHttpClient();
        // Provide API with API key
        client.addHeader("Authorization", "Bearer " + getString(R.string.yelp_api_key));
        mCategories = new ArrayList<>(); // change to array of categories
        mAdapter = new CategoryAdapter(mCategories);
        mSelections = mAdapter.getSelection();
        mRecyclerView = view.findViewById(R.id.rvCategories);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setAdapter(mAdapter);
        getCategories();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelections.size() == 5) {
                    mListener.sendCategories(mSelections);
                }
            }
        });
    }

    /*
     * @brief getCategories fetches the categories from the Yelp api and populates the recycler view.
     * In order to get the full category name, the alias must be placed directly into the url, and not
     * passed as an argument.
     *
     * @input  -
     * @output void
     */
    public void getCategories() {
        String url;

        // todo migrate category aliases to categories fragment
        final String[] categoryAliases = MainActivity.getCategoryAliases();
        for (int i = 0; i < categoryAliases.length; i++) {
            url = getString(R.string.base_url) + getString(R.string.categories) + categoryAliases[i];

            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    String alias;
                    try {
                        JSONObject category = response.getJSONObject("category");
                        alias = category.getString("alias");
                        if (Categories.getAliasAndTitle().containsKey(alias)) {
                            mCategories.add(alias);
                            mAdapter.notifyItemInserted(mCategories.size() - 1);
                            Log.i("getCategories", alias);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        Log.i("CategoriesFragment", errorResponse.getJSONObject("error").getString("code"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnNextButtonClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNextButtonClicked");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNextButtonClicked {
        void sendCategories(ArrayList<String> categories);
    }

    /*
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCategories);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        String[] categoryAliases = MainActivity.getCategoryAliases();
        ArrayList data = new ArrayList<String>();
        for (int i = 0; i < categoryAliases.length; i++)
        {
            data.add(categoryAliases[i]);
            Log.d("categoryAliases", categoryAliases[i]);
        }

        mListadapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mListadapter);

        return view;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<String> dataList;

        public ListAdapter(ArrayList<String> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewText;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewText = (TextView) itemView.findViewById(R.id.text);
            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewText.setText(dataList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
    */
}
