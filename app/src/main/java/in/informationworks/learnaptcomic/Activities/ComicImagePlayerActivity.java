package in.informationworks.learnaptcomic.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.informationworks.learnaptcomic.Adapters.ComicImagePlayerAdapter;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.Views.HackyViewPager;

public class ComicImagePlayerActivity extends AppCompatActivity {
    ViewPager viewPager;
    ComicImagePlayerAdapter comicImagePlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_image_player);
        viewPager=(HackyViewPager)findViewById(R.id.comic_image_player_viewpager);
        comicImagePlayerAdapter = new ComicImagePlayerAdapter(this);
        viewPager.setAdapter(comicImagePlayerAdapter);
    }
}
