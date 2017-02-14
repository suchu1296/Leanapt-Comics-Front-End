package in.informationworks.learnaptcomic.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ComicImagePlayerActivity extends AppCompatActivity {
    ViewPager viewPager;
    int comicID;
    String originalImageUrl;
    public ArrayList<String> image_resources;

    ComicImagePlayerAdapter comicImagePlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_image_player);
        viewPager=(HackyViewPager)findViewById(R.id.comic_image_player_viewpager);
        readIntent();
        getData(comicID);
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
                        JsonObject jsonObject = (new JsonParser()).parse(response).getAsJsonObject();
                        JsonObject comicObject = jsonObject.get("comic").getAsJsonObject();
                        JsonArray comicImageArray = comicObject.getAsJsonArray("comic_images");
                        for (int i = 0; i < comicImageArray.size(); i++) {
                           JsonObject image;
                            image = comicImageArray.get(i).getAsJsonObject();
                            originalImageUrl=image.get("original_image_url").getAsString();
                            Toast.makeText(getApplicationContext(),originalImageUrl,Toast.LENGTH_LONG).show();
                            image_resources.add(originalImageUrl);

                        }
                        comicImagePlayerAdapter= new ComicImagePlayerAdapter(image_resources,getApplicationContext());
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
}
