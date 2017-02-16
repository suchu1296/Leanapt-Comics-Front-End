package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.ComicImagePlayerAdapter;
import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

import static in.informationworks.learnaptcomic.R.id.recyclerview_featured_comics;

public class ComicImagesGridActivity extends AppCompatActivity {

    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    int comicID;
    String StringjsonObject;
    List<ComicCardPreviewItem> receivedComicImages;
    JsonArray comicImageArray;
    JsonObject jsonObject;
    String ComicIamgeTitle;

    /** Android Views **/
    RelativeLayout activityComicImagesGrid;
    android.support.v7.widget.RecyclerView comicImageGridRecyclerview;
    TextView gridImageTitle;
    /** Android Views **/

    /**
     * Binds XML views
     * Call this function after setContentView() in onCreate().
     **/
    private void bindViews(){
        activityComicImagesGrid = (RelativeLayout) findViewById(R.id.activity_comic_images_grid);
        gridImageTitle = (TextView) findViewById(R.id.grid_image_title);
        comicImageGridRecyclerview = (android.support.v7.widget.RecyclerView) findViewById(R.id.comic_image_grid_recyclerview);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_images_grid);
        bindViews();
        readIntent();
        prepareRecyclerView();
        getDataFromJson();
        startFetchingData();
        setTitle();
    }
    private void readIntent()
    {

        Intent comicImagePlayer = getIntent();
        comicID=comicImagePlayer.getIntExtra("comicID",-1);
        StringjsonObject = (comicImagePlayer.getStringExtra("jsonObject"));
        Toast.makeText(this, "comic id:"+comicID, Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(StringjsonObject,JsonElement.class);
        jsonObject = element.getAsJsonObject();
    }

    private  void getDataFromJson()
    {
        JsonObject comicObject = jsonObject.get("comic").getAsJsonObject();
        comicImageArray = comicObject.getAsJsonArray("comic_images");
        Type listType = new TypeToken<ArrayList<ComicCardPreviewItem>>(){}.getType();
        receivedComicImages = new Gson().fromJson(comicImageArray,listType);
        setDataInRecyclerView();
    }

    private void setDataInRecyclerView()
    {
        recyclerItems = new ArrayList<>();
        for (ComicCardPreviewItem comicCardPreviewItem : receivedComicImages) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_PREVIEW_IMAGE,comicCardPreviewItem));
        }
    }


    private void prepareRecyclerView()
    {
        comicImageGridRecyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
    }

    private void startFetchingData()
    {
        homeAdapter = new HomeAdapter(this,recyclerItems);
        comicImageGridRecyclerview.setAdapter(homeAdapter);
    }

    private void setTitle()
    {
        JsonObject comicObject = jsonObject.get("comic").getAsJsonObject();
        ComicIamgeTitle=comicObject.get("name").getAsString();
        gridImageTitle.setText(ComicIamgeTitle);

    }

    public void onCloseGrid(View view)
    {
        ComicImagesGridActivity.this.finish();
    }
}
