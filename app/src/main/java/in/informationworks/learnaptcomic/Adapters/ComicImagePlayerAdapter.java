package in.informationworks.learnaptcomic.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import in.informationworks.learnaptcomic.Activities.ComicDetailsActivity;
import in.informationworks.learnaptcomic.Activities.ComicImagePlayerActivity;
import in.informationworks.learnaptcomic.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Riya on 31-Jan-17.
 */

public class ComicImagePlayerAdapter extends PagerAdapter {
    private ArrayList<String> image_resources,thumbImageResources;//={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4};
    private Context context;
    ProgressBar progressBar;
    private LayoutInflater layoutInflater;
    public PhotoView photoView;
    ImageView thumbImageView;
    ComicImagePlayerActivity comicImagePlayerActivity;



    public ComicImagePlayerAdapter(ArrayList<String> image_resources,ArrayList<String> thumbImageResources, Context context,ComicImagePlayerActivity comicImagePlayerActivity) {
        this.image_resources = image_resources;
        this.thumbImageResources = thumbImageResources;
        this.context = context;
        this.comicImagePlayerActivity = comicImagePlayerActivity;
    }


    @Override
    public int getCount()
    {
        return image_resources.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        comicImagePlayerActivity.setVisiblityOfButton();
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view=layoutInflater.inflate(R.layout.swip_layout_image_player,container,false);
        photoView = (PhotoView) item_view.findViewById(R.id.comic_image_player_photoview);
        thumbImageView = (ImageView)item_view.findViewById(R.id.comic_image_player_thumb_photoview);
        progressBar = (ProgressBar)item_view.findViewById(R.id.player_progress);

        item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comicImagePlayerActivity.onPhotoViewClick();
            }
        });
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                comicImagePlayerActivity.onPhotoViewClick();
            }
        });

        thumbImageView.setVisibility(View.VISIBLE);
        Picasso.with(context).load(image_resources.get(position)).error(R.mipmap.ic_launcher).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                photoView.setImageBitmap(bitmap);
                thumbImageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE );
                /*try {

                    String name = new Date().toString() + ".jpg";
                    Toast.makeText(context,context.getFilesDir().toString(),Toast.LENGTH_LONG).show();
                    File myDir = new File(context.getFilesDir(), name);

                    if (!myDir.exists()) {
                        myDir.mkdirs();
                    }

                    FileOutputStream out = new FileOutputStream(myDir);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                    out.flush();
                    out.close();
                } catch(Exception e){
                    // some action
                }*/
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        Picasso.with(context).load(thumbImageResources.get(position)).error(R.mipmap.ic_launcher).into(thumbImageView);
        comicImagePlayerActivity.setCurrentImage();
        container.addView(item_view);
        return item_view;
    }


    @Override
    public void destroyItem(ViewGroup container,int position,Object object) {
       container.removeView((View)object);
    }


}
