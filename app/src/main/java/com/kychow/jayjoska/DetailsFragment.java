package com.kychow.jayjoska;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.kychow.jayjoska.helpers.GlideApp;
import com.kychow.jayjoska.models.Place;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/**
 * @brief Fragment showing details chosen from Recommendations fragment
 * */
public class DetailsFragment extends Fragment {
    Place business;

    ImageView ivBusinessPhoto;
    TextView tvBusinessName;
    TextView tvPrice;
    ImageView ivReview1;
    ImageView ivReview2;
    ImageView ivReview3;
    TextView tvName1;
    TextView tvName2;
    TextView tvName3;
    TextView tvReview1;
    TextView tvReview2;
    TextView tvReview3;
    TextView tvLearnMore;
    RatingBar ratingBar;

    Context context;
    AsyncHttpClient client;
    RequestParams params;

    //needed for Glide center crop
    RequestOptions options;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = new RequestOptions();
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + getString(R.string.yelp_api_key));
        params = new RequestParams();
    }

    // called when fragment draws its UI fpr the first time
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        context = container.getContext();
        business = Parcels.unwrap(getArguments().getParcelable("place"));

        ivBusinessPhoto = view.findViewById(R.id.ivBusinessPhoto);
        tvBusinessName = view.findViewById(R.id.tvBusinessName);
        tvPrice = view.findViewById(R.id.tvPrice);
        ivReview1 = view.findViewById(R.id.ivReview1);
        ivReview2 = view.findViewById(R.id.ivReview2);
        ivReview3 = view.findViewById(R.id.ivReview3);
        tvName1 = view.findViewById(R.id.tvName1);
        tvName2 = view.findViewById(R.id.tvName2);
        tvName3 = view.findViewById(R.id.tvName3);
        tvReview1 = view.findViewById(R.id.tvReview1);
        tvReview2 = view.findViewById(R.id.tvReview2);
        tvReview3 = view.findViewById(R.id.tvReview3);
        tvLearnMore = view.findViewById(R.id.tvLearnMore);
        ratingBar = view.findViewById(R.id.ratingBar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (business.getPrice().equalsIgnoreCase("no price range")) {
            business.setPrice("");
        }

        tvBusinessName.setText(business.getName());
        tvPrice.setText(business.getPrice());
        Log.d("DetailsFragment", business.getPrice());
        ratingBar.setRating(business.getRating());
        tvLearnMore.setText(Html.fromHtml("<a href='" + business.getBusinessURL() + "'> Learn more </a>"));
        tvLearnMore.setMovementMethod(LinkMovementMethod.getInstance());
        tvLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: NOT WORKING!!!
                tvLearnMore.setLinkTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        getReviews();

        GlideApp.with(context)
                .load(business.getImgURL())
                .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(20,0)))
                .placeholder(R.drawable.default_company)
                .into(ivBusinessPhoto);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getReviews() {
        String url = getString(R.string.base_url) + getString(R.string.businesses) + business.getId() + getString(R.string.reviews);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    //grab JSONArray of reviews for business
                    JSONArray reviews = response.getJSONArray("reviews");

                    //grab individual reviewers from JSONArray (only allowed three reviews)
                    JSONObject reviewer1 = reviews.getJSONObject(0).getJSONObject("user");
                    JSONObject reviewer2 = reviews.getJSONObject(1).getJSONObject("user");
                    JSONObject reviewer3 = reviews.getJSONObject(2).getJSONObject("user");

                    tvName1.setText(reviewer1.getString("name"));
                    tvReview1.setText(reviews.getJSONObject(0).getString("text"));
                    GlideApp.with(context)
                            .load(reviewer1.getString("image_url"))
                            .placeholder(R.drawable.default_user)
                            .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(20,0)))
                            .into(ivReview1);

                    tvName2.setText(reviewer2.getString("name"));
                    tvReview2.setText(reviews.getJSONObject(1).getString("text"));
                    GlideApp.with(context)
                            .load(reviewer2.getString("image_url"))
                            .placeholder(R.drawable.default_user)
                            .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(20,0)))
                            .into(ivReview2);

                    tvName3.setText(reviewer3.getString("name"));
                    tvReview3.setText(reviews.getJSONObject(2).getString("text"));
                    GlideApp.with(context)
                            .load(reviewer3.getString("image_url"))
                            .placeholder(R.drawable.default_user)
                            .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(20,0)))
                            .into(ivReview3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.e("DetailsFragment", errorResponse.getJSONObject("error").getString("code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
