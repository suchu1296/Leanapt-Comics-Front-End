package in.informationworks.learnaptcomic.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import in.informationworks.learnaptcomic.Adapters.ComicImagePlayerAdapter;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.Views.HackyViewPager;

import static android.R.attr.id;
import static android.R.attr.visible;

public class ComicImagePlayerActivity extends AppCompatActivity {
    ViewPager viewPager;
    int comicID;
    String originalImageUrl;
    public ArrayList<String> image_resources;
    public RelativeLayout overlay;
    JsonArray comicImageArray;
    JsonObject jsonObject;


    ComicImagePlayerAdapter comicImagePlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comic_image_player);
        viewPager=(HackyViewPager)findViewById(R.id.comic_image_player_viewpager);
        overlay = (RelativeLayout) findViewById(R.id.relative_overlay);
        readIntent();
        getData(comicID);
        addClickListener();
    }

    private void addClickListener() {

    }

    private void readIntent()
    {
        comicID=getIntent().getIntExtra("comicID",-1);
        Toast.makeText(this, "comic id:"+comicID, Toast.LENGTH_SHORT).show();
    }

    private void getData(int comicID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.2.30:3000/api/mobile/v1/comics/"+comicID+".json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        image_resources = new ArrayList<>();
                        jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        JsonObject comicObject = jsonObject.get("comic").getAsJsonObject();
                        comicImageArray = comicObject.getAsJsonArray("comic_images");
                        for (int i = 0; i < comicImageArray.size(); i++) {
                            JsonObject image;
                            image = comicImageArray.get(i).getAsJsonObject();
                            originalImageUrl=image.get("original_image_url").getAsString();
                            //Toast.makeText(getApplicationContext(),originalImageUrl,Toast.LENGTH_LONG).show();
                            image_resources.add(originalImageUrl);

                        }
                        comicImagePlayerAdapter= new ComicImagePlayerAdapter(image_resources,getApplicationContext(),ComicImagePlayerActivity.this);
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
            Toast.makeText(getApplicationContext(), "visibility yes", Toast.LENGTH_SHORT).show();
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
        startActivity(i);
    }


}
