package in.informationworks.learnaptcomic.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.informationworks.learnaptcomic.Activities.ComicImagePlayerActivity;
import in.informationworks.learnaptcomic.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Riya on 31-Jan-17.
 */

public class ComicImagePlayerAdapter extends PagerAdapter {
    private ArrayList<String> image_resources;//={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4};
    private Context context;
    private LayoutInflater layoutInflater;
    public PhotoView photoView;
    ComicImagePlayerActivity comicImagePlayerActivity;

    public ComicImagePlayerAdapter(ArrayList<String> image_resources, Context context, ComicImagePlayerActivity comicImagePlayerActivity) {
        this.image_resources = image_resources;
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
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view=layoutInflater.inflate(R.layout.swip_layout_image_player,container,false);
        photoView = (PhotoView) item_view.findViewById(R.id.comic_image_player_photoview);
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                comicImagePlayerActivity.onPhotoViewClick();
            }
        });
        Picasso.with(context).load(image_resources.get(position)).error(R.drawable.ic_menu_manage).into(photoView);
        container.addView(item_view);
        return item_view;


    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object) {
       container.removeView((LinearLayout)object);
    }


}
