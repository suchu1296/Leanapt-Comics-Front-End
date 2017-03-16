package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.informationworks.learnaptcomic.Activities.ComicImagePlayerActivity;
import in.informationworks.learnaptcomic.Activities.ComicImagesGridActivity;
import in.informationworks.learnaptcomic.Models.ComicCardGridItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.R;

import static in.informationworks.learnaptcomic.R.id.grid_pagenumber;
import static in.informationworks.learnaptcomic.R.id.grid_thumbimg;

/**
 * Created by Riya on 15-Feb-17.
 */

public class SingleComicCardGridItemViewHolder extends RecyclerView.ViewHolder {
    ImageView gridThumbImageView;
    TextView pageNumber;
    public SingleComicCardGridItemViewHolder(View itemView) {
        super(itemView);
        gridThumbImageView=(ImageView)itemView.findViewById(grid_thumbimg);
        pageNumber= (TextView)itemView.findViewById(grid_pagenumber);

    }


    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){
        final ComicCardGridItem ComicCardGridItem = (ComicCardGridItem) commonRecyclerItem.getItem();
        Picasso.with(context).load(ComicCardGridItem.getThumbImageUrl()).error(R.drawable.ic_menu_manage).into(gridThumbImageView);
        pageNumber.setText(String.valueOf(getPosition()+1));
        if(commonRecyclerItem.getOptions()==null || commonRecyclerItem.getOptions().size()==0) {
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ComicImagePlayerActivity.class);
                    intent.putExtra(ComicCardGridItem.EXTRA_ORDER, ComicCardGridItem.getOrder());
                    intent.putExtra(ComicCardGridItem.EXTRA_ID, ComicCardGridItem.getId());
                    v.getContext().startActivity(intent);

                }

            });
        }else{

            final ComicImagesGridActivity comicImagesGridActivity=(ComicImagesGridActivity) commonRecyclerItem.getOptions().get(0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comicImagesGridActivity.onCardGridClicked(ComicCardGridItem);
                }
            });

        }
    }

}
