package in.informationworks.learnaptcomic.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.ComicCardGridItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.R;

public class ComicImagesGridActivity extends AppCompatActivity {

    public static final String SCROLL_INDEX = "ScrollIndex";
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    int comicID;
    String StringjsonObject;
    List<ComicCardGridItem> receivedComicImages;
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
        jsonObject = (new JsonParser()).parse(StringjsonObject).getAsJsonObject();
    }

    private  void getDataFromJson()
    {
        JsonObject comicObject = jsonObject.get("comic").getAsJsonObject();
        comicImageArray = comicObject.getAsJsonArray("comic_images");
        Type listType = new TypeToken<ArrayList<ComicCardGridItem>>(){}.getType();
        receivedComicImages = new Gson().fromJson(comicImageArray,listType);
        setDataInRecyclerView();
    }

    private void setDataInRecyclerView()
    {
        recyclerItems = new ArrayList<>();
        for (ComicCardGridItem ComicCardGridItem : receivedComicImages) {
            CommonRecyclerItem commonRecyclerItem=new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_GRID_IMAGE,ComicCardGridItem);
            List<Object> options=new ArrayList<>();
            options.add(ComicImagesGridActivity.this);
            commonRecyclerItem.setOptions(options);
            recyclerItems.add(commonRecyclerItem);
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

    public void onCardGridClicked(ComicCardGridItem ComicCardGridItem) {
        int index=receivedComicImages.indexOf(ComicCardGridItem);
        Intent intent=new Intent();
        intent.putExtra(SCROLL_INDEX,index);
        setResult(RESULT_OK,intent);
        finish();
    }
}
