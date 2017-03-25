package in.informationworks.learnaptcomic.Activities;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import in.informationworks.learnaptcomic.Adapters.ComicImagePlayerAdapter;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.Views.HackyViewPager;
import in.informationworks.learnaptcomic.helper.LCHelper;
//import static in.informationworks.learnaptcomic.R.id.simpleProgressBar;

public class ComicImagePlayerActivity extends AppCompatActivity {
    private static final int INDEX_OF_PAGE_TO_SCROLL = 111;
    ViewPager viewPager;
    int comicID;
    String originalImageUrl,thumbImageUrl;
    public ArrayList<String> image_resources;
    public ArrayList<String> thumbImageResources;
    public RelativeLayout overlay;
    JsonArray comicImageArray;
    JsonObject jsonObject;
    int order;
    TextView overlayComicName;
    ProgressBar progressBar;
    JsonObject comicObject;
    TextView currentImageNo,totalImages;
    ImageButton nextButton,backButton;
    ComicImagePlayerAdapter comicImagePlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comic_image_player);

       // progressBar = (ProgressBar) findViewById(simpleProgressBar);
        bindViews();
        readIntent();
        getData(comicID);
    }

    private void bindViews()
    {
        viewPager=(HackyViewPager)findViewById(R.id.comic_image_player_viewpager);
        overlay = (RelativeLayout) findViewById(R.id.relative_overlay);
        overlayComicName = (TextView)findViewById(R.id.textView_custom_appbar_title);
        currentImageNo = (TextView) findViewById(R.id.current_image_no);
        totalImages = (TextView) findViewById(R.id.total_images);
        nextButton = (ImageButton) findViewById(R.id.next_button);
        backButton = (ImageButton) findViewById(R.id.back_button);
    }


    private void readIntent()
    {
        comicID=getIntent().getIntExtra("comicID",-1);
        Toast.makeText(this, "comic id:"+comicID, Toast.LENGTH_SHORT).show();
    }

    private void getData(int comicID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+ LCHelper.getNetworkIp()+":3000/api/mobile/v1/comics/"+comicID+".json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        image_resources = new ArrayList<>();
                        thumbImageResources = new ArrayList<>();
                        jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        comicObject = jsonObject.get("comic").getAsJsonObject();
                        comicImageArray = comicObject.getAsJsonArray("comic_images");
                        for (int i = 0; i < comicImageArray.size(); i++) {
                            JsonObject image;
                            image = comicImageArray.get(i).getAsJsonObject();
                            originalImageUrl=image.get("original_image_url").getAsString();
                            image_resources.add(originalImageUrl);
                            thumbImageUrl=image.get("thumb_image_url").getAsString();
                           // Toast.makeText(getApplicationContext(),thumbImageUrl,Toast.LENGTH_LONG).show();
                            thumbImageResources.add(thumbImageUrl);
                        }
                        comicImagePlayerAdapter= new ComicImagePlayerAdapter(image_resources,thumbImageResources,getApplicationContext(),ComicImagePlayerActivity.this);
                        viewPager.setAdapter(comicImagePlayerAdapter);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  mTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void onPhotoViewClick()
    {
        if(overlay.getVisibility()!=View.VISIBLE) {
            overlay.setVisibility(View.VISIBLE);
            String ComicName= comicObject.get("name").getAsString();
            overlayComicName.setText(ComicName);
            Toast.makeText(getApplicationContext(), "visibility yes", Toast.LENGTH_SHORT).show();
            int index = comicImagePlayerAdapter.getCount();
            String indexs = String.valueOf(index);
            totalImages.setText(indexs);
        }
        else
        {
            overlay.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "visibility gone", Toast.LENGTH_SHORT).show();
        }

    }
    public void onGridViewClick(View view)
    {
        Intent i = new Intent(this,ComicImagesGridActivity.class);
        i.putExtra("comicID",comicID);
        i.putExtra("jsonObject",jsonObject.toString());
        startActivityForResult(i,INDEX_OF_PAGE_TO_SCROLL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==INDEX_OF_PAGE_TO_SCROLL){
            if(resultCode==RESULT_OK){
                int index=data.getIntExtra(ComicImagesGridActivity.SCROLL_INDEX,viewPager.getCurrentItem());
                viewPager.setCurrentItem(index);
            }
        }
    }

    public void setCurrentImage()
    {viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
            String s = String.valueOf(position+1);
            currentImageNo.setText(s);
            if(position+1 == image_resources.size())
            {
                nextButton.setVisibility(View.INVISIBLE);

            }
            else
            {
                nextButton.setVisibility(View.VISIBLE);
            }
            if(position == 0)
            {
                backButton.setVisibility(View.INVISIBLE);
            }
            else
            {
                backButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    });

    }

    public void onCloseClick(View view)
    {
        ComicImagePlayerActivity.this.finish();
    }

    public void onBackClick(View view)
    {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }
    public  void onNextClick(View view)
    {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }
    public void setVisiblityOfButton()
    {
        if(viewPager.getCurrentItem() == 0)
        {
            String s = String.valueOf(viewPager.getCurrentItem() + 1);
            currentImageNo.setText(s);
            backButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            backButton.setVisibility(View.VISIBLE);
        }
    }

}
