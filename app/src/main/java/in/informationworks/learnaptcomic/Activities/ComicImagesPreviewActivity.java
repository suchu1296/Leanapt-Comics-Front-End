package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.informationworks.learnaptcomic.Adapters.ComicImagePlayerAdapter;
import in.informationworks.learnaptcomic.Adapters.ComicImagesPreviewAdapter;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.Views.HackyViewPager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ComicImagesPreviewActivity extends AppCompatActivity {

    public ArrayList<String> image_resources,thumbImageResources;
    int comicID;
    ComicImagesPreviewAdapter comicImagesPreviewAdapter ;
    ViewPager viewPager;
    RelativeLayout overlay;
    TextView tvPerview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comic_images_preview);


        // progressBar = (ProgressBar) findViewById(simpleProgressBar);
        bindViews();
        readIntent();
        //getData(comicID);
        setDataInViewPager();
    }

    public void bindViews() {
        viewPager=(HackyViewPager)findViewById(R.id.comic_images_preview_viewpager);
        overlay = (RelativeLayout) findViewById(R.id.relative_preview_overlay);
        tvPerview =(TextView)findViewById(R.id.textView_custom_preview_appbar_title);
    }

    public void setDataInViewPager() {
        comicImagesPreviewAdapter= new ComicImagesPreviewAdapter(image_resources,thumbImageResources,getApplicationContext(),ComicImagesPreviewActivity.this);
        viewPager.setAdapter(comicImagesPreviewAdapter);
    }

    public void readIntent() {
        Intent intent = getIntent();
        comicID=intent.getIntExtra("comicID",-1);
        image_resources=intent.getStringArrayListExtra("image_resources");
        thumbImageResources=intent.getStringArrayListExtra("thumbImageResources");
        //Toast.makeText(this,String.valueOf(comicID),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,image_resources.toString(),Toast.LENGTH_SHORT).show();
    }

    public static void launch(Context context,int comicID,ArrayList image_resources,ArrayList thumbImageResources) {
        Intent intent = new Intent(context,ComicImagesPreviewActivity.class);
        intent.putExtra("comicID",comicID);
        intent.putExtra("image_resources",image_resources);
        intent.putExtra("thumbImageResources",thumbImageResources);
        context.startActivity(intent);
    }

    public void onPhotoViewClick()
    {
        if(overlay.getVisibility()!= View.VISIBLE) {
            overlay.setVisibility(View.VISIBLE);
            tvPerview.setText("Preview");
        }
        else
        {
            overlay.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "visibility gone", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClosePreviewClick(View view)
    {
        ComicImagesPreviewActivity.this.finish();
    }
}
