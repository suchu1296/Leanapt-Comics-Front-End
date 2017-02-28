package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import static android.provider.ContactsContract.QuickContact.EXTRA_MODE;

public class FeaturedComicsActivity extends AppCompatActivity {
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    List<SingleItemModel> comicDataModels;
    Context context;
    int loadedPages=0;
    Toolbar featuredComicsToolbar;
    ProgressBar progressbar;
    /** Android Views **/
    RelativeLayout activityFeaturedComics;
    android.support.v7.widget.RecyclerView recyclerviewFeaturedComics;

    /** Android Views **/

    /**
     * Binds XML views
     * Call this function after setContentView() in onCreate().
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_comics);
        bindViews();
        readintent();
        setFeaturedComicsToolbar();
        prepareRecyclerView();
        startFetchingData();
    }

    private void bindViews(){
        activityFeaturedComics = (RelativeLayout) findViewById(R.id.activity_featured_comics);
        recyclerviewFeaturedComics = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerview_featured_comics);
        featuredComicsToolbar = (Toolbar) findViewById(R.id.featured_comics_toolbar);
    }

    public void readintent()
    {
        Intent i = getIntent();
        int mode=i.getIntExtra("EXTRA_MODE",-1);
        String mode1 = String.valueOf(mode);
        Toast.makeText(getApplicationContext(),mode1,Toast.LENGTH_LONG).show();
    }


    private void setFeaturedComicsToolbar()
    {
        setSupportActionBar(featuredComicsToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        featuredComicsToolbar.setTitle("FeaturedComics");
        featuredComicsToolbar.setTitleTextColor(0xFFFFFFFF);
        featuredComicsToolbar.setNavigationIcon(R.drawable.leftaero);
        featuredComicsToolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FeaturedComicsActivity.this.finish();
            }
        });
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
                        else
                        {
                            progressbar = (ProgressBar)findViewById(R.id.fetured_comic_progressbar);
                            progressbar.setVisibility(View.INVISIBLE);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_featured_comics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_search){
          Toast.makeText(this,"search",Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_settings){
            Toast.makeText(this,"setting",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
