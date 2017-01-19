package in.informationworks.learnaptcomic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class FeaturedComicsActivity extends AppCompatActivity {
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    List<SingleItemModel> receivedComicsData;

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
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.2.30:3000/api/mobile/v1/home/featured-comics.json";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // mTextView.setText("Response is: "+ response.substring(0,500));
                       // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        JsonObject jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        JsonArray jComicsArray = jsonObject.getAsJsonArray("comics");
                        Type listType = new TypeToken<ArrayList<SingleItemModel>>(){}.getType();
                        receivedComicsData = new Gson().fromJson(jComicsArray,listType);
                        setDataInRecyclerView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  mTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);


/*
       // Picasso.with(this).load("https://s3.amazonaws.com/learnapt/uploads/item_image/image/1926/ch3_2_1.jpg").into(imageView);
        recyclerItems =new ArrayList<>();
               recyclerItems = new ArrayList<>();
        createAllFeaturedComicList();
        RecyclerView recyclerview_featured_comics = (RecyclerView) findViewById(R.id.recyclerview_featured_comics);
       // recyclerview_featured_comics.setHasFixedSize(true);
        homeAdapter = new HomeAdapter(this,recyclerItems);
        recyclerview_featured_comics.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerview_featured_comics.setAdapter(homeAdapter);*/






    }

    private void setDataInRecyclerView() {
        recyclerItems = new ArrayList<>();
        for (SingleItemModel singleItemModel : receivedComicsData) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_ITEM,singleItemModel));
        }
        homeAdapter = new HomeAdapter(this,recyclerItems);
        recyclerviewFeaturedComics.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerviewFeaturedComics.setAdapter(homeAdapter);
    }

    private void createAllFeaturedComicList() {

        List singleItems=new ArrayList();
        for (int i = 0; i < 21; i++) {

                singleItems.add(new SingleItemModel());
                CommonRecyclerItem i1 = new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_ITEM, singleItems);
                recyclerItems.add(i1);


        }
    }

}
