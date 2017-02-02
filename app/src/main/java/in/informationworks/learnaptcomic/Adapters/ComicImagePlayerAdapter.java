package in.informationworks.learnaptcomic.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 31-Jan-17.
 */

public class ComicImagePlayerAdapter extends PagerAdapter {
    private int[] image_resources={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4};
    private Context context;
    private LayoutInflater layoutInflater;



    public ComicImagePlayerAdapter(Context context)
    {
        this.context=context;
    }
    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view=layoutInflater.inflate(R.layout.swip_layout_image_player,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.comic_image_player_image);
        TextView textView = (TextView)item_view.findViewById(R.id.image_count);
        imageView.setImageResource(image_resources[position]);
        textView.setText("Image No:"+position);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object) {
       container.removeView((LinearLayout)object);
    }
}
