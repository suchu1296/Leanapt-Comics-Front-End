package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.informationworks.learnaptcomic.Activities.ComicDetailsActivity;
import in.informationworks.learnaptcomic.Activities.ComicImagePlayerActivity;
import in.informationworks.learnaptcomic.Activities.ComicImagesGridActivity;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

import static in.informationworks.learnaptcomic.R.id.grid_thumbimg;

/**
 * Created by Riya on 15-Feb-17.
 */

public class SingleComicCardPreviewItemViewHolder extends RecyclerView.ViewHolder {
    ImageView previewThumbImageView;
    public SingleComicCardPreviewItemViewHolder(View itemView) {
        super(itemView);
        previewThumbImageView=(ImageView)itemView.findViewById(grid_thumbimg);
    }

    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){
        final ComicCardPreviewItem comicCardPreviewItem = (ComicCardPreviewItem) commonRecyclerItem.getItem();
        Picasso.with(context).load(comicCardPreviewItem.getThumbImageUrl()).error(R.drawable.ic_menu_manage).into(previewThumbImageView);
        if(commonRecyclerItem.getOptions()==null || commonRecyclerItem.getOptions().size()==0) {
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ComicImagePlayerActivity.class);
                    intent.putExtra(ComicCardPreviewItem.EXTRA_ORDER, comicCardPreviewItem.getOrder());
                    intent.putExtra(ComicCardPreviewItem.EXTRA_ID, comicCardPreviewItem.getId());
                    v.getContext().startActivity(intent);

                }

            });
        }else{

            final ComicImagesGridActivity comicImagesGridActivity=(ComicImagesGridActivity) commonRecyclerItem.getOptions().get(0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comicImagesGridActivity.onCardPreviewClicked(comicCardPreviewItem);
                }
            });

        }
    }

}
