package in.informationworks.learnaptcomic.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

import static in.informationworks.learnaptcomic.R.id.recyclerview_comic_card_list;

public class HomeActivity extends AppCompatActivity {



    ArrayList<CommonRecyclerItem> recyclerItems;
    HomeAdapter homeAdapter;
    List<SingleItemModel> receivedComicsData;
    RecyclerView recyclerview_comic_card_list;
    String[] typeOfComics  ={SingleItemModel.COMIC_TYPE_FEATURED,
            SingleItemModel.COMIC_TYPE_POPULAR};
    ProgressBar progressbar;
    boolean featuredLoaded=false, popularLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindViews();
        createCoverData();
        prepareRecyclerView();
    }

    protected void bindViews()
    {
        recyclerview_comic_card_list = (RecyclerView) findViewById(R.id.main_recycler_view);
      //  progressbar = (ProgressBar)findViewById(R.id.home_progressbar);
    }

    protected void prepareRecyclerView()
    {
        homeAdapter = new HomeAdapter(this,recyclerItems);
        recyclerview_comic_card_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview_comic_card_list.setAdapter(homeAdapter);
        popularLoaded=false;
        featuredLoaded=false;
        loadNextCard();
    }

    public void loadNextCard(){
        if(!featuredLoaded) {
            loadComicsForType(SingleItemModel.COMIC_TYPE_FEATURED);
        }else if(!popularLoaded){
            loadComicsForType(SingleItemModel.COMIC_TYPE_POPULAR);
        }
    }

    private void loadComicsForType(final String comicType) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.2.30:3000/api/mobile/v1/home/"+comicType+".json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonObject jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        JsonArray jComicsArray = jsonObject.getAsJsonArray("comics");
                        Type listType = new TypeToken<ArrayList<SingleItemModel>>() {
                        }.getType();
                        receivedComicsData = new Gson().fromJson(jComicsArray, listType);
                        Toast.makeText(getApplicationContext(),comicType,Toast.LENGTH_SHORT).show();
                        CommonRecyclerItem cri1 = new CommonRecyclerItem(CommonRecyclerItem.TYPE_SECTION_DATA, receivedComicsData);
                        List<Object> options=new ArrayList<>();
                        options.add(comicType);
                        cri1.setOptions(options);
                        recyclerItems.add(cri1);
                        homeAdapter.notifyItemInserted(recyclerItems.size());
                        if(comicType.equals(SingleItemModel.COMIC_TYPE_FEATURED)){
                            featuredLoaded = true;
                        }else if (comicType.equals(SingleItemModel.COMIC_TYPE_POPULAR)){
                            popularLoaded = true;
                        }
                        loadNextCard();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  mTextView.setText("That didn't work!");
                Toast.makeText(HomeActivity.this, "That failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    private void createCoverData() {
        recyclerItems = new ArrayList<>();
        List coverItems = new ArrayList();
        for (int i = 0; i < 5; i++) {
            coverItems.add(new CoverItem());
        }

        CommonRecyclerItem cri=new CommonRecyclerItem(CommonRecyclerItem.TYPE_COVER_LIST,coverItems);
        recyclerItems.add(cri);
    }


}
