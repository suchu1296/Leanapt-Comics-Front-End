package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.AppStorageAgent;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

import static android.view.View.INVISIBLE;

public class ComicDetailsActivity extends AppCompatActivity {
    int temp;
    String comicname,comicImageURL,comicpages,s,comicsize,comicsummary;
    Intent intent;
    int comicID;
    ImageView imageView4;
    TextView nameLable;
    TextView comicName;
    TextView sizeLabel;
    TextView comicSize;
    TextView pagesLabel;
    TextView comicPages;
    TextView comicSummary;
    android.support.v7.widget.RecyclerView previewRecyclerView;
    Context context;
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    JsonObject jsonObject,comicObject;
    List<ComicCardPreviewItem> receivedComicImages;
    JsonArray comicImagePreviewArray;
    RelativeLayout commicDetailsContent;
    Toolbar comicDetailsToolbar;
    ProgressBar progressBar;
    Button loginButton,readNowButton,downloadButton;
    Boolean isResumed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);
        bindViews();
        setComicDetailsToolbar();
        readIntent();
        refreshViewsBasedOnLoginStatus();
        prepareRecyclerView();
        getData(comicID);
        startFetchingData();
        isResumed = true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(isResumed)
        {
            refreshViewsBasedOnLoginStatus();
        }


        // put your code here...

    }

    private void setComicDetailsToolbar()
    {
        setSupportActionBar(comicDetailsToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        comicDetailsToolbar.setTitle("Comic");
        comicDetailsToolbar.setTitleTextColor(0xFFFFFFFF);
        comicDetailsToolbar.setNavigationIcon(R.drawable.leftaero);
        comicDetailsToolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ComicDetailsActivity.this.finish();
            }
        });
    }


    private void bindViews(){
        previewRecyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.preview_recycler_view);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        comicName = (TextView) findViewById(R.id.comicName);
        sizeLabel = (TextView) findViewById(R.id.sizeLabel);
        comicSize = (TextView) findViewById(R.id.comicSize);
        pagesLabel = (TextView) findViewById(R.id.pagesLabel);
        comicPages = (TextView) findViewById(R.id.comicPages);
        readNowButton = (Button) findViewById(R.id.readNowButton);
        commicDetailsContent = (RelativeLayout) findViewById(R.id.all_content);
        progressBar = (ProgressBar) findViewById(R.id.comic_details_progressbar);
        comicDetailsToolbar = (Toolbar) findViewById(R.id.comic_details_toolbar);
        comicSummary = (TextView) findViewById(R.id.comic_summary);
        loginButton = (Button) findViewById(R.id.login);
        downloadButton = (Button) findViewById(R.id.downloadButton);
    }

    private void refreshViewsBasedOnLoginStatus() {
        if(AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext()))
        {
            loginButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.VISIBLE);
            supportInvalidateOptionsMenu();

        }
        else
        {
            loginButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();

        }
    }

    private void readIntent()
    {
     comicID=getIntent().getIntExtra(SingleItemModel.EXTRA_ID,-1);
        Toast.makeText(this, "comic id:"+comicID+" : "+comicname, Toast.LENGTH_SHORT).show();
    }

    private void prepareRecyclerView() {
        recyclerItems = new ArrayList<>();
        previewRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
    }


    private void getData(int comicID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.2.30:3000/api/mobile/v1/comics/"+comicID+".json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        comicObject = jsonObject.get("comic").getAsJsonObject();
                        comicname=comicObject.get("name").getAsString();
                        comicpages=comicObject.get("pages_count").getAsString();
                        comicsize=comicObject.get("size").getAsString();
                        comicsummary=comicObject.get("short_description").getAsString();
                        JsonObject coverImageObject = comicObject.get("cover_image").getAsJsonObject();
                        comicImageURL = coverImageObject.get("compressed_image_url").getAsString();
                        Picasso.with(getApplicationContext()).load(comicImageURL).error(R.drawable.ic_menu_manage).into(imageView4);
                        comicName.setText(comicname);
                        comicPages.setText(comicpages);
                        comicSize.setText(comicsize);
                        comicSummary.setText(comicsummary);
                        comicImagePreviewArray = comicObject.getAsJsonArray("comic_images");
                        Type listType = new TypeToken<ArrayList<ComicCardPreviewItem>>(){}.getType();
                        receivedComicImages = new Gson().fromJson(comicImagePreviewArray,listType);
                        setDataInRecyclerView();
                        commicDetailsContent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  mTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void onReadNow(View view)
    {
        Intent comicDetailsIntent = new Intent(this,ComicImagePlayerActivity.class);
        comicDetailsIntent.putExtra("comicID",comicID);
        startActivity(comicDetailsIntent);
    }

    public void onLoginClick(View view)
    {
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
    }

    private void startFetchingData()
    {
        homeAdapter = new HomeAdapter(this,recyclerItems);
        previewRecyclerView.setAdapter(homeAdapter);
    }

    private void setDataInRecyclerView()
    {
        for (ComicCardPreviewItem ComicCardPreviewItem : receivedComicImages.subList(0,3)) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_PREVIEW_IMAGE,ComicCardPreviewItem));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comic_details, menu);

        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu (Menu menu)
    {
        if (!AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext())) {
            menu.findItem(R.id.action_like).setVisible(false);
           // Toast.makeText(getApplicationContext(),"disabled",Toast.LENGTH_LONG).show();
        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_like){
            Toast.makeText(this,"like",Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_share){
            Toast.makeText(this,"share",Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_cart){
            Toast.makeText(this,"cart",Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_settings){
            Toast.makeText(this,"setting",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
