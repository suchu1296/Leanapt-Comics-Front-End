package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;

import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

public class FeaturedComicsActivity extends AppCompatActivity {
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    List<SingleItemModel> comicDataModels;

    Context context;
    int loadedPages=0;

    /** Android Views **/
    RelativeLayout activityFeaturedComics;
    android.support.v7.widget.RecyclerView recyclerviewFeaturedComics;

    /** Android Views **/

    /**
     * Binds XML views
     * Call this function after setContentView() in onCreate().
     **/
    private void bindViews(){
        activityFeaturedComics = (RelativeLayout) findViewById(R.id.activity_featured_comics);
        recyclerviewFeaturedComics = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerview_featured_comics);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_comics);
        bindViews();
        prepareRecyclerView();
        startFetchingData();
    }

    private void startFetchingData() {
        loadedPages =0;
        comicDataModels = new ArrayList<>();
        recyclerItems = new ArrayList<>();
        homeAdapter = new HomeAdapter(this,recyclerItems);
        recyclerviewFeaturedComics.setAdapter(homeAdapter);
        loadNextDataFromApi();
    }

    private void prepareRecyclerView() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        recyclerviewFeaturedComics.setLayoutManager(gridLayoutManager);
    }

    private void setDataInRecyclerView(List<SingleItemModel> receivedComicsData) {
        comicDataModels.addAll(receivedComicsData);
        for (SingleItemModel singleItemModel : receivedComicsData) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_ITEM,singleItemModel));
        }
        homeAdapter.notifyItemRangeInserted(comicDataModels.size()-receivedComicsData.size(),receivedComicsData.size());
    }

    public void loadNextDataFromApi()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.2.30:3000/api/mobile/v1/home/featured-comics.json?page="+(loadedPages+1);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadedPages++;
                        JsonObject jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        JsonArray jComicsArray = jsonObject.getAsJsonArray("comics");
                        Type listType = new TypeToken<ArrayList<SingleItemModel>>(){}.getType();
                        List<SingleItemModel> receivedComicsData = new Gson().fromJson(jComicsArray,listType);
                        setDataInRecyclerView(receivedComicsData);
                        if(jsonObject.get("totalentries").getAsInt()>comicDataModels.size()) {
                            loadNextDataFromApi();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  mTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }
}
