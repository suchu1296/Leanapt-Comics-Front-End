package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import in.informationworks.learnaptcomic.Models.AppStorageAgent;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.helper.LCHelper;

import static android.provider.ContactsContract.QuickContact.EXTRA_MODE;
import static in.informationworks.learnaptcomic.Models.SingleItemModel.COMIC_TYPE_FEATURED;
import static in.informationworks.learnaptcomic.R.style.AppTheme;
import static in.informationworks.learnaptcomic.helper.LCHelper.showAlertDialogBox;

public class FeaturedComicsActivity extends AppCompatActivity {
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    List<SingleItemModel> comicDataModels;
    Context context;
    int loadedPages=0, mode;
    String mode1,selectedType;
    Toolbar featuredComicsToolbar;
    ProgressBar progressbar;
    FloatingActionButton homeButton;
    FloatingActionButton profileButton;
    int retrivedId;
    String retrivedEmail;
    Boolean isLoggedIn;
    SharedPreferences sharedPref;
    Boolean isResumed = false;
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
       // evaluateSharedPreferences();
        setFeaturedComicsToolbar();
        readintent();
        selectType();
        prepareRecyclerView();
        startFetchingData();
        setStatusBarColour();
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
    }

    private void refreshViewsBasedOnLoginStatus()
    {
        supportInvalidateOptionsMenu();
    }



    public void setStatusBarColour() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void evaluateSharedPreferences()
    {
       sharedPref = getApplicationContext().getSharedPreferences("LearnaptComic_preference", getApplicationContext().MODE_PRIVATE);
        retrivedId = AppStorageAgent.getSharedStoredInt("responseId",getApplicationContext());
        retrivedEmail = AppStorageAgent.getSharedStoredString("responseEmail",getApplicationContext());
        isLoggedIn = AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext());
        String temps = String.valueOf(retrivedId);
        Toast.makeText(this,temps,Toast.LENGTH_LONG).show();
    }

    private void bindViews(){
        activityFeaturedComics = (RelativeLayout) findViewById(R.id.activity_featured_comics);
        recyclerviewFeaturedComics = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerview_featured_comics);
        featuredComicsToolbar = (Toolbar) findViewById(R.id.featured_comics_toolbar);
        homeButton = (FloatingActionButton) findViewById(R.id.home_button);
        profileButton = (FloatingActionButton) findViewById(R.id.profile_button);

    }



    public void readintent()
    {
        Intent i = getIntent();
        mode=i.getIntExtra("EXTRA_MODE",-1);
        mode1 = String.valueOf(mode);
        Toast.makeText(getApplicationContext(),mode1,Toast.LENGTH_LONG).show();
    }

    public void selectType()
    {
        switch (mode) {
            case 1:
                selectedType = "featured-comics";
                featuredComicsToolbar.setTitle("Featured Comics");
                break;
            case 2:
                selectedType = "popular-comics";
                featuredComicsToolbar.setTitle("Popular Comics");
                break;
        }


    }


    private void setFeaturedComicsToolbar()
    {
        setSupportActionBar(featuredComicsToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(context,);
        recyclerviewFeaturedComics.setLayoutManager(linearLayoutManager);
    }

    private void setDataInRecyclerView(List<SingleItemModel> receivedComicsData) {
        comicDataModels.addAll(receivedComicsData);
        for (SingleItemModel singleItemModel : receivedComicsData) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPT_HORIZONTAL_COMIC_CARD,singleItemModel));
        }
        homeAdapter.notifyItemRangeInserted(comicDataModels.size()-receivedComicsData.size(),receivedComicsData.size());
    }

    public void loadNextDataFromApi()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.2.30:3000/api/mobile/v1/home/"+selectedType+".json?page="+(loadedPages+1);
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
        if(id == R.id.action_login){
            Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);
        }if(id == R.id.action_logout){
            LCHelper.userLogoutFromApp(getApplicationContext());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu)
    {
        if (!AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext())) {
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_login).setVisible(true);
            // Toast.makeText(getApplicationContext(),"disabled",Toast.LENGTH_LONG).show();
        }
        else
        {
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    public void onHomeButtonClick(View view)
    {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void onProfileButtonClick(View view)
    {
        if(AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext()))
        {
            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
        else
        {
            showAlertDialogBox(this);
        }
    }
    public void onSettingsButtonClick(View view)
    {
        Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
    }
    public void onLikeButtonClick(View view)
    {
        Toast.makeText(this,"Button",Toast.LENGTH_SHORT).show();
    }
}
