package in.informationworks.learnaptcomic.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.ComicImagePlayerAdapter;
import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.AppStorageAgent;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.Views.HackyViewPager;
import in.informationworks.learnaptcomic.helper.LCHelper;
import in.informationworks.learnaptcomic.util.IabHelper;
import in.informationworks.learnaptcomic.util.IabResult;
import in.informationworks.learnaptcomic.util.Inventory;
import in.informationworks.learnaptcomic.util.Purchase;

import static android.view.View.INVISIBLE;
import static in.informationworks.learnaptcomic.helper.LCHelper.showAlertDialogBox;

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
    Button readSampleButton,readNowButton,downloadButton,buyButton,addToCartButton,cartCountButton;
    Boolean isResumed = false;
    Boolean ispaid;
    String originalImageUrl,thumbImageUrl;
   public ArrayList<String> image_resources,thumbImageResources;
    ComicImagePlayerAdapter comicImagePlayerAdapter;
    ViewPager viewPager;
    //for purchase
    static final String TAG = "In-App-Billing";
    IabHelper mHelper;
    String PRODUCT_ID = "demo_comic_product_1";
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
        setStatusBarColour();

        isResumed = true;
        //PURCHASE PART
        setConnection();

    }



    public void setStatusBarColour() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(isResumed)
        {
            refreshViewsBasedOnLoginStatus();
           // getData(comicID);
        }
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
        readSampleButton = (Button) findViewById(R.id.read_sample);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        buyButton = (Button) findViewById(R.id.buy_button);
        addToCartButton=(Button) findViewById(R.id.add_to_cart_button);
        viewPager=(HackyViewPager)findViewById(R.id.comic_image_player_viewpager);
        cartCountButton = (Button) findViewById(R.id.cart_button);
    }

    private void refreshViewsBasedOnLoginStatus() {
        if(AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext()))
        {
            //readSampleButton.setVisibility(View.GONE);
            //downloadButton.setVisibility(View.VISIBLE);
            //buyButton.setVisibility(View.VISIBLE);
            //addToCartButton.setVisibility(View.VISIBLE);
            supportInvalidateOptionsMenu();

        }
        else
        {
            //readSampleButton.setVisibility(View.VISIBLE);
            //downloadButton.setVisibility(View.GONE);
            //buyButton.setVisibility(View.GONE);
            //addToCartButton.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();

        }
    }

    public void refreshViewsBasedOnComicPaidOrFree()
    {
        if(ispaid)
        {
            buyButton.setVisibility(View.VISIBLE);
            addToCartButton.setVisibility(View.VISIBLE);
            readSampleButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.GONE);
            readNowButton.setVisibility(View.GONE);
        }
        else
        {
            buyButton.setVisibility(View.GONE);
            addToCartButton.setVisibility(View.GONE);
            readSampleButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.VISIBLE);
            readNowButton.setVisibility(View.VISIBLE);
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
        String url = "http://"+LCHelper.getNetworkIp()+":3000/api/mobile/v1/comics/"+comicID+".json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        image_resources = new ArrayList<>();
                        thumbImageResources = new ArrayList<>();
                        jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        comicObject = jsonObject.get("comic").getAsJsonObject();
                        comicname=comicObject.get("name").getAsString();
                        comicpages=comicObject.get("pages_count").getAsString();
                        comicsize=comicObject.get("size").getAsString();
                        ispaid = comicObject.get("paid").getAsBoolean();
                        //comicsummary=comicObject.get("short_description").getAsString();
                       if((comicObject.get("short_description").toString()).equals("null"))
                        {
                            comicsummary= "nothing to display";
                        }
                        else
                        {
                            comicsummary=comicObject.get("short_description").getAsString();
                        }
                        //Toast.makeText(getApplicationContext(),comicObject.get("short_description").toString(),Toast.LENGTH_LONG).show();
                        JsonObject coverImageObject = comicObject.get("cover_image").getAsJsonObject();
                        // comicImageURL = coverImageObject.get("compressed_image_url").getAsString();
                        if((coverImageObject.get("compressed_image_url").toString()).equals("null"))
                        {
                            int x=10;
                        }
                        else
                        {
                            comicImageURL=coverImageObject.get("compressed_image_url").getAsString();
                        }
                        Picasso.with(getApplicationContext()).load(comicImageURL).error(R.drawable.ic_menu_manage).into(imageView4);
                        comicName.setText(comicname);
                        comicPages.setText(comicpages);
                        comicSize.setText(comicsize);
                        comicSummary.setText(comicsummary);
                        comicImagePreviewArray = comicObject.getAsJsonArray("comic_images");
                        Type listType = new TypeToken<ArrayList<ComicCardPreviewItem>>(){}.getType();
                        receivedComicImages = new Gson().fromJson(comicImagePreviewArray,listType);
                        setDataInRecyclerView();
                        for(int i=0; i<receivedComicImages.size();i++)
                        {
                            if(i>2)
                                break;
                            JsonObject image;
                            image = comicImagePreviewArray.get(i).getAsJsonObject();
                            originalImageUrl=image.get("original_image_url").getAsString();
                            image_resources.add(originalImageUrl);
                            thumbImageUrl=image.get("thumb_image_url").getAsString();
                            // Toast.makeText(getApplicationContext(),thumbImageUrl,Toast.LENGTH_LONG).show();
                            thumbImageResources.add(thumbImageUrl);
                        }
                        commicDetailsContent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(INVISIBLE);
                        refreshViewsBasedOnComicPaidOrFree();
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
    public void onCartIconButtonClick(View view)
    {
        Intent intent = new Intent(this,CartActivity.class);
        startActivity(intent);
    }
    public void onAddToCartClick(View view)
    {
        Toast.makeText(this,"Add to cart",Toast.LENGTH_LONG).show();
    }

    public void onReadSampleClick(View view)
    {
  //  comicImagePlayerAdapter= new ComicImagePlayerAdapter(image_resources,thumbImageResources,getApplicationContext(),ComicDetailsActivity.this);
   // viewPager.setAdapter(comicImagePlayerAdapter);
        //Toast.makeText(this,"Read sample",Toast.LENGTH_LONG).show();
        ComicImagesPreviewActivity.launch(ComicDetailsActivity.this,comicID,image_resources,thumbImageResources);
    }

    private void startFetchingData()
    {
        homeAdapter = new HomeAdapter(this,recyclerItems);
        previewRecyclerView.setAdapter(homeAdapter);
    }

    private void setDataInRecyclerView()
    {
        for (ComicCardPreviewItem ComicCardPreviewItem : receivedComicImages.subList(0,3)) {
            //if(receivedComicImages.subList(0,3).size()>2)
              //  break;
            CommonRecyclerItem cri=new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_PREVIEW_IMAGE,ComicCardPreviewItem);
            List<Object> options = new ArrayList<>();
            options.add(ComicDetailsActivity.this);
            cri.setOptions(options);
            recyclerItems.add(cri);
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
        //if (!AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext())) {
          //  menu.findItem(R.id.action_like).setVisible(false);
           // Toast.makeText(getApplicationContext(),"disabled",Toast.LENGTH_LONG).show();
        //}
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_like){
            if(AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext()))
            {
                Toast.makeText(this,"Write like code",Toast.LENGTH_LONG).show();
            }
            else
            {
               showAlertDialogBox(this);
            }
        }
        if(id == R.id.action_share){
            Toast.makeText(this,"share",Toast.LENGTH_LONG).show();
        }
       /* if(id == R.id.action_cart){
            Toast.makeText(this,"cart",Toast.LENGTH_LONG).show();
        }*/
        if(id == R.id.action_login){
            Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);
        }
        if(id == R.id.action_logout){
            LCHelper.userLogoutFromApp(getApplicationContext());
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDownload(View view)
    {
        if(AppStorageAgent.getSharedStoredBoolean("isLoggedIn",getApplicationContext()))
        {
            Toast.makeText(this,"Write download code here",Toast.LENGTH_LONG).show();
        }
        else
        {
            showAlertDialogBox(this);
        }
    }

    //PURCHASE PART
    private void setConnection() {
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4Wr4meWb0kIif1Kg0NYTiBhGq6UPDT5nXWrVE2yzRG4wP4ciqNo4SMsrMrCSSVcyZGKEVWIdgUZiDqfsvvum6+u5Ld1a+DSuNYTPtluRz/300Xzyg5yntQKPKekPN071INBJLRLol9t/MF48KhXmZDRRANtO35V1aoEgUnT8f5LtZcRVFa8gHkz8IFNe8/eyq5PK77Z+fqEwqAOHwLJpthd0WHWsQwPTE1jof+pluTdsW6xclCutC2ALxKplqFKxCqfSGEYbKCdKpD2CtqozDQpjOXGyO/WMltkKgdUmryIt55EpF0I/wXkOiGLtmBIAtH62PKaMnZzzwZyaH0ZjQIDAQAB";
        Log.d(TAG,"Creating IAB helper");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(false);

        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }
                if (mHelper == null) return;
                Log.d(TAG, "Setup successful.");
                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }

    void complain(String message) {
        Log.e(TAG, "**** ComicPurchase Error: " + message);
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Purchase comicPurchase = inventory.getPurchase(PRODUCT_ID);
            if (comicPurchase != null && verifyDeveloperPayload(comicPurchase)) {
                Log.d(TAG, "We have Lesson1.");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(PRODUCT_ID), mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error consuming Lesson1. Another async operation in progress.");
                }
                return;
            }
        }
    };

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        Toast.makeText(this, "Payload is "+payload, Toast.LENGTH_SHORT).show();
        return true;
    }

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            if (mHelper == null) return;
            if (result.isSuccess()) {
                Log.d(TAG, "Consumption successful. Provisioning.");
            }
            else {
                complain("Error while consuming: " + result);
            }
            Log.d(TAG, "End consumption flow.");
        }
    };
    public void onBuyClick(View view)
    {
       /*if (getActiveCourse() != null) {
            if (getActiveCourse().isPaid()) {
                if (getActiveCourse().isPurchased()) {
                    launchCurrentLesson();
                } else {
                     getActiveCourse().promptForAppUpdate(this);}
                    purchaseCourse();
                }
            }
            else {
                launchCurrentLesson();
            }
        }*/
        buyComic();
    }

    private void buyComic() {
        Log.d(TAG, "Buy lesson1 button clicked.");
        // We will be notified of completion via mPurchaseFinishedListener
        Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "order6";

        try {
            mHelper.launchPurchaseFlow(this,PRODUCT_ID,10001,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");

        }
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(PRODUCT_ID)) {
                // bought lesson1. So read it.
                Log.d(TAG, "Purchase is lesson1. Starting lesson reading.");
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error consuming gas. Another async operation in progress.");
                    return;
                }
            }

        }
    };
    public void onHomeButtonClick(View view)
    {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
    public void onLikeButtonClick(View view)
    {
        Toast.makeText(this,"Button",Toast.LENGTH_SHORT).show();
    }
}
